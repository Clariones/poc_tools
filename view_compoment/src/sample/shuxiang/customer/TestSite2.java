package sample.shuxiang.customer;

import com.terapico.caf.viewcomponent.BaseViewComponent;
import com.terapico.caf.viewcomponent.CarouselViewComponent;
import com.terapico.caf.viewcomponent.CodeScannerComponent;
import com.terapico.caf.viewcomponent.ContainerViewComponent;
import com.terapico.caf.viewcomponent.FormActionViewComponent;
import com.terapico.caf.viewcomponent.FormFieldViewComponent;
import com.terapico.caf.viewcomponent.FormViewComponent;
import com.terapico.caf.viewcomponent.ImageViewComponent;
import com.terapico.caf.viewcomponent.PageViewComponent;
import com.terapico.caf.viewcomponent.TextViewComponent;

import sample.shuxiang.ViewPageFactory;

public class TestSite2 implements ViewPageFactory {
    private static int cnt = 0;

    @Override
    public PageViewComponent produce() {
	PageViewComponent me = new PageViewComponent("扫码借书");
	productScanner(me);
	return me;
    }

    private void productScanner(PageViewComponent parent) {
	parent.addChild(new TextViewComponent("您可以借7本书", "text-align-center"));
	parent.addChild(new TextViewComponent("已借3本，还可借4本", "text-align-center"));
	parent.addChild(new CodeScannerComponent("./wxaService/scanToBorrow/", "full-screen-scanner"));
	parent.addChild(new ContainerViewComponent("horizontal-layout flex-space-around")
		.addChild(new ContainerViewComponent("vertical-layout").assignLinkToUrl("./wxaService/customerIdQrCode/")
				.addChild(new ImageViewComponent("./images/scan1.png"))
				.addChild(new TextViewComponent("我的借书码")))
		.addChild(new ContainerViewComponent("vertical-layout")
			.addChild(new ImageViewComponent("./images/scan2.png"))
			.addChild(new TextViewComponent("自助借书"))));
    }

}
