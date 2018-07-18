package uispec.parser.specparser;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.terapico.system.CMRField;
import com.terapico.system.FieldDescriptor;
import com.terapico.system.ObjectCollection;
import com.terapico.system.ObjectCollectionHome;
import com.terapico.system.ObjectDescriptor;

import poc.utils.TextUtil;
import uispec.parser.datasource.DataSourceInfo;
import uispec.parser.specelement.PageUiSpec;

public class DataSourceUtil {
    public static ObjectCollection oc = null;
    public static final int EXPRESSION_TYPE_MODEL_PATH = 0;
    public static final int EXPRESSION_TYPE_STRING_CONCAT_FUNCTION = 1;
    public static final int EXPRESSION_TYPE_PAGE_VARIABLE = 2;
    private static AtomicLong varCnt = new AtomicLong(0);

    public static void parseGlobalExpression(Map<String, DataSourceInfo> globalVarTable,
            DataSourceInfo parentDataSourceInfo, String dataSourceName, String dataSourceExpression) throws Exception {
        if (dataSourceExpression == null || TextUtil.isBlank(dataSourceExpression)) {
            throw new Exception("无任何内容的表达式");
        }
        dataSourceExpression = dataSourceExpression.trim();
        DataSourceInfo result = null;

        int exprType = parseExpressionType(dataSourceExpression);
        switch (exprType) {
        case EXPRESSION_TYPE_MODEL_PATH:
            // result = parseModelVariableExpression(globalVarTable, parentDataSourceInfo,
            // dataSourceExpression, true);
            break;
        case EXPRESSION_TYPE_STRING_CONCAT_FUNCTION:
            result = parseStringConcatFunctionExpression(globalVarTable, parentDataSourceInfo, null, dataSourceName,
                    dataSourceExpression, true);
            result.setVarScope(DataSourceInfo.PAGE_SCOPE);
            globalVarTable.put(dataSourceName, result);
            return;
        default:
            result = parseVariableExpression(globalVarTable, parentDataSourceInfo, dataSourceName,
                    dataSourceExpression);
            result.setVarScope(DataSourceInfo.PAGE_SCOPE);
            globalVarTable.put(dataSourceName, result);
            return;
        }

        // 如果前面都没分析到，可能是新的模型变量,page level 只允许直接声明对象
        Matcher m = ptnModelPath.matcher(dataSourceExpression);
        if (!m.matches()) {
            throw new Exception("Cannot handle data-source " + dataSourceExpression + " in page level.");
        }
        String modelPath = m.group(1);
        if (modelPath.indexOf('.') > 0) {
            throw new Exception(
                    "Only model object allowed in page level, " + dataSourceExpression + " is an expression");
        }
        result = createDataSourceFromBizModel(modelPath);
        if (result != null) {
            result.setVariableName(dataSourceName);
            result.setVarScope(DataSourceInfo.PAGE_SCOPE);
            globalVarTable.put(dataSourceName, result);
            return;
        }

        // 理论上任何分析不到的，都是异常
        throw new Exception("cannot parse data-source " + dataSourceExpression);
    }

    public static DataSourceInfo parseExpression(PageUiSpec page, Map<String, DataSourceInfo> globalVarTable,
            DataSourceInfo parentDataSourceInfo, List<DataSourceInfo> localVarTable, String dataSourceExpression)
            throws Exception {
        if (dataSourceExpression == null || TextUtil.isBlank(dataSourceExpression)) {
            throw new Exception("无任何内容的表达式");
        }
        dataSourceExpression = dataSourceExpression.trim();

        int exprType = parseExpressionType(dataSourceExpression);
        switch (exprType) {
        case EXPRESSION_TYPE_MODEL_PATH:
            return parseModelVariableExpression(globalVarTable, parentDataSourceInfo, localVarTable,
                    dataSourceExpression, false);
        case EXPRESSION_TYPE_STRING_CONCAT_FUNCTION:
            return parseStringConcatFunctionExpression(globalVarTable, parentDataSourceInfo, localVarTable, null, dataSourceExpression,
                    false);
        default:
            return parseVariableExpression(globalVarTable, parentDataSourceInfo, null, dataSourceExpression);
        }
    }

    /**
     * 处理 $(store.todayRecommondation.size)这样的表达式
     * 
     * @param localVarTable
     * 
     * @throws Exception
     */
    private static DataSourceInfo parseModelVariableExpression(Map<String, DataSourceInfo> vartable,
            DataSourceInfo parentDataSourceInfo, List<DataSourceInfo> localVarTable, String dataSourceExpression,
            boolean isForGlobal) throws Exception {
        // 先从父亲节点开始找，如果是子节点，那么就找到了。
        // 父节点不能有效的找到子节点声明的数据，再从全局变量里找。
        // 没有局部变量表
        Matcher m = ptnModelPath.matcher(dataSourceExpression);
        if (!m.matches()) {
            throw new Exception("expression " + dataSourceExpression + " should be model-path, but why not?");
        }

        String pathExpression = m.group(1);

        DataSourceInfo result = parseBaseOnParentDataSourceInfo(pathExpression, parentDataSourceInfo);
        if (result != null) {
            return result;
        }
        // 再从本地变量表找
        if (localVarTable != null) {
            for (DataSourceInfo dsInfo : localVarTable) {
                // 暂定本地变量只支持 模型变量表达式
                /*
                 * if (pathExpression.equals(gVar.getVariableName())) { if
                 * (gVar.getType().equals(DataSourceInfo.TYPE_JAVA_VARIABLE)) { return gVar; }
                 * if (gVar.getType().equals(DataSourceInfo.TYPE_STRING_CONCAT_FUNCTION)) {
                 * return gVar; } continue; }
                 */
                // 不是变量和函数
                result = parseBaseOnParentDataSourceInfo(pathExpression, dsInfo);
                if (result != null) {
                    return result;
                }
            }
        }
        // 然后从全局变量表找
        for (DataSourceInfo gVar : vartable.values()) {
            if (pathExpression.equals(gVar.getVariableName())) {
                if (gVar.getType().equals(DataSourceInfo.TYPE_JAVA_VARIABLE)) {
                    return gVar;
                }
                if (gVar.getType().equals(DataSourceInfo.TYPE_STRING_CONCAT_FUNCTION)) {
                    return gVar;
                }
                continue;
            }
            String varNameInPathExpr = pathExpression.split("\\.")[0];
            if (!varNameInPathExpr.equals(gVar.getVariableName())) {
                continue;
            }
            // 不是变量和函数
            String baseOnGlobalExpr = pathExpression.substring(pathExpression.indexOf(".") + 1);
            result = parseBaseOnParentDataSourceInfo(baseOnGlobalExpr, gVar);
            if (result != null) {
                break;
            }
        }
        if (result != null) {
            return result;
        }

        
        // special case: 分析全局变量表的时候不抛异常而是返回null
        if (!isForGlobal) {
            throw new Exception("无法分析变量表达式：" + dataSourceExpression);
        } else {
            return null;
        }
    }

    private static DataSourceInfo createDataSourceFromBizModel(String objName) throws Exception {
        ensureBizModel();

        ObjectDescriptor od = oc.getObjByKey(objName);
        if (od == null) {
            throw new Exception("在业务模型中找不到" + objName);
        }
        DataSourceInfo result = new DataSourceInfo(DataSourceInfo.TYPE_MODEL_PATH, objName, null, null);
        result.setVarScope(DataSourceInfo.PAGE_SCOPE);
        result.setModelingObject(true);
        result.setJavaTypeName(TextUtil.hyphenCaseToBigCamelName(objName));
        return result;
    }

    private static void ensureBizModel() throws Exception {
        if (oc != null) {
            return;
        }
        // TODO, POC, hard code model file
        String fileName = System.getProperty("skynet.model");
        oc = ObjectCollectionHome.getHome(fileName);
    }

    private static DataSourceInfo parseBaseOnParentDataSourceInfo(String pathExpression,
            DataSourceInfo parentDataSourceInfo) throws Exception {
        if (parentDataSourceInfo == null) {
            return null;
        }
        if (!parentDataSourceInfo.canHaveChild()) {
            return null;
        }
        String[] pathExp = pathExpression.split("\\.");

        // throw new Exception("还没弄: " + pathExpression);
        DataSourceInfo parentFinalDsInfo = parentDataSourceInfo.getFinalDataSourceInfo();
        if (parentFinalDsInfo == null) {
            throw new Exception("Error when parse: " + pathExpression + ", its parrent is invalid");
        }

        if (!parentFinalDsInfo.canHaveChild()) {
            return null; // 如果不是一个正常的模型对象，跳过。
        }

        String parentTypeName = parentFinalDsInfo.getDeclaredTypeName();
        DataSourceInfo result = new DataSourceInfo();
        result.setType(DataSourceInfo.TYPE_MODEL_PATH);
        result.setDataSourceExpression(pathExpression);
        result.setVarScope(DataSourceInfo.COMPONENT_SCOPE);
        result.setVariableName(null);

        result.addChild(parentDataSourceInfo); // 本变量的起点

        DataSourceInfo pathNodeDsInfp = parentFinalDsInfo;
        for (int i = 0; i < pathExp.length; i++) {
            // _by_key_ 特殊处理
            if (pathExp[i].startsWith("_by_key_")) {
                pathNodeDsInfp = new DataSourceInfo();
                pathNodeDsInfp.setDataSourceExpression(pathExp[i].substring(8));
                pathNodeDsInfp.setType(DataSourceInfo.TYPE_MODEL_PATH_ITEM);
                pathNodeDsInfp.setVarScope(DataSourceInfo.COMPONENT_SCOPE);
                pathNodeDsInfp.setDeclaredTypeName("Object");
                pathNodeDsInfp.setModelingObject(false);
                pathNodeDsInfp.setVariableName("_by_key_");
                pathNodeDsInfp.setJavaTypeName("Object");
            } else {
                if (!isValidMember(parentTypeName, pathExp[i])) {
                    return null;
                }
                pathNodeDsInfp = createPathItemDsInfo(parentTypeName, pathExp[i]);
            }

            result.addChild(pathNodeDsInfp);

            parentTypeName = pathNodeDsInfp.getDeclaredTypeName();
        }
        result.setDeclaredTypeName(pathNodeDsInfp.getDeclaredTypeName());
        result.setVariableName(pathNodeDsInfp.getVariableName());
        result.setJavaTypeName(pathNodeDsInfp.getJavaTypeName());
        result.setList(pathNodeDsInfp.isList());
        return result;
    }

    private static DataSourceInfo createPathItemDsInfo(String parentType, String memberType) throws Exception {
        DataSourceInfo pathNodeDsInfp;
        pathNodeDsInfp = new DataSourceInfo();
        pathNodeDsInfp.setDataSourceExpression(memberType);
        pathNodeDsInfp.setType(DataSourceInfo.TYPE_MODEL_PATH_ITEM);
        pathNodeDsInfp.setVarScope(DataSourceInfo.COMPONENT_SCOPE);

        ensureBizModel();
        ObjectDescriptor od = oc.getObjByKey(parentType);
        String key = parentType + "." + memberType;
        FieldDescriptor fd = oc.getFieldByKey(key);
        if (fd != null) {
            pathNodeDsInfp.setDeclaredTypeName(fd.getType());
            pathNodeDsInfp.setModelingObject(fd.isObj());
            pathNodeDsInfp.setVariableName(fd.getCamelRunName());
            pathNodeDsInfp.setJavaTypeName(fd.getRunType("java"));
            return pathNodeDsInfp;
        }
        // 再试试是不是 list 成员
        CMRField cmrFd = findRefInOd(od, memberType);
        pathNodeDsInfp.setDeclaredTypeName(cmrFd.getType());
        pathNodeDsInfp.setModelingObject(true);
        pathNodeDsInfp.setVariableName(cmrFd.getFieldName());
        pathNodeDsInfp.setList(true);
        pathNodeDsInfp.setJavaTypeName(TextUtil.hyphenCaseToBigCamelName(cmrFd.getType()));
        return pathNodeDsInfp;
    }

    private static boolean isValidMember(String parentTypeName, String memberName) throws Exception {
        ensureBizModel();
        ObjectDescriptor od = oc.getObjByKey(parentTypeName);
        if (od == null) {
            return false; // 根都找不到，返回false
        }
        String key = parentTypeName + "." + memberName;
        FieldDescriptor fd = oc.getFieldByKey(key);
        if (fd != null) {
            return true; // 是单引用对象，返回true
        }
        // 再试试是不是 list 成员
        if (findRefInOd(od, memberName) != null) {
            return true;
        }
        return false;
    }

    private static CMRField findRefInOd(ObjectDescriptor od, String memberName) {
        for (int i = 0; i < od.getRefer().size(); i++) {
            CMRField refObj = (CMRField) od.getRefer().get(i);
            if (refObj.getType().equals(memberName)) {
                return refObj;
            }
        }
        return null;
    }

    /**
     * 分析变量类型的 var: boolean
     */
    private static DataSourceInfo parseVariableExpression(Map<String, DataSourceInfo> vartable,
            DataSourceInfo parentDataSourceInfo, String dataSourceName, String dataSourceExpression) {
        String[] inputs = dataSourceExpression.split("\\s*:\\s*");
        String typeName = inputs[1].trim().toLowerCase();
        DataSourceInfo result = new DataSourceInfo(DataSourceInfo.TYPE_JAVA_VARIABLE, supportedVariableTypes.get(typeName),
                dataSourceExpression, dataSourceName == null ? getAnonymoseVarName() : dataSourceName);
        result.setJavaTypeName(result.getDeclaredTypeName());
        result.setList(dataSourceExpression.startsWith("list:"));
        return result;
    }

    private static String getAnonymoseVarName() {
        return "__a_n_o_n_y_m_o_u_s_" + varCnt.incrementAndGet();
    }

    /**
     * 分析 xxx/$(a.b.c.d)/xxx 这种
     */
    private static final Pattern ptnVarInString = Pattern.compile("\\$\\(([a-zA-Z0-9\\._]+)\\)");

    private static DataSourceInfo parseStringConcatFunctionExpression(Map<String, DataSourceInfo> vartable,
            DataSourceInfo parentDataSourceInfo, List<DataSourceInfo> localVarTable, String dataSourceName, String dataSourceExpression,
            boolean isForGlobal) throws Exception {
        DataSourceInfo result = new DataSourceInfo(DataSourceInfo.TYPE_STRING_CONCAT_FUNCTION, "String",
                dataSourceExpression, dataSourceName == null ? getAnonymoseVarName() : dataSourceName);
        Matcher m = ptnVarInString.matcher(dataSourceExpression);
        int startPos = 0;
        while (m.find()) {
            int varStartPos = m.start();
            int varEndPos = m.end();
            if (varStartPos > startPos) {
                String constStr = dataSourceExpression.substring(startPos, varStartPos);
                DataSourceInfo constData = new DataSourceInfo(DataSourceInfo.TYPE_CONST_STRING, "String", constStr,
                        null);
                result.addChild(constData);
            }
            result.addChild(parseModelVariableExpression(vartable, parentDataSourceInfo, localVarTable, m.group(), isForGlobal));
            startPos = varEndPos;
        }
        if (startPos < dataSourceExpression.length()) {
            DataSourceInfo constData = new DataSourceInfo(DataSourceInfo.TYPE_CONST_STRING, "String",
                    dataSourceExpression.substring(startPos), null);
            result.addChild(constData);
        }
        return result;
    }

    private static final Pattern ptnModelPath = Pattern.compile("^\\$\\(([a-zA-Z0-9_\\.]+)\\)$");
    private static final Pattern ptnPageVariable = Pattern.compile("([a-zA-Z_]+)\\s*:\\s*([a-zA-Z_]+)");
    private static final Pattern ptnStringConcat = Pattern.compile("^.*\\$\\([a-zA-Z0-9_\\.]+\\).*$");

    // 分析表达式的类型
    // $(a.b.c.d) 是 EXPRESSION_TYPE_MODEL_PATH
    // xxx/$(a.b.c.d)/xxx 是 EXPRESSION_TYPE_STRING_CONCAT_FUNCTION
    // var: [boolean|string] 是 EXPRESSION_TYPE_PAGE_VARIABLE
    private static int parseExpressionType(String dataSourceExpression) throws Exception {
        Matcher m = ptnModelPath.matcher(dataSourceExpression);
        if (m.matches()) {
            System.out.println("分析表达式：" + dataSourceExpression + ", 是一个模型变量的路径表示");
            return EXPRESSION_TYPE_MODEL_PATH;
        }
        m = ptnPageVariable.matcher(dataSourceExpression);
        if (m.matches()) {
            String declarePrefix = m.group(1);
            String declareType = m.group(2);
            if ((declarePrefix.equals("var") || declarePrefix.equals("list"))&& isSupportedVarType(declareType)) {
                System.out.println("分析表达式：" + dataSourceExpression + ", 是一个ViewMode内部的" + declareType + "变量");
                return EXPRESSION_TYPE_PAGE_VARIABLE;
            }
        }
        m = ptnStringConcat.matcher(dataSourceExpression);
        if (m.matches()) {
            System.out.println("分析表达式：" + dataSourceExpression + ", 是一个字符串拼接函数");
            return EXPRESSION_TYPE_STRING_CONCAT_FUNCTION;
        }
        throw new Exception("无法分析表达式：" + dataSourceExpression);
    }

    private static boolean isSupportedVarType(String declareType) {
        return supportedVariableTypes.containsKey(declareType);
    }

    private static final Map<String, String> supportedVariableTypes = new HashMap<String, String>();
    static {
        supportedVariableTypes.put("string", "String");
        supportedVariableTypes.put("boolean", "Boolean");
        supportedVariableTypes.put("date", "Date");
        supportedVariableTypes.put("object", "Object");
    }

    public static final boolean isVariableExpression(String expr) {
        try {
            int type = parseExpressionType(expr);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}
