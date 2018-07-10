package uispec.parser.specelement;

import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

import uispec.parser.datasource.DataSourceInfo;

public class PageUiSpec extends BaseUiSpecElement {
    protected String title;
    protected String name;
    protected Set<String> importedModels;
    protected Map<String, DataSourceInfo> variableTable;
    protected String frontColor;
    protected String backgroundColor;
    
    
    public String getFrontColor() {
        return frontColor;
    }

    public void setFrontColor(String frontColor) {
        this.frontColor = frontColor;
    }

    public String getBackgroundColor() {
        return backgroundColor;
    }

    public void setBackgroundColor(String backgroundColor) {
        this.backgroundColor = backgroundColor;
    }

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

    
    public Set<String> getImportedModels() {
        return importedModels;
    }

    public void setImportedModels(Set<String> importedModels) {
        this.importedModels = importedModels;
    }

    public void initImportedList() { 
        if (importedModels == null) {
        importedModels = new TreeSet<String>();
        }
        if (this.getVariableTable() == null) {
            return;
        }
        for(DataSourceInfo var : getVariableTable().values()) {
            markAsImportedIfNeeded(var);
        }
    }

    public void markAsImportedIfNeeded(DataSourceInfo var) {
        System.out.println("===> add data-source " + var.getType() + " " + var.getJavaTypeName() + ": "
                + var.getVariableName() + "," + var.isModelingObject());

        if (!"_by_key_".equals(var.getVariableName())) {
            if (var.isModelingObject()) {
                importedModels.add(var.getJavaTypeName());
                System.out.println("===> now is :" + importedModels);
            }
        }

        if (var.getChildren() == null) {
            return;
        }
        for (DataSourceInfo item : var.getChildren()) {
            markAsImportedIfNeeded(item);
        }
    }
    
    
}
