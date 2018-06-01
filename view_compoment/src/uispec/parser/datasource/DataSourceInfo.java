package uispec.parser.datasource;

import java.util.List;

public class DataSourceInfo {
    public static final String TYPE_FUNCTION = "function";
    public static final String TYPE_PROJECT_MODEL = "model";
    public static final String TYPE_JAVA_CLASS = "java";
    public static final String TYPE_VIEW_MODEL = "view-model";
    protected String variableName;
    protected String type;
    protected String declaredTypeName;
    
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
    
    
}
