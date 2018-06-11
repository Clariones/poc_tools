package test.generator.render;

import java.util.List;

import com.terapico.shuxiang.ShuxiangUserContext;
import com.terapico.shuxiang.store.Store;
import com.terapico.shuxiang.storeslide.StoreSlide;


import viewcomponent.*;

//import CustomerHomePageViewModel;
//import CustomerHomePageViewModelFactory;

public class CustomerHomePageRender extends BasicRender{
    protected CustomerHomePageViewModel viewModel;

    public CustomerHomePageViewModel getViewModel(){
        return viewModel;
    }
    public void setViewModel(CustomerHomePageViewModel viewModel){
        this.viewModel = viewModel;
    }

    public PageViewComponent doRendering(ShuxiangUserContext userContext, CustomerHomePageViewModel viewModel) throws Exception{
        setViewModel(viewModel);

        // 渲染page对象
        PageViewComponent me = new PageViewComponent("书香社区");
        RenderingContext context = initRenderingContext(userContext);
        me.addChild(renderCarouselL14(context, me))
            .addChild(renderFormSurvey(context, me));
        return me;
    }



    // ///////////////// render carousel  defined line 14 //////////////////
    protected BaseViewComponent renderCarouselL14 (/*TODO*/RenderingContext context, BaseViewComponent parent) throws Exception {
        List<StoreSlide> data = getCarouselL14DataFromViewModel(context, parent);
        boolean shouldRender = isNeedRenderCarouselL14(context, parent, data);
        if (!shouldRender){
            return null;
        }
        // render by gen_component_carousel 
        CarouselViewComponent me = new CarouselViewComponent();
        me.setClasses("my_css_slider");
        for(StoreSlide item: data){
            me.addItem(item.getBannerImage(), item.getWxaLinkUrl(), item.getTips());
        }
        parent.addChild(me);
        return me;
    }
    
    protected List<StoreSlide>  getCarouselL14DataFromViewModel(/*TODO*/RenderingContext context, BaseViewComponent parent) throws Exception{
           Store data_1 = viewModel.getStore();
        return data_1.getStoreSlideList();
    }
    
    protected boolean isNeedRenderCarouselL14(/*TODO*/RenderingContext context, BaseViewComponent parent, List<StoreSlide> data) {
        if (data == null){
            return false;
        }
        return true;
    }

    // ///////////////// render form survey defined line 19 //////////////////
    protected BaseViewComponent renderFormSurvey (/*TODO*/RenderingContext context, BaseViewComponent parent) throws Exception {
        boolean shouldRender = isNeedRenderFormSurvey(context, parent);
        if (!shouldRender){
            return null;
        }
        // render by gen_component_form 
        FormViewComponent me = new FormViewComponent();

        parent.addChild(me);
        me.addChild(renderContainerL20 (context, parent));
        me.addChild(renderContainerL23 (context, parent));
        return me;
    }
    
    
    protected boolean isNeedRenderFormSurvey(/*TODO*/RenderingContext context, BaseViewComponent parent) {
        // even this is MUST to show, just leave this method as customization pointer to control your view
        return true;
    }

    // ///////////////// render container  defined line 20 //////////////////
    protected BaseViewComponent renderContainerL20 (/*TODO*/RenderingContext context, BaseViewComponent parent) throws Exception {
        boolean shouldRender = isNeedRenderContainerL20(context, parent);
        if (!shouldRender){
            return null;
        }
        // render by gen_component_container 
        ContainerViewComponent me = new ContainerViewComponent();

        me.setId("form_title");
        parent.addChild(me);
        me.addChild(renderTextL21 (context, parent));
        return me;
    }
    
    
    protected boolean isNeedRenderContainerL20(/*TODO*/RenderingContext context, BaseViewComponent parent) {
        // even this is MUST to show, just leave this method as customization pointer to control your view
        return true;
    }

    // ///////////////// render text  defined line 21 //////////////////
    protected BaseViewComponent renderTextL21 (/*TODO*/RenderingContext context, BaseViewComponent parent) throws Exception {
        boolean shouldRender = isNeedRenderTextL21(context, parent);
        if (!shouldRender){
            return null;
        }
        // render by gen_component_text 
        TextViewComponent me = new TextViewComponent();
        me.setClasses("block-title");
        me.setContent("问卷调查表");
        parent.addChild(me);
        return me;
    }
    
    
    protected boolean isNeedRenderTextL21(/*TODO*/RenderingContext context, BaseViewComponent parent) {
        // even this is MUST to show, just leave this method as customization pointer to control your view
        return true;
    }

    // ///////////////// render container  defined line 23 //////////////////
    protected BaseViewComponent renderContainerL23 (/*TODO*/RenderingContext context, BaseViewComponent parent) throws Exception {
        boolean shouldRender = isNeedRenderContainerL23(context, parent);
        if (!shouldRender){
            return null;
        }
        // render by gen_component_container 
        ContainerViewComponent me = new ContainerViewComponent();

        me.setId("form_body");
        parent.addChild(me);
        me.addChild(renderFieldName (context, parent));
        me.addChild(renderFieldBirthday (context, parent));
        me.addChild(renderFieldLikedfood (context, parent));
        me.addChild(renderFieldSelfrate (context, parent));
        return me;
    }
    
    
    protected boolean isNeedRenderContainerL23(/*TODO*/RenderingContext context, BaseViewComponent parent) {
        // even this is MUST to show, just leave this method as customization pointer to control your view
        return true;
    }

    // ///////////////// render field name defined line 31 //////////////////
    protected BaseViewComponent renderFieldName (/*TODO*/RenderingContext context, BaseViewComponent parent) throws Exception {
        boolean shouldRender = isNeedRenderFieldName(context, parent);
        if (!shouldRender){
            return null;
        }
        // render by gen_component_field 
        FormFieldViewComponent me = new FormFieldViewComponent();
        me.setClasses("demo_field");
        me.setParameterName("name");
        me.setLabel("姓名");
        me.setType("text");
        me.setPlaceholder("请输入姓名");
        me.setRequired(true);
        me.setDisabled(false);
        // candidate values are complex, you should override this method if you need candidate values
        parent.addChild(me);
        return me;
    }
    
    
    protected boolean isNeedRenderFieldName(/*TODO*/RenderingContext context, BaseViewComponent parent) {
        // even this is MUST to show, just leave this method as customization pointer to control your view
        return true;
    }

    // ///////////////// render field birthday defined line 40 //////////////////
    protected BaseViewComponent renderFieldBirthday (/*TODO*/RenderingContext context, BaseViewComponent parent) throws Exception {
        boolean shouldRender = isNeedRenderFieldBirthday(context, parent);
        if (!shouldRender){
            return null;
        }
        // render by gen_component_field 
        FormFieldViewComponent me = new FormFieldViewComponent();
        me.setClasses("demo_field");
        me.setParameterName("birthday");
        me.setLabel("出生日期");
        me.setType("text");
        me.setPlaceholder("yyyy-MM-dd");
        me.setRequired(true);
        me.setDisabled(false);
        // candidate values are complex, you should override this method if you need candidate values
        parent.addChild(me);
        return me;
    }
    
    
    protected boolean isNeedRenderFieldBirthday(/*TODO*/RenderingContext context, BaseViewComponent parent) {
        // even this is MUST to show, just leave this method as customization pointer to control your view
        return true;
    }

    // ///////////////// render field likedfood defined line 49 //////////////////
    protected BaseViewComponent renderFieldLikedfood (/*TODO*/RenderingContext context, BaseViewComponent parent) throws Exception {
        boolean shouldRender = isNeedRenderFieldLikedfood(context, parent);
        if (!shouldRender){
            return null;
        }
        // render by gen_component_field 
        FormFieldViewComponent me = new FormFieldViewComponent();
        me.setClasses("demo_field");
        me.setParameterName("likedfood");
        me.setLabel("喜欢的食物");
        me.setType("text");
        me.setPlaceholder("中餐、西餐还是泰国菜");
        me.setRequired(true);
        me.setDisabled(false);
        // candidate values are complex, you should override this method if you need candidate values
        parent.addChild(me);
        return me;
    }
    
    
    protected boolean isNeedRenderFieldLikedfood(/*TODO*/RenderingContext context, BaseViewComponent parent) {
        // even this is MUST to show, just leave this method as customization pointer to control your view
        return true;
    }

    // ///////////////// render field selfrate defined line 58 //////////////////
    protected BaseViewComponent renderFieldSelfrate (/*TODO*/RenderingContext context, BaseViewComponent parent) throws Exception {
        boolean shouldRender = isNeedRenderFieldSelfrate(context, parent);
        if (!shouldRender){
            return null;
        }
        // render by gen_component_field 
        FormFieldViewComponent me = new FormFieldViewComponent();
        me.setClasses("demo_field");
        me.setParameterName("selfrate");
        me.setLabel("自我评价");
        me.setType("longtext");
        me.setPlaceholder("请输入自己的自我评价，最多500个字。");
        me.setRequired(true);
        me.setDisabled(false);
        // candidate values are complex, you should override this method if you need candidate values
        parent.addChild(me);
        return me;
    }
    
    
    protected boolean isNeedRenderFieldSelfrate(/*TODO*/RenderingContext context, BaseViewComponent parent) {
        // even this is MUST to show, just leave this method as customization pointer to control your view
        return true;
    }
}

