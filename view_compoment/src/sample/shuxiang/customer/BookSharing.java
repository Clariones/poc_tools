package sample.shuxiang.customer;

import sample.shuxiang.ViewPageFactory;
import sample.shuxiang.deprecatedcomponent.LinkViewComponent;
import sample.shuxiang.deprecatedcomponent.VerticalContainerViewComponent;
import viewcomponent.BaseViewComponent;
import viewcomponent.ContainerViewComponent;
import viewcomponent.FormActionViewComponent;
import viewcomponent.FormFieldViewComponent;
import viewcomponent.FormViewComponent;
import viewcomponent.ImageViewComponent;
import viewcomponent.PageViewComponent;
import viewcomponent.TextViewComponent;

public class BookSharing implements ViewPageFactory {

    @Override
    public PageViewComponent produce() {
	PageViewComponent me = new PageViewComponent("共享图书");
	produceSummaryBlock(me);
	productShareRequestForm(me);
	return me;
    }

    private BaseViewComponent produceSummaryBlock(PageViewComponent me) {
	return me.newChild(new VerticalContainerViewComponent()).addChild(new ImageViewComponent(
		"https://tse2-mm.cn.bing.net/th?id=OIP.7HSfLmISDKVNPu814ZD-WAHaLG&w=183&h=160&c=7&o=5&dpr=2&pid=1.7"))
		.addChild(new TextViewComponent("图书共享申请", "title_level1")).addChild(new TextViewComponent(
			"书香社区欢迎广大社区成员共享、捐赠图书。您可以将您要共享、捐赠的图书送到您身边的【书香社区门店】，如果需要上门取件，请填写下面表单，我们将尽快与您联系"));
    }

    private BaseViewComponent productShareRequestForm(PageViewComponent me) {
	BaseViewComponent form = me.newChild(new FormViewComponent("/shuxiang/bookManager/sharingBook/"));
	FormFieldViewComponent field = null;
	field = new FormFieldViewComponent();
	field.setId("customerId");
	field.setParameterName("customerId");
	field.setType("text");
	field.setFieldGroup("基本信息");
	field.setDefaultValue("C000001");
	field.setLabel("用户ID");
	field.setVisiable(false);
	form.addChild(field);
	
	field = new FormFieldViewComponent();
	field.setId("shareNum");
	field.setParameterName("shareNum");
	field.setType("select");
	//field.setCandidateValues("请选择书册的大概数量,10本以下,一箱（10~20本）以内, 很多（超过20本)");
	field.setFieldGroup("基本信息");
	field.setDefaultValue("10本以下");
	field.setLabel("共享数量");
	form.addChild(field);
	
	field = new FormFieldViewComponent();
	field.setId("contactName");
	field.setParameterName("contactName");
	field.setType("text");
	field.setFieldGroup("基本信息");
	field.setLabel("联系人");
	field.setPlaceholder("请输入取件联系人");
	form.addChild(field);
	
	field = new FormFieldViewComponent();
	field.setId("contactMobile");
	field.setParameterName("contactMobile");
	field.setType("text");
	field.setFieldGroup("基本信息");
	field.setLabel("联系电话");
	field.setPlaceholder("请输入取件联系人的电话");
	form.addChild(field);
	
	field = new FormFieldViewComponent();
	field.setId("contactAddress");
	field.setParameterName("contactAddress");
	field.setType("text");
	field.setFieldGroup("基本信息");
	field.setLabel("联系地址");
	field.setPlaceholder("请输入取件的地址");
	form.addChild(field);
	
	field = new FormFieldViewComponent();
	field.setId("agreement");
	field.setParameterName("agreement");
	field.setType("switch");
	field.setFieldGroup("用户协议");
	field.setLabel("我同意");
	field.setPlaceholder("请输入取件的地址");
	field.setContent(false);
	form.newChild(new ContainerViewComponent("user-agreement-container")).addChild(field)
		.addChild(new LinkViewComponent("/shuxiang/miscContent/userShareBookAgreement/")
			.addChild(new TextViewComponent("《书香社区共享图书条款》")));
	
	
	form.addChild(new FormActionViewComponent("/shuxiang/bookManager/sharingBook/", "提交"));
	
	return form;
    }


}
