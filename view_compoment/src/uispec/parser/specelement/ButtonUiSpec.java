package uispec.parser.specelement;

import java.util.HashMap;
import java.util.Map;

import poc.utils.TextUtil;
import uispec.parser.datasource.DataSourceInfo;

public class ButtonUiSpec extends BaseUiSpecElement {
    protected String level;
    protected String type;
    protected String sharingTitle;
    protected String callBackUrl;
    protected String imageUrl;
    protected DataSourceInfo callBackUrlDataSourceInfo;
    protected DataSourceInfo imageUrlDataSourceInfo;
    protected DataSourceInfo sharingTitleDataSourceInfo;
    
    
    public DataSourceInfo getSharingTitleDataSourceInfo() {
        return sharingTitleDataSourceInfo;
    }

    public void setSharingTitleDataSourceInfo(DataSourceInfo sharingTitleDataSourceInfo) {
        this.sharingTitleDataSourceInfo = sharingTitleDataSourceInfo;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public DataSourceInfo getImageUrlDataSourceInfo() {
        return imageUrlDataSourceInfo;
    }

    public void setImageUrlDataSourceInfo(DataSourceInfo imageUrlDataSourceInfo) {
        this.imageUrlDataSourceInfo = imageUrlDataSourceInfo;
    }

    public DataSourceInfo getCallBackUrlDataSourceInfo() {
        return callBackUrlDataSourceInfo;
    }

    public void setCallBackUrlDataSourceInfo(DataSourceInfo callbackDataSourceInfo) {
        this.callBackUrlDataSourceInfo = callbackDataSourceInfo;
    }

    public String getSharingTitle() {
        return sharingTitle;
    }

    public void setSharingTitle(String sharingTitle) {
        this.sharingTitle = sharingTitle;
    }

    public String getCallBackUrl() {
        return callBackUrl;
    }

    public void setCallBackUrl(String callBackUrl) {
        this.callBackUrl = callBackUrl;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Override
    public Map<String, String> getAdditonalExpressions() {
        Map<String, String> result = new HashMap<String, String>();
        if (!TextUtil.isBlank(this.getLinkToUrl())) {
            result.put("linkToUrl", this.getLinkToUrl());
        }
        if (!TextUtil.isBlank(this.getCallBackUrl())) {
            result.put("callbackUrl", this.getCallBackUrl());
        }
        if (!TextUtil.isBlank(this.getImageUrl())) {
            result.put("imageUrl", this.getImageUrl());
        }
        if (!TextUtil.isBlank(this.getSharingTitle())) {
            result.put("shareTitle", this.getSharingTitle());
        }
        return result;
    }

    @Override
    public boolean setAdditionalDataSourceInfo(String key, DataSourceInfo dsInfo) {
        // TODO Auto-generated method stub
        if (super.setAdditionalDataSourceInfo(key, dsInfo)) {
            return true;
        }
        if (key.equals("callbackUrl")) {
            this.setCallBackUrlDataSourceInfo(dsInfo);
            return true;
        }
        if (key.equals("imageUrl")) {
            this.setImageUrlDataSourceInfo(dsInfo);
            return true;
        }
        if (key.equals("shareTitle")) {
            this.setSharingTitleDataSourceInfo(dsInfo);
            return true;
        }
        return false;
    }

}
