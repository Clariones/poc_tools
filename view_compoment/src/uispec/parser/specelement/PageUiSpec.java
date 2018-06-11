package uispec.parser.specelement;

import java.util.Map;

import uispec.parser.datasource.DataSourceInfo;

public class PageUiSpec extends BaseUiSpecElement {
    protected String title;
    protected String name;
    
    protected Map<String, DataSourceInfo> variableTable;
    
    
    public Map<String, DataSourceInfo> getVariableTable() {
        return variableTable;
    }

    public void setVariableTable(Map<String, DataSourceInfo> variableTable) {
        this.variableTable = variableTable;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
    
    
}
