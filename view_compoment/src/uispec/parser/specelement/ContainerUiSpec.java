package uispec.parser.specelement;

import java.util.HashMap;
import java.util.Map;

import poc.utils.TextUtil;
import uispec.parser.datasource.DataSourceInfo;

public class ContainerUiSpec extends BaseUiSpecElement {
    protected String targetId;
    protected DataSourceInfo targetIdDataSourceInfo;
    
    
    public DataSourceInfo getTargetIdDataSourceInfo() {
        return targetIdDataSourceInfo;
    }

    public void setTargetIdDataSourceInfo(DataSourceInfo targetIdDataSourceInfo) {
        this.targetIdDataSourceInfo = targetIdDataSourceInfo;
    }

    public String getTargetId() {
        return targetId;
    }

    public void setTargetId(String targetId) {
        this.targetId = targetId;
    }
    
    @Override
    public Map<String, String> getAdditonalExpressions() {
        Map<String, String> result = new HashMap<String, String>();
        if (!TextUtil.isBlank(this.getLinkToUrl())) {
            result.put("linkToUrl", this.getLinkToUrl());
        }
        if (!TextUtil.isBlank(this.getTargetId())) {
            result.put("targetId", this.getTargetId());
        }
        return result;
    }

    @Override
    public boolean setAdditionalDataSourceInfo(String key, DataSourceInfo dsInfo) {
        // TODO Auto-generated method stub
        if (super.setAdditionalDataSourceInfo(key, dsInfo)) {
            return true;
        }
        if (key.equals("targetId")) {
            this.setTargetIdDataSourceInfo(dsInfo);
            return true;
        }
        return false;
    }
}
