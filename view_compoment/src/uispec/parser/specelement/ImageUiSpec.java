package uispec.parser.specelement;

import java.util.HashMap;
import java.util.Map;

import poc.utils.TextUtil;
import uispec.parser.datasource.DataSourceInfo;

public class ImageUiSpec extends BaseUiSpecElement {
    protected String srcUrl;
    protected DataSourceInfo srcUrlDataSourceInfo;
    protected boolean clickToViewBig = false;
    
    
    public boolean isClickToViewBig() {
        return clickToViewBig;
    }

    public void setClickToViewBig(boolean clickToViewBig) {
        this.clickToViewBig = clickToViewBig;
    }

    public String getSrcUrl() {
        if (srcUrl == null) {
            return this.getDataSource();
        }
        return srcUrl;
    }

    public void setSrcUrl(String srcUrl) {
        this.srcUrl = srcUrl;
    }

    public DataSourceInfo getSrcUrlDataSourceInfo() {
        return srcUrlDataSourceInfo;
    }

    public void setSrcUrlDataSourceInfo(DataSourceInfo srcUrlDataSourceInfo) {
        this.srcUrlDataSourceInfo = srcUrlDataSourceInfo;
    }

    public ImageUiSpec() {
        super();
        this.setIgnoreWhenGeneration(false);
    }

    @Override
    public Map<String, String> getAdditonalExpressions() {
        Map<String, String> result = new HashMap<String, String>();
        if (!TextUtil.isBlank(this.getLinkToUrl())) {
            result.put("linkToUrl", this.getLinkToUrl());
        }
        if (!TextUtil.isBlank(this.getSrcUrl())) {
            result.put("srcUrl", this.getSrcUrl());
        }
        return result;
    }

    @Override
    public boolean setAdditionalDataSourceInfo(String key, DataSourceInfo dsInfo) {
        // TODO Auto-generated method stub
        if (super.setAdditionalDataSourceInfo(key, dsInfo)) {
            return true;
        }
        if (key.equals("srcUrl")) {
            this.setSrcUrlDataSourceInfo(dsInfo);
            return true;
        }
        return false;
    }

    @Override
    protected String getBindedClass() {
        return "image--default";
    }
    
    
}
