package sample.shuxiang.customer;

import java.util.Date;

import sample.shuxiang.ViewPageFactory;
import sample.shuxiang.deprecatedcomponent.HorizontalContainerViewComponent;
import sample.shuxiang.deprecatedcomponent.LinkViewComponent;
import sample.shuxiang.deprecatedcomponent.VerticalContainerViewComponent;
import viewcomponent.BaseViewComponent;
import viewcomponent.CarouselViewComponent;
import viewcomponent.ContainerViewComponent;
import viewcomponent.ImageViewComponent;
import viewcomponent.PageViewComponent;
import viewcomponent.SearchViewCompoenet;
import viewcomponent.TextViewComponent;

public class HomePage implements ViewPageFactory {

    @Override
    public PageViewComponent produce() {
	PageViewComponent me = new PageViewComponent("书香社区");
	produceHeaderBar(me);
	produceCarrousal(me);
	productCampaignHeader(me);
	productCampaingList(me);
	return me;
    }

    private BaseViewComponent produceCarrousal(PageViewComponent me) {
	BaseViewComponent carrousel = me.newChild(new CarouselViewComponent());

	carrousel.newChild(new LinkViewComponent("/shuxiang/bookManager/viewBook/B00001"))
		.newChild(new ImageViewComponent(
			"https://tse2-mm.cn.bing.net/th?id=OIP.2rm8Vw7E0ObPs0RJAmJq6AHaEo&w=300&h=187&c=7&o=5&dpr=2&pid=1.7",
			null, false));
	carrousel.newChild(new LinkViewComponent("/shuxiang/bookManager/viewBook/B00002"))
		.newChild(new ImageViewComponent(
			"https://tse3-mm.cn.bing.net/th?id=OIP.H8dUYDEff27VZNsPT-qWqAHaCx&w=300&h=112&c=7&o=5&dpr=2&pid=1.7",
			null, false));
	carrousel.newChild(new LinkViewComponent("/shuxiang/bookManager/viewBook/B00003"))
		.newChild(new ImageViewComponent(
			"https://tse4-mm.cn.bing.net/th?id=OIP.a83Ypgxw8vigynhvdXjLygHaFV&w=287&h=199&c=7&o=5&dpr=2&pid=1.7",
			null, false));
	return carrousel;
    }

    private BaseViewComponent produceHeaderBar(PageViewComponent me) {
	return me.newChild(new ContainerViewComponent()).addChild(new TextViewComponent("慕和南道四期店"))
		.addChild(new LinkViewComponent("/shuxiang/storeManager/selectStores/customer0001/", "btn btn-primary")
			.addChild(new TextViewComponent("切换")))
		.addChild(
			new SearchViewCompoenet("搜索", "/shuxiang/storeManager/searchStore/", "search-bar pull-right"));
    }

    private BaseViewComponent productCampaignHeader(PageViewComponent me) {
	return me.newChild(new HorizontalContainerViewComponent()).addChild(new TextViewComponent("活动", "title_level2"))
		.addChild(new LinkViewComponent("/shuxiang/campaignManager/signUp/C00002/", "link link-more")
			.addChild(new TextViewComponent("报名")));
    }

    private BaseViewComponent productCampaingList(PageViewComponent me) {
	BaseViewComponent list = me.newChild(new VerticalContainerViewComponent());
	list.addChild(new CampaignSummaryViewComponent(new DummyCampaignInfo("读书会活动1",
		"https://tse4-mm.cn.bing.net/th?id=OIP.VjX0yDaZWJT4WiZuN-LK9gHaLH&w=125&h=187&c=7&o=5&dpr=2&pid=1.7",
		"/shuxiang/campaignManager/signUp/C00001/", new Date(), "华府大道一段108号益州国际广场505", "火热报名中", 15, 20)))
		.addChild(new CampaignSummaryViewComponent(new DummyCampaignInfo("读书会活动2",
			"https://tse4-mm.cn.bing.net/th?id=OIP.VjX0yDaZWJT4WiZuN-LK9gHaLH&w=125&h=187&c=7&o=5&dpr=2&pid=1.7",
			"/shuxiang/campaignManager/signUp/C00001/", new Date(), "华府大道一段108号益州国际广场505", "火热报名中", 10,
			20)))
		.addChild(new CampaignSummaryViewComponent(new DummyCampaignInfo("读书会活动3",
			"https://tse4-mm.cn.bing.net/th?id=OIP.VjX0yDaZWJT4WiZuN-LK9gHaLH&w=125&h=187&c=7&o=5&dpr=2&pid=1.7",
			"/shuxiang/campaignManager/signUp/C00001/", new Date(), "华府大道一段108号益州国际广场505", "筹备中", 0, 20)))
		.addChild(new CampaignSummaryViewComponent(new DummyCampaignInfo("读书会活动4",
			"https://tse4-mm.cn.bing.net/th?id=OIP.VjX0yDaZWJT4WiZuN-LK9gHaLH&w=125&h=187&c=7&o=5&dpr=2&pid=1.7",
			"/shuxiang/campaignManager/signUp/C00001/", new Date(), "华府大道一段108号益州国际广场505", "名额已满", 20,
			20)));

	return list;
    }
}
