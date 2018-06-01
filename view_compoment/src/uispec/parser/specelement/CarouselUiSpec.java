package uispec.parser.specelement;

import java.util.Arrays;
import java.util.List;

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
    
    public List<String> getDataSourceExpressionList() {
	return Arrays.asList(dataSourceImage, dataSourceLink, dataSourceTips);
    }
}
