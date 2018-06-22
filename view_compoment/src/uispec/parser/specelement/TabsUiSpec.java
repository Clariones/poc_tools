package uispec.parser.specelement;

import java.util.HashMap;
import java.util.Map;

import poc.utils.TextUtil;
import uispec.parser.datasource.DataSourceInfo;

public class TabsUiSpec extends BaseUiSpecElement {
    protected String classSelected;
    protected String classUnselected;
    protected String activeTab;
    protected DataSourceInfo activeTabDataSourceInfo;
    

    public DataSourceInfo getActiveTabDataSourceInfo() {
        return activeTabDataSourceInfo;
    }

    public void setActiveTabDataSourceInfo(DataSourceInfo activeTabDataSourceInfo) {
        this.activeTabDataSourceInfo = activeTabDataSourceInfo;
    }

    public String getActiveTab() {
        return activeTab;
    }

    public void setActiveTab(String activeTab) {
        this.activeTab = activeTab;
    }

    public String getClassSelected() {
        return classSelected;
    }

    public void setClassSelected(String classSelected) {
        this.classSelected = classSelected;
    }

    public String getClassUnselected() {
        return classUnselected;
    }

    public void setClassUnselected(String classUnselected) {
        this.classUnselected = classUnselected;
    }

    @Override
    public Map<String, String> getAdditonalExpressions() {
        Map<String, String> result = new HashMap<String, String>();
        if (!TextUtil.isBlank(this.getLinkToUrl())) {
            result.put("linkToUrl", this.getLinkToUrl());
        }
        if (!TextUtil.isBlank(this.getActiveTab())) {
            result.put("activeTab", this.getActiveTab());
        }
        return result;
    }

    @Override
    public boolean setAdditionalDataSourceInfo(String key, DataSourceInfo dsInfo) {
        // TODO Auto-generated method stub
        if (super.setAdditionalDataSourceInfo(key, dsInfo)) {
            return true;
        }
        if (key.equals("activeTab")) {
            this.setActiveTabDataSourceInfo(dsInfo);
            return true;
        }
        return false;
    }

}
