<#ftl strip_text="yes" strip_whitespace="yes">

<#macro makePageChildrenCall page>
    <#list pageSpec.page.children as subUiSpec>
        <#if subUiSpec.jobInfo?has_content>
        render${subUiSpec.jobInfo.methodName}(context, me);
        </#if>
    </#list>
</#macro>



<#macro makeRenderMethodCallParametersDeclaration uiSpec>
<@compress single_line=true>
    RenderingContext context,
    <#if uiSpec.jobInfo.hasInputData>
        BaseViewComponent parent, 
        <#if uiSpec.jobInfo.listRenderingMethod>
        	${uiSpec.jobInfo.inputDataTypeName} inputParentData
        <#else>
        	${uiSpec.jobInfo.inputDataTypeName} inputData
        </#if>
    <#else>
        BaseViewComponent parent
    </#if>
</@compress>
</#macro>

<#macro makeSubRenderMethodCallParametersDeclaration uiSpec>
<@compress single_line=true>
    RenderingContext context,
    <#if uiSpec.jobInfo.hasInputData>
        BaseViewComponent me, 
        <#if uiSpec.jobInfo.listRenderingMethod>
        	List<${uiSpec.jobInfo.inputDataTypeName}> inputDataList
        <#else>
        	${uiSpec.jobInfo.inputDataTypeName} inputData
        </#if>
    <#else>
        BaseViewComponent me
    </#if>
</@compress>
</#macro>

<#macro makeRenderMethodCallParameters uiSpec>
<@compress single_line=true>
    context,
    <#if uiSpec.ancestDataSourceInfo?has_content>
    	<#if uiSpec.jobInfo.listRenderingMethod>
        parent, inputParentData
        <#else>
        parent, inputData
        </#if>
    <#else>
        parent
    </#if>
</@compress>
</#macro>
         
<#macro makeSubRenderMethodCallParameters uiSpec>
<@compress single_line=true>
    context,
    <#if uiSpec.jobInfo.hasInputData>
        me, inputData
    <#else>
        me
    </#if>
</@compress>
</#macro>

              
<#macro gen_get_local_var_part uiSpec>
<#if uiSpec.jobInfo.hasLocalVariable>
        ${''}<@compress single_line=true>
    <#if uiSpec.selfHanleListInput>
        List<${uiSpec.jobInfo.localVariableTypeName}>
    <#else>
        ${uiSpec.jobInfo.localVariableTypeName} 
    </#if>
    data = get${uiSpec.jobInfo.methodName}DataFromViewModel(<@utils.makeRenderMethodCallParameters uiSpec/>);
    </@compress>
    
</#if>
</#macro>

<#macro gen_check_need_render_part uiSpec>
        boolean needRender = isNeedRender${uiSpec.jobInfo.methodName}(<@utils.makeRenderMethodCallParameters uiSpec/>${uiSpec.jobInfo.localDataVar});
        if (!needRender){
            return null;
        }
</#macro>
<#macro gen_set_link_url_part uiSpec>
<#if !uiSpec.jobInfo.hasLinkUrl><#return></#if>
<#if uiSpec.jobInfo.linkTo.type=="const_string">
        me.setLinkToUrl("${uiSpec.jobInfo.linkTo.dataSourceExpression}");
    <#return>
</#if>
        me.setLinkToUrl(getLinkToUrl4${uiSpec.jobInfo.methodName}(<@utils.makeRenderMethodCallParameters uiSpec/>${uiSpec.jobInfo.localDataVar}));
</#macro>


<#macro gen_get_model_path_data_part uiSpec, dsInfo, postFix>
<#assign geneticDsInfo = dsInfo.children[0] />
<#assign startVarName="inputData"/>
<#if uiSpec.jobInfo.listRenderingMethod>
<#assign startVarName="inputParentData"/>
</#if>
<#if geneticDsInfo.varScope = "page">
    <#assign startVarName="viewModel"/>
</#if>
<#list dsInfo.children as segDsInfo>
	<#if !uiSpec.selfHanleListInput && uiSpec.jobInfo.hasInputData && segDsInfo?index==0>
		<#continue>
	</#if>
    <#if segDsInfo?has_next>
        <#assign varName="data_"+(segDsInfo?index+1)+postFix />
        ${segDsInfo.javaTypeName} ${varName} = ${startVarName}.get${segDsInfo.variableName?cap_first}();
        if (${varName} == null){
            return null;
        }
        <#assign startVarName=varName/>
    <#else>
    	<#if segDsInfo.variableName == "_by_key_">
    	return ${startVarName}.valueByKey("${segDsInfo.dataSourceExpression}");
        <#else>
        return ${startVarName}.get${segDsInfo.variableName?cap_first}();
        </#if>
    </#if>
</#list>
</#macro>

<#macro gen_string_concat_builder_part uiSpec, dsInfo, postFix>
<#if !dsInfo.children?has_content>
        sb.append(viewModel.get${dsInfo.variableName?cap_first}());
	<#return>
</#if>
<#assign geneticDsInfo = dsInfo.children[0] />
<#assign startVarName="inputData"/>
<#if geneticDsInfo.varScope = "page">
    <#assign startVarName="viewModel"/>
</#if>
<#list dsInfo.children as segDsInfo>
	<#if !uiSpec.selfHanleListInput && uiSpec.jobInfo.hasInputData && segDsInfo?index==0 && segDsInfo.varScope != 'page'>
		<#continue>
	</#if>
    <#if segDsInfo?has_next>
        <#assign varName="data_"+(segDsInfo?index+1)+postFix />
        <#if segDsInfo?index == 0 && uiSpec.jobInfo.listRenderingMethod>
        ${segDsInfo.javaTypeName} ${varName} = data;
		<#else>
        ${segDsInfo.javaTypeName} ${varName} = ${startVarName}.get${segDsInfo.variableName?cap_first}();
		</#if>
        if (${varName} == null){
            return null;
        }
        <#assign startVarName=varName/>
    <#else>
    	<#if segDsInfo.variableName == "_by_key_">
    	sb.append(${startVarName}.valueByKey("${segDsInfo.dataSourceExpression}"));
        <#else>
        sb.append(${startVarName}.get${segDsInfo.variableName?cap_first}());
        </#if>
    </#if>
</#list>
</#macro>