package sample.shuxiang.customer;

import viewcomponent.BaseViewComponent;

/**
 * 
 * @author clariones
 * 自定义的组件
 */
public class CampaignSummaryViewComponent extends BaseViewComponent {

    public CampaignSummaryViewComponent() {
	this(null);
    }
    
    public CampaignSummaryViewComponent(DummyCampaignInfo content) {
	this(content, null);
    }
    
    public CampaignSummaryViewComponent(DummyCampaignInfo content, String classes) {
	this(content, classes, null);
    }
    
    public CampaignSummaryViewComponent(DummyCampaignInfo content, String classes, String tag) {
	this.setContent(content);
	this.setClasses(classes);
	this.setTag(tag);
	this.setComponentType("campaign-summary");
    }
}
