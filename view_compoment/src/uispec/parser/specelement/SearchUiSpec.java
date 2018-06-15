package uispec.parser.specelement;

import java.util.HashMap;
import java.util.Map;

import poc.utils.TextUtil;
import uispec.parser.datasource.DataSourceInfo;

public class SearchUiSpec extends BaseUiSpecElement {
    protected String apiUrl;
    protected DataSourceInfo apiUrlDataSourceInfo;

    
    public DataSourceInfo getApiUrlDataSourceInfo() {
        return apiUrlDataSourceInfo;
    }

    public void setApiUrlDataSourceInfo(DataSourceInfo apiUrlDataSourceInfo) {
        this.apiUrlDataSourceInfo = apiUrlDataSourceInfo;
    }

    public String getApiUrl() {
        return apiUrl;
    }

    public void setApiUrl(String apiUrl) {
        this.apiUrl = apiUrl;
    }

    @Override
    public Map<String, String> getAdditonalExpressions() {
	Map<String, String> result = new HashMap<String, String>();
	if (!TextUtil.isBlank(this.getLinkToUrl())) {
	    result.put("linkToUrl", this.getLinkToUrl());
	}
	if (!TextUtil.isBlank(this.getApiUrl())) {
	    result.put("apiUrl", this.getApiUrl());
	}
	return result;
    }

    @Override
    public boolean setAdditionalDataSourceInfo(String key, DataSourceInfo dsInfo) {
	// TODO Auto-generated method stub
	if( super.setAdditionalDataSourceInfo(key, dsInfo)) {
	    return true;
	}
	if (key.equals("apiUrl")) {
	    this.setApiUrlDataSourceInfo(dsInfo);
	    return true;
	}
	return false;
    }
    
    
}
