<#ftl strip_text="yes" strip_whitespace="yes">
<#import "utils.ftl" as utils>

<#macro gen_component_common_part uiSpec>
<#if uiSpec.cssClass?has_content>
        me.setClasses("${uiSpec.cssClass}");</#if><#if uiSpec.id?has_content>
        me.setId("${uiSpec.id}");</#if><#if uiSpec.tag?has_content>
        me.setTag("${uiSpec.tag}");</#if>
</#macro>

<#macro gen_component_chidren_ifhas uiSpec>
<#if !uiSpec.children?has_content><#return></#if>
<#list uiSpec.children as subComp>
        render${subComp.jobInfo.methodName} (<@utils.makeSubRenderMethodCallParameters subComp/>);
</#list>
</#macro>

<#-- 前面是公共宏，下面是每种UI Spec元素对应的宏 -->
<#macro gen_component_carousel uiSpec>
        CarouselViewComponent me = new CarouselViewComponent();
        <@gen_component_common_part uiSpec/>
        for(${uiSpec.bindedDataSourceInfo.javaTypeName} item: data){
            me.addItem(item.get${uiSpec.imageDataSrc.variableName?cap_first}(), item.get${uiSpec.linkDataSrc.variableName?cap_first}(), item.get${uiSpec.tipsDataSrc.variableName?cap_first}());
        }
        <@gen_component_chidren_ifhas uiSpec/>
</#macro>

<#macro gen_component_form uiSpec>
        FormViewComponent me = new FormViewComponent();
        <@gen_component_common_part uiSpec/>
        <@gen_component_chidren_ifhas uiSpec/>
</#macro>

<#macro gen_component_container uiSpec>
        ContainerViewComponent me = new ContainerViewComponent();
        <@gen_component_common_part uiSpec/>
        <@gen_component_chidren_ifhas uiSpec/>
</#macro>

<#macro gen_component_text uiSpec>
        TextViewComponent me = new TextViewComponent();
        <@gen_component_common_part uiSpec/>
	<#if uiSpec.bindedDataSourceInfo??>
        me.setContent(data);
	<#else>
        me.setContent("${uiSpec.elementTextContent}");
	</#if>
        <@gen_component_chidren_ifhas uiSpec/>
</#macro>

<#macro gen_component_bar_or_qr_code uiSpec>
        BarOrQrCodeViewComponent me = new BarOrQrCodeViewComponent();
        <@gen_component_common_part uiSpec/>
	<#if uiSpec.bindedDataSourceInfo??>
        me.setContent(data);
	<#else>
        me.setContent("${uiSpec.elementTextContent}");
	</#if>
        <@gen_component_chidren_ifhas uiSpec/>
</#macro>

<#macro gen_component_field uiSpec>
        FormFieldViewComponent me = new FormFieldViewComponent();
        <@gen_component_common_part uiSpec/>
        <#if uiSpec.name?has_content>me.setParameterName("${uiSpec.name}");</#if>
        <#if uiSpec.label?has_content>me.setLabel("${uiSpec.label}");</#if>
        <#if uiSpec.type?has_content>me.setType("${uiSpec.type}");</#if>
        <#if uiSpec.placeholder?has_content>me.setPlaceholder("${uiSpec.placeholder}");</#if>
        me.setRequired(${uiSpec.required?c});
        me.setDisabled(${uiSpec.disabled?c});
        // candidate values are complex, you should override this method if you need candidate values
        <@gen_component_chidren_ifhas uiSpec/>
</#macro>

<#macro gen_component_search uiSpec>
        SearchViewComponent me = new SearchViewComponent();
        <@gen_component_common_part uiSpec/>
        me.setPlaceHolder("${uiSpec.elementTextContent}");
    <#if uiSpec.apiUrlDataSourceInfo??>
    	<#if uiSpec.apiUrlDataSourceInfo.type="const_string">
    	me.setApi("${uiSpec.apiUrlDataSourceInfo.dataSourceExpression}");
    	<#else>
    	// TODO: 设置字符串拼接的部分. 复杂的api-url就自己重载这个函数
    	</#if>
    </#if>
        <@gen_component_chidren_ifhas uiSpec/>
</#macro>
<#macro gen_component_scanner uiSpec>
        CodeScannerComponent me = new CodeScannerComponent();
        <@gen_component_common_part uiSpec/>
    <#if uiSpec.apiUrlDataSourceInfo??>
    	<#if uiSpec.apiUrlDataSourceInfo.type="const_string">
    	me.setApi("${uiSpec.apiUrlDataSourceInfo.dataSourceExpression}");
    	<#else>
    	// TODO: 设置字符串拼接的部分，复杂的api-url就自己重载这个函数
    	</#if>
    </#if>
        <@gen_component_chidren_ifhas uiSpec/>
</#macro>


<#macro gen_component_image uiSpec>
        ImageViewComponent me = new ImageViewComponent();
        <@gen_component_common_part uiSpec/>
	<#if uiSpec.srcUrlDataSourceInfo??>
    	<#if uiSpec.srcUrlDataSourceInfo.type="const_string">
    	me.setContent("${uiSpec.srcUrlDataSourceInfo.dataSourceExpression}");
    	<#else>
    	me.setContent(getSrcUrl4${uiSpec.jobInfo.methodName}(<@utils.makeRenderMethodCallParameters uiSpec/>${uiSpec.jobInfo.localDataVar}));
    	</#if>
    </#if>
        <@gen_component_chidren_ifhas uiSpec/>
</#macro>
