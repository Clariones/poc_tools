package uispec.parser.datasource;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * xxxInfo 是元数据的意思。 DataSourceInfo 就是指元素的动态数据的元数据。 变量有两种作用域：页面 和 元素。
 * 变量有两种‘命名’：匿名变量，和 命名变量。
 * 变量的来源类型有几种：立即数（常亮）、业务模型内定义的类型、某种Java数据类型（通常是bool和string）、函数（通常是复杂规则的抽象）
 * 变量的结果就是一个Object，用于判断是否需要渲染，立即值等。
 * 
 * @author clariones
 *
 */
public class DataSourceInfo {
    public static final String TYPE_CONST_STRING = "const_string";
    public static final String TYPE_MODEL_PATH = "model_path";
    public static final String TYPE_JAVA_VARIABLE = "java_variable";
    public static final String TYPE_STRING_CONCAT_FUNCTION = "string_concat_function";
    public static final String TYPE_MODEL_PATH_ITEM = "model_path_item";

    public static final String PAGE_SCOPE = "page";
    public static final String COMPONENT_SCOPE = "component";

    /** 变量有两种‘命名’：匿名变量，和 命名变量。 null表示匿名 */
    protected String variableName;
    /** 变量的来源类型有几种：立即数（常亮）、业务模型内定义的类型、某种Java数据类型（通常是bool和string）、函数（通常是复杂规则的抽象） */
    protected String type;
    /** 变量的类型。对于字符串，是string，对于变量，是var后的声明。 对于模型变量，是模型中的xml元素的名字 */
    protected String declaredTypeName;
    protected String dataSourceExpression;
    /** 变量有两种作用域：页面 和 元素。page/local */
    protected String varScope;
    protected boolean isList = false;
    protected boolean isModelingObject = false;

    protected String javaTypeName = "String";

    public String getJavaTypeName() {
        return javaTypeName;
    }

    public void setJavaTypeName(String javaTypeName) {
        this.javaTypeName = javaTypeName;
    }

    public boolean isModelingObject() {
        return isModelingObject;
    }

    public void setModelingObject(boolean isModelingObject) {
        this.isModelingObject = isModelingObject;
    }

    public boolean isList() {
        return isList;
    }

    public void setList(boolean isList) {
        this.isList = isList;
    }

    public String getVarScope() {
        return varScope;
    }

    public void setVarScope(String varScope) {
        this.varScope = varScope;
    }

    public String getDataSourceExpression() {
        return dataSourceExpression;
    }

    public void setDataSourceExpression(String dataSourceExpression) {
        this.dataSourceExpression = dataSourceExpression;
    }

    @JsonIgnore
    protected List<DataSourceInfo> children;

    public String getVariableName() {
        return variableName;
    }

    public void setVariableName(String variableName) {
        this.variableName = variableName;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getDeclaredTypeName() {
        return declaredTypeName;
    }

    public void setDeclaredTypeName(String declaredTypeName) {
        this.declaredTypeName = declaredTypeName;
    }

    public List<DataSourceInfo> getChildren() {
        return children;
    }

    public void setChildren(List<DataSourceInfo> children) {
        this.children = children;
    }

    public void addChild(DataSourceInfo child) {
        ensureChildren();
        children.add(child);
    }

    private void ensureChildren() {
        if (children != null) {
            return;
        }
        children = new ArrayList<DataSourceInfo>();
    }

    public DataSourceInfo() {
        super();
    }

    public DataSourceInfo(String type, String declaredTypeName, String dataSourceExpression, String variableName) {
        this();
        this.type = type;
        this.declaredTypeName = declaredTypeName;
        this.dataSourceExpression = dataSourceExpression;
        this.variableName = variableName;
    }

    public boolean canHaveChild() {
        if (TYPE_MODEL_PATH.equals(this.getType())) {
            return true;
        }
        if (this.isModelingObject) {
            return true;
        }
        return false;
    }

    public DataSourceInfo getFinalDataSourceInfo() {
        if (this.getChildren() == null || this.getChildren().isEmpty()) {
            return this;
        }
        return this.getChildren().get(this.getChildren().size() - 1);
    }

    @Override
    public String toString() {
        return "DataSourceInfo [dataSourceExpression=" + dataSourceExpression + ", variableName=" + variableName
                + ", type=" + type + ", declaredTypeName=" + declaredTypeName + ", varScope=" + varScope + "]";
    }

}
