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
        render${subComp.jobInfo.methodName} (<@utils.makeSubRenderMethodCallParameters subComp/>); // ${uiSpec.jobInfo.localDataVar}
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

<#macro gen_component_font_icon uiSpec>
        FontIconViewComponent me = new FontIconViewComponent();
        <@gen_component_common_part uiSpec/>
	<#if uiSpec.bindedDataSourceInfo??>
        me.setContent(data);
	<#else>
        me.setContent("${uiSpec.elementTextContent}");
	</#if>
	<#if uiSpec.level??>
		me.setLevel("${uiSpec.level}");
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

<#macro gen_component_action uiSpec>
        FormActionViewComponent me = new FormActionViewComponent();
        <@gen_component_common_part uiSpec/>
	<#if uiSpec.bindedDataSourceInfo??>
        me.setContent(data);
	<#else>
        me.setContent("${uiSpec.elementTextContent}");
	</#if>
		<#if uiSpec.level??>
		me.setLevel("${uiSpec.level}");
		</#if>
        <@gen_component_chidren_ifhas uiSpec/>
</#macro>

<#macro gen_component_date uiSpec>
        TextViewComponent me = new TextViewComponent();
        <@gen_component_common_part uiSpec/>
	<#if uiSpec.bindedDataSourceInfo??>
        me.setContent(formatDate(data, "${uiSpec.format}"));
	<#else>
        me.setContent("${uiSpec.elementTextContent}");
	</#if>
        <@gen_component_chidren_ifhas uiSpec/>
</#macro>

<#macro gen_component_money uiSpec>
        TextViewComponent me = new TextViewComponent();
        <@gen_component_common_part uiSpec/>
	<#if uiSpec.bindedDataSourceInfo??>
        me.setContent(formatMoney(data, "${uiSpec.format}"));
	<#else>
        me.setContent("${uiSpec.elementTextContent}");
	</#if>
        <@gen_component_chidren_ifhas uiSpec/>
</#macro>

<#macro gen_component_field uiSpec>
        FormFieldViewComponent me = new FormFieldViewComponent();
    <#if !uiSpec.type??>
        me.setType(FormFieldViewComponent.TYPE_TEXT);
    <#elseif uiSpec.type?contains("_")>
        me.setType(FormFieldViewComponent.TYPE_${uiSpec.type?upper_case});
    <#else>
        me.setType("${uiSpec.type}");
    </#if>
        <@gen_component_common_part uiSpec/>
        <#if uiSpec.bindedDataSourceInfo??>me.setContent(data);</#if><#if uiSpec.name?has_content>
        me.setParameterName("${uiSpec.name}");</#if><#if uiSpec.label?has_content>
        me.setLabel("${uiSpec.label}");</#if><#if uiSpec.placeholder?has_content>
        me.setPlaceholder("${uiSpec.placeholder}");</#if><#if uiSpec.minValue?has_content>
        me.setMinValue("${uiSpec.minValue}");</#if><#if uiSpec.maxValue?has_content>
        me.setMaxValue("${uiSpec.maxValue}");</#if><#if uiSpec.maxLine gt 0 >
        me.setMaxLine(${uiSpec.maxLine});</#if><#if !uiSpec.required >
        me.setRequired(${uiSpec.required?c});</#if><#if uiSpec.disabled >
        me.setDisabled(${uiSpec.disabled?c});</#if>
        <@gen_field_candidate_values uiSpec/>
        <#-- 
        	form-field 应该没有子对象，碰到了再说
          <@gen_component_chidren_ifhas uiSpec/>
        
          -->
</#macro>

<#macro gen_field_candidate_values uiSpec>
	<#if ! uiSpec.children?has_content>
	<#return>
	</#if>
	<#list uiSpec.children as optionSpec>
		<#if optionSpec.elementTypeName == "option">
		me.addCandidateValue("${optionSpec.value}","${optionSpec.displayText}"<#if optionSpec.checked??>, ${optionSpec.checked?c}</#if>);
		</#if>
	</#list>
</#macro>
