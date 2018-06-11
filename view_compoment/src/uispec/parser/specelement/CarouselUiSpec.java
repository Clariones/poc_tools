package uispec.parser.specelement;

import java.util.Arrays;
import java.util.List;

import uispec.parser.datasource.DataSourceInfo;

public class CarouselUiSpec extends BaseUiSpecElement {
    protected String dataSourceImage;
    protected String dataSourceLink;
    protected String dataSourceTips;
    public String getDataSourceImage() {
        return dataSourceImage;
    }
    public void setDataSourceImage(String dataSourceImage) {
        this.dataSourceImage = dataSourceImage;
    }
    public String getDataSourceLink() {
        return dataSourceLink;
    }
    public void setDataSourceLink(String dataSourceLink) {
        this.dataSourceLink = dataSourceLink;
    }
    public String getDataSourceTips() {
        return dataSourceTips;
    }
    public void setDataSourceTips(String dataSourceTips) {
        this.dataSourceTips = dataSourceTips;
    }
    
    protected DataSourceInfo linkDataSrc;
    protected DataSourceInfo imageDataSrc;
    protected DataSourceInfo tipsDataSrc;
    
    
    public DataSourceInfo getLinkDataSrc() {
        return linkDataSrc;
    }
    public void setLinkDataSrc(DataSourceInfo linkDataSrc) {
        this.linkDataSrc = linkDataSrc;
    }
    public DataSourceInfo getImageDataSrc() {
        return imageDataSrc;
    }
    public void setImageDataSrc(DataSourceInfo imageDataSrc) {
        this.imageDataSrc = imageDataSrc;
    }
    public DataSourceInfo getTipsDataSrc() {
        return tipsDataSrc;
    }
    public void setTipsDataSrc(DataSourceInfo tipsDataSrc) {
        this.tipsDataSrc = tipsDataSrc;
    }
    /**
     * 有多个数据源表达式的组件，需要重载这个函数，例如 carouselUiSpec
     * @return
     */
    @Override
    public List<String> getDataSourceExpressionList() {
	return Arrays.asList(this.getDataSource(), dataSourceLink, dataSourceImage, dataSourceTips);
    }
    /**
     * 有多个数据源表达式的组件，需要重载这个函数，例如 carouselUiSpec
     * @return
     */
    @Override
    public void setDataSourceExpressionResultList(List<DataSourceInfo> dataSourceInfoList) {
	if (dataSourceInfoList == null  || dataSourceInfoList.isEmpty()) {
	    bindedDataSourceInfo = null;
	    return;
	}
	bindedDataSourceInfo = dataSourceInfoList.get(0);
	linkDataSrc = dataSourceInfoList.get(1);
	imageDataSrc = dataSourceInfoList.get(2);
	tipsDataSrc = dataSourceInfoList.get(3);
    }
}
