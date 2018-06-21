package sample.shuxiang.customer;

import com.terapico.caf.viewcomponent.BaseViewComponent;
import com.terapico.caf.viewcomponent.CarouselViewComponent;
import com.terapico.caf.viewcomponent.CodeScannerComponent;
import com.terapico.caf.viewcomponent.ContainerViewComponent;
import com.terapico.caf.viewcomponent.FormActionViewComponent;
import com.terapico.caf.viewcomponent.FormFieldViewComponent;
import com.terapico.caf.viewcomponent.FormViewComponent;
import com.terapico.caf.viewcomponent.PageViewComponent;
import com.terapico.caf.viewcomponent.TextViewComponent;

import sample.shuxiang.ViewPageFactory;

public class TestSite1 implements ViewPageFactory {
    private static int cnt = 0;

    @Override
    public PageViewComponent produce() {
	PageViewComponent me = new PageViewComponent("试验场1");
	produceHeadCarousel(me);
	productForm(me);
	//productScanner(me);
	return me;
    }

    private void productScanner(PageViewComponent parent) {
	parent.addChild(new CodeScannerComponent("./wxaService/scanToBorrow/", "full-screen-scanner"));
    }

    private BaseViewComponent productForm(PageViewComponent parent) {
	BaseViewComponent form = parent.newChild(new FormViewComponent());

	BaseViewComponent me = form.newChild(new ContainerViewComponent("my_css_form_title"))
		.addChild(new TextViewComponent("问卷调查表", "my_css_block_title"));
	me = form.newChild(new ContainerViewComponent("my_css_form_title"));

	FormFieldViewComponent field = addField(me, "text", "姓名", "name", "请输入姓名");
	field = addField(me, "date", "出生日期", "birthday", "请选择您的出生日期");
	field = addField(me, "date_time", "常看手机?", "lastPhoneTime", "yyyy-MM-dd HH:mm");
	field.setDescription("您最后一次看手机的时间。");
	field.setFieldMessage("时间格式不正确");
	field.setFieldMessageLevel("info");

	field = addField(me, "money", "图书支出", "bookcost", "平均每年图书花费");
	field.setFieldMessage("必须输入一个有效的数字");
	field.setFieldMessageLevel("warning");

	field = addField(me, "url", "个人网站", "homesite", "您的主页");
	field.setDefaultValue("htpp://www.baidu.com");
	field.setFieldMessage("您输入的值不是一个有效的互联网地址");
	field.setFieldMessageLevel("error");

	field = addField(me, "image", "最近看的书", "bookimages", "您最近看了哪些书的照片");

	field = addField(me, "password", "密码", "password", "您的密码");

	field = addField(me, "number", "年龄", "age", "请输入您的年龄");

	field = addField(me, "select", "性别", "sex", null);
	field.addCandidateValue("man", "男");
	field.addCandidateValue("woman", "女");

	field = addField(me, "switch", "是某个图书馆会员", "member", null);

	field = addField(me, "checkbox", "阅读爱好", "favorite", null);
	field.addCandidateValue("game", "游戏", true);
	field.addCandidateValue("finance", "金融", false);
	field.addCandidateValue("tech", "科技", false);
	field.addCandidateValue("story", "小说", true);

	field = addField(me, "vcode", "短信验证码", "vcode", "");
	field = addField(me, "longtext", "自我评价", "selfreview", "你对自己的评价，不少于100字。");

	me = me.newChild(new ContainerViewComponent("my_css_form_agreement_row"));
	field = (FormFieldViewComponent) addField(me, "checkbox", null, "agreed", null).assignClasses("smaller-size");
	field.addCandidateValue("agreed", "我同意", false);
	me.newChild(new TextViewComponent("书香用户协议", "my_css_agreement_link")
		.assignLinkToUrl("./wxaService/viewCustomerAgreement/fromTestSite1/"));

	form.newChild(new ContainerViewComponent("vertical-layout")).addChild(new FormActionViewComponent("./wxaService/saveQuestionnaires/C000001/Q000002/", "提交", "info")
		.assignClasses("btn btn-full"))
		.newChild(new ContainerViewComponent("horizontal-layout")
		.addChild(new FormActionViewComponent("./wxaService/cancelQuestionnaires/C000001/Q000002/", "撤回",
			"default").assignClasses("btn btn-half"))
		.addChild(new FormActionViewComponent("./wxaService/complaintQuestionnaires/C000001/Q000002/", "投诉",
			"warning").assignClasses("btn btn-half")));
	return form;
    }

    private FormFieldViewComponent addField(BaseViewComponent me, String type, String label, String parameterName,
	    String placeHolder) {
	FormFieldViewComponent field = new FormFieldViewComponent();
	field.setType(type);
	field.setLabel(label);
	field.setParameterName(parameterName);
	field.setPlaceholder(placeHolder);
	field.setVisiable(true);
	field.setDisabled(false);
	field.setRequired((cnt++ % 2) == 0);
	me.newChild(field);
	return field;
    }

    private BaseViewComponent produceHeadCarousel(PageViewComponent me) {
	CarouselViewComponent carousel = new CarouselViewComponent();
	carousel.addItem(
		"https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcTL0zJ2f6DwWOw6xkuc16pDAyxfGv5_udkV1c0VbAUctmFULbbD",
		"./wxaService/activities/A000001/", "8月亲子活动");
	carousel.addItem(
		"https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcQY2RLztnCf306MdR4wm9RUTJy2r0HHc0c3HY2oQyjXLUR9nBZ9",
		"./wxaService/activities/A000002/", "诺贝尔文学奖得主见面会");
	carousel.addItem("https://i0.hdslb.com/bfs/article/4486e9c92b7add273a5b55f86a816d0603c1313f.jpg",
		"./wxaService/book/B001234/", "热读推荐：《海伯利安》");
	me.newChild(carousel);
	return carousel;
    }

}
