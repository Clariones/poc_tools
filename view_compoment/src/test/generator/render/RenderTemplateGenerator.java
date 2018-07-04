package test.generator.render;

import java.io.File;
import java.io.OutputStreamWriter;
import java.io.PrintStream;
import java.io.Writer;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.terapico.util.FileUtils;

import freemarker.template.Configuration;
import freemarker.template.Template;
import poc.utils.DebugUtil;
import poc.utils.TextUtil;
import sample.shuxiang.customer.MapUtil;
import uispec.parser.datasource.DataSourceInfo;
import uispec.parser.specelement.BaseUiSpecElement;
import uispec.parser.specelement.DataSourceUiSpec;
import uispec.parser.specelement.PageUiSpec;
import uispec.parser.specparser.DataSourceUtil;

public class RenderTemplateGenerator {
    protected String packageBaseName;
    protected File outputBaseFolder;
    protected List<PageUiSpec> pages;
    protected File templateBaseFolder;
    protected String templateFileName = "renderTemplate.java.ftl";
    protected String customTemplateFileName = "renderCustomTemplate.java.ftl";

    public File getTemplateBaseFolder() {
        return templateBaseFolder;
    }

    public void setTemplateBaseFolder(File templateBaseFolder) {
        this.templateBaseFolder = templateBaseFolder;
    }

    public String getCustomTemplateFileName() {
        return customTemplateFileName;
    }

    public void setCustomTemplateFileName(String customTemplateFileName) {
        this.customTemplateFileName = customTemplateFileName;
    }

    public String getTemplateFileName() {
        return templateFileName;
    }

    public void setTemplateFileName(String templateFileName) {
        this.templateFileName = templateFileName;
    }

    public String getPackageBaseName() {
        return packageBaseName;
    }

    public void setPackageBaseName(String packageBaseName) {
        this.packageBaseName = packageBaseName;
    }

    public File getOutputBaseFolder() {
        return outputBaseFolder;
    }

    public void setOutputBaseFolder(File outputBaseFolder) {
        this.outputBaseFolder = outputBaseFolder;
    }

    public List<PageUiSpec> getPages() {
        return pages;
    }

    public void setPages(List<PageUiSpec> pages) {
        this.pages = pages;
    }

    public Map<String, Object> preprocess() throws Exception {
        Map<String, Object> result = MapUtil.newMap(MapUtil.$("packageBase", this.getPackageBaseName()),
                MapUtil.$("userContext", "ShuxiangUserContext"));
        List<Map<String, Object>> jobs = new ArrayList<Map<String, Object>>();

        for (PageUiSpec page : pages) {
            parsePageDataSource(page);
            jobs.add(createPageJob(page));
        }
        result.put("pages", jobs);
        return result;
    }

    // 分析页面内的“数据”
    private void parsePageDataSource(PageUiSpec page) throws Exception {
        System.out.println("\n\nNow parse ui-spec " + page.getName()+":"+page.getTitle());
        Map<String, DataSourceInfo> globalVarTable = new HashMap<String, DataSourceInfo>();
        // 先分析page level的变量，所有变量最终都以此为起点
        for (BaseUiSpecElement child : page.getChildren()) {
            if (child instanceof DataSourceUiSpec) {
                try {
                    DataSourceUtil.parseGlobalExpression(globalVarTable, null,
                            ((DataSourceUiSpec) child).getVariableName(),
                            ((DataSourceUiSpec) child).getDataSourceExpression());
                } catch (Exception e) {
                    e.printStackTrace();
                    throw new Exception("Error when parse data-source at line " + child.getElementDeclaredLineNumber(),
                            e);
                }
            }
        }
        page.setVariableTable(globalVarTable);
        page.initImportedList();
        // 然后开始分析各级组件内包含的变量
        for (BaseUiSpecElement child : page.getChildren()) {
            parseDataSource(page, globalVarTable, null, child);
        }
    }

    /**
     * 分析一个UI Spec单元的数据源
     * @param page 
     * 
     * @param globalVarTable
     * @param closestParentDataInfo
     * @param curUiSpec
     * @throws Exception
     */
    private void parseDataSource(PageUiSpec page, Map<String, DataSourceInfo> globalVarTable, DataSourceInfo closestParentDataInfo,
            BaseUiSpecElement curUiSpec) throws Exception {
        // 先看本组件是否有需要分析的变量
        List<String> exprList = curUiSpec.getDataSourceExpressionList();
        curUiSpec.setAncestDataSourceInfo(closestParentDataInfo);
        List<DataSourceInfo> dsInfoList = null;
        if (exprList != null && !exprList.isEmpty()) {
            try {
                dsInfoList = parseDataSourceOfUiSpec(page, globalVarTable, closestParentDataInfo, exprList);
                curUiSpec.setDataSourceExpressionResultList(dsInfoList);
                if (dsInfoList!=null && dsInfoList.size()>0) {
                    for(DataSourceInfo dsInfoItem: dsInfoList) {
                        page.markAsImportedIfNeeded(dsInfoItem);
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
                throw new Exception("Error when parse data-source at line " + curUiSpec.getElementDeclaredLineNumber(),
                        e);
            }
        }

        Map<String, String> aothExpressions = curUiSpec.getAdditonalExpressions();
        if (aothExpressions != null && !aothExpressions.isEmpty()) {
            for (String key : aothExpressions.keySet()) {
                String value = aothExpressions.get(key);
                System.out.println("parse " + key + "=" + value + " at line " + curUiSpec.getElementDeclaredLineNumber());
                DataSourceInfo dsInfo = null;
                if (!DataSourceUtil.isVariableExpression(value)) {
                    dsInfo = new DataSourceInfo(DataSourceInfo.TYPE_CONST_STRING, "string", value, null);
                } else {
                    try {
                    dsInfo = DataSourceUtil.parseExpression(page, globalVarTable, closestParentDataInfo, dsInfoList, value);
                    }catch(Exception e) {
                        throw new Exception(" at line " + curUiSpec.getElementDeclaredLineNumber(), e); 
                    }
                    if (dsInfo == null) {
                        throw new Exception("Cannot parse data-source " + value +" at line " + curUiSpec.getElementDeclaredLineNumber());
                    }
                }
                if (!curUiSpec.setAdditionalDataSourceInfo(key, dsInfo)) {
                    throw new Exception("No one handle additional data-source " + key + " in ui-spec-"
                            + curUiSpec.getElementTypeName());
                }
                page.markAsImportedIfNeeded(dsInfo);
            }
        }

        if (curUiSpec.getChildren() == null) {
            return;
        }
        DataSourceInfo curDataSrc = curUiSpec.getBindedDataSourceInfo();
        if (curDataSrc != null) {
            closestParentDataInfo = curDataSrc;
        }
        for (BaseUiSpecElement child : curUiSpec.getChildren()) {
            parseDataSource(page, globalVarTable, closestParentDataInfo, child);
        }
    }

    private List<DataSourceInfo> parseDataSourceOfUiSpec(PageUiSpec page, Map<String, DataSourceInfo> globalVarTable,
            DataSourceInfo closestParentDataInfo, List<String> exprList) throws Exception {
        List<DataSourceInfo> localVarTable = new ArrayList<DataSourceInfo>();
        for (String dsExpr : exprList) {
            DataSourceInfo dsInfo = DataSourceUtil.parseExpression(page, globalVarTable, closestParentDataInfo, localVarTable,
                    dsExpr);
            if (dsInfo == null) {
                throw new Exception("Cannot parse data-source " + dsExpr);
            }
            localVarTable.add(dsInfo);
            page.markAsImportedIfNeeded(dsInfo);
        }
        return localVarTable;
    }

    private Map<String, Object> createPageJob(PageUiSpec page) {
        RenderTemplatePreprocessContext jobContext = new RenderTemplatePreprocessContext();
        List<BaseUiSpecElement> memberElements = new ArrayList<BaseUiSpecElement>();

        // page 没有child我就直接抛空指针
        List<BaseUiSpecElement> children = page.getChildren();
        page.initImportedList();
        for (BaseUiSpecElement child : children) {
            createUiSpecElementTask(jobContext, memberElements, page, child);
        }

        return MapUtil.newMap(MapUtil.$("page", page), MapUtil.$("members", memberElements),
                MapUtil.$("className", TextUtil.hyphenCaseToBigCamelName(page.getName())),
                MapUtil.$("viewModelName", TextUtil.hyphenCaseToBigCamelName(page.getName()) + "ViewModel"));
    }

    private void createUiSpecElementTask(RenderTemplatePreprocessContext jobContext,
            List<BaseUiSpecElement> memberElements, PageUiSpec page, BaseUiSpecElement element) {
        // DebugUtil.dumpObjectToJson("当前处理的的元素：", element);
        if (!element.isShouldBeRender()) {
            return;
        }
        
        String renderMethodName = RUtils.calcElementRenderName(jobContext, element);
        Map<String, Object> jobInfo = MapUtil.newMap(MapUtil.$("methodName", renderMethodName),
                MapUtil.$("isDynamic", false));
        if (element.getLinkToDataSourceInfo() != null) {
            jobInfo.put("linkTo", element.getLinkToDataSourceInfo());
            jobInfo.put("hasLinkUrl", true);
        } else {
            jobInfo.put("hasLinkUrl", false);
        }
        if (element.getAncestDataSourceInfo() != null) {
            jobInfo.put("hasInputData", true);
            jobInfo.put("inputDataTypeName",
                    TextUtil.hyphenCaseToBigCamelName(element.getAncestDataSourceInfo().getDeclaredTypeName()));
        } else {
            jobInfo.put("hasInputData", false);
        }

        if (element.getBindedDataSourceInfo() != null) {
//            String localVarBaseTypeName = TextUtil
//                    .hyphenCaseToBigCamelName(element.getBindedDataSourceInfo().getDeclaredTypeName());
            String localVarBaseTypeName = element.getBindedDataSourceInfo().getJavaTypeName();
            if (element.isSelfHanleListInput()) {
                jobInfo.put("listRenderingMethod", false);
            } else if (element.getBindedDataSourceInfo().isList()) {
                jobInfo.put("listRenderingMethod", true);
            } else {
                jobInfo.put("listRenderingMethod", false);
            }
            jobInfo.put("hasLocalVariable", true);
            jobInfo.put("localVariableTypeName", localVarBaseTypeName);
        } else {
            jobInfo.put("listRenderingMethod", false);
            jobInfo.put("hasLocalVariable", false);
            jobInfo.put("localVariableTypeName", "// 没变量");
        }

        // getDataXXXFromViewModel 方法需要的参数
        if ((boolean) jobInfo.get("hasLocalVariable")) {
//            String typeName = TextUtil
//                    .hyphenCaseToBigCamelName(element.getBindedDataSourceInfo().getDeclaredTypeName());
            String typeName = element.getBindedDataSourceInfo().getJavaTypeName();
            if (element.isSelfHanleListInput()) {
                jobInfo.put("localDataTypeName", "List<" + typeName + ">");
                jobInfo.put("localDataDeclare", ", List<" + typeName + "> data");
                jobInfo.put("localDataCheckDeclare", ", List<" + typeName + "> data");
                jobInfo.put("localDataVar", ", data");
            } else if (element.getBindedDataSourceInfo().isList()) {
                jobInfo.put("localDataTypeName", "List<" + typeName + ">");
                jobInfo.put("localDataDeclare", ", List<" + typeName + "> data");
                jobInfo.put("localDataCheckDeclare", ", " + typeName + " data");
                jobInfo.put("localDataVar", ", data");
            } else {
                jobInfo.put("localDataTypeName", typeName);
                jobInfo.put("localDataDeclare", ", " + typeName + " data");
                jobInfo.put("localDataCheckDeclare", ", " + typeName + " data");
                jobInfo.put("localDataVar", ", data");
            }
        } else {
            jobInfo.put("localDataTypeName", "");
            jobInfo.put("localDataDeclare", "");
            jobInfo.put("localDataCheckDeclare", "");
            jobInfo.put("localDataVar", "");
        }
        // System.out.println("local==>" +
        // jobInfo.get("hasLocalVariable")+":"+jobInfo.get("localDataDeclare")+" at line
        // " + element.getElementDeclaredLineNumber());
        element.setJobInfo(jobInfo);

        memberElements.add(element);

        // process children element
        if (element.getChildren() == null || element.getChildren().isEmpty()) {
            return;
        }
        for (BaseUiSpecElement child : element.getChildren()) {
            createUiSpecElementTask(jobContext, memberElements, page, child);
        }
    }

    public void produce(Map<String, Object> data, Writer out) throws Exception {
        Configuration config = new Configuration(Configuration.VERSION_2_3_28);
        config.setDirectoryForTemplateLoading(getTemplateBaseFolder());
        config.setDefaultEncoding("UTF-8");
        Template template = config.getTemplate(getTemplateFileName());
        Template template4Custom = config.getTemplate(this.getCustomTemplateFileName());

        List<Object> pages = (List<Object>) data.get("pages");
        boolean writeToSeperateFile = out == null;
        for (Object page : pages) {
            // base render
            data.put("pageSpec", page);
            if (writeToSeperateFile) {
                Map<String, Object> pageJob = (Map<String, Object>) page;
                String fileName = pageJob.get("className") + "BaseRender.java";
                File outputFile = new File(this.getOutputBaseFolder(), fileName);
                System.out.println("==> Will write to " + outputFile.getAbsolutePath());
                PrintStream fPrinter = FileUtils.createFileForPrint(outputFile);
                out = new OutputStreamWriter(fPrinter);
            }
            template.process(data, out);
            out.flush();
            if (writeToSeperateFile) {
                out.close();
            }
            
            // view model
            // TODO
            
            // custom render
            if (writeToSeperateFile) {
                Map<String, Object> pageJob = (Map<String, Object>) page;
                String fileName = pageJob.get("className") + "Render.java";
                File outputFile = new File(this.getOutputBaseFolder(), fileName);
                if (outputFile.exists()) {
                    System.out.println("==> File existes, skip: " + outputFile.getAbsolutePath());
                    continue;
                }
                System.out.println("==> Will into " + outputFile.getAbsolutePath());
                PrintStream fPrinter = FileUtils.createFileForPrint(outputFile);
                out = new OutputStreamWriter(fPrinter);
            }
            template4Custom.process(data, out);
            out.flush();
            if (writeToSeperateFile) {
                out.close();
            }
        }
    }

}
