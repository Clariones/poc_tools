
<#import "lib/utils.ftl" as utils>
<#include "lib/component_temp.java.ftl" />
package com.terapico.shuxiang.wxalayout;

import java.util.List;

import com.terapico.caf.viewcomponent.*;
import com.terapico.shuxiang.ShuxiangUserContext;
import com.terapico.shuxiang.campaign.Campaign;
import com.terapico.shuxiang.store.Store;
import com.terapico.shuxiang.storeslide.StoreSlide;


import viewcomponent.*;

//import ${pageSpec.className}ViewModel;
//import ${pageSpec.className}ViewModelFactory;

public class ${pageSpec.className}BaseRender extends BasicRender{
    protected ${pageSpec.viewModelName} viewModel;

    public ${pageSpec.viewModelName} getViewModel(){
        return viewModel;
    }
    public void setViewModel(${pageSpec.viewModelName} viewModel){
        this.viewModel = viewModel;
    }

    public PageViewComponent doRendering(${userContext} userContext, ${pageSpec.viewModelName} viewModel) throws Exception{
        setViewModel(viewModel);

        // 渲染page对象
        PageViewComponent me = new PageViewComponent("${pageSpec.page.title}");
        RenderingContext context = initRenderingContext(userContext);
        <@utils.makePageChildrenCall pageSpec.page/>
        return me;
    }


<#list pageSpec.members as uiSpec>
    <#if !uiSpec.jobInfo??>
    //// skip ${uiSpec.elementTypeName} ${uiSpec.name!} defined line ${uiSpec.elementDeclaredLineNumber}
        <#continue>
    </#if>
    
    // render ${uiSpec.elementTypeName} ${uiSpec.name!} defined line ${uiSpec.elementDeclaredLineNumber} 
    <#if uiSpec.jobInfo.listRenderingMethod>
    <#-- 这里是渲染函数入口 -->
    protected void render${uiSpec.jobInfo.methodName} (<@utils.makeRenderMethodCallParametersDeclaration uiSpec/>) throws Exception {
        List<${uiSpec.bindedDataSourceInfo.javaTypeName}> dataList = get${uiSpec.jobInfo.methodName}DataFromViewModel(<@utils.makeRenderMethodCallParameters uiSpec/>);
        if (dataList == null){
        	return;
        }
        for(int i=0;i<dataList.size();i++){
            parent.addChild(renderEach${uiSpec.jobInfo.methodName}(<@utils.makeRenderMethodCallParameters uiSpec/>, i, dataList.get(i)));
        }
    }
    protected BaseViewComponent renderEach${uiSpec.jobInfo.methodName} (<@utils.makeRenderMethodCallParametersDeclaration uiSpec/>, int index, ${uiSpec.bindedDataSourceInfo.javaTypeName} inputData) throws Exception {
        context.setIndex("${uiSpec.jobInfo.methodName}", index);
        ${uiSpec.bindedDataSourceInfo.javaTypeName} data = inputData;
    <#else>
    protected BaseViewComponent render${uiSpec.jobInfo.methodName} (<@utils.makeRenderMethodCallParametersDeclaration uiSpec/>) throws Exception {
    <@utils.gen_get_local_var_part uiSpec/>
    </#if>
    <#-- 下面是 判断是否需要渲染的部分-->
    <@utils.gen_check_need_render_part uiSpec/>
    <#assign component_render_macro="gen_component_"+uiSpec.elementTypeName />
    <#if .vars[component_render_macro]??>
        <@.vars[component_render_macro] uiSpec/>
    <#else>
        // TODO: ${component_render_macro} 未定义
    </#if>
    <@utils.gen_set_link_url_part uiSpec/>
        parent.addChild(me);
        return me;
    }
    <#-- 获取数据的函数 -->
    <#if uiSpec.jobInfo.hasLocalVariable>
    protected ${uiSpec.jobInfo.localDataTypeName} get${uiSpec.jobInfo.methodName}DataFromViewModel(<@utils.makeRenderMethodCallParametersDeclaration uiSpec/>) throws Exception{
        <#if uiSpec.bindedDataSourceInfo.type == "java_variable">
        return viewModel.get${uiSpec.bindedDataSourceInfo.variableName?cap_first}();
        <#elseif uiSpec.bindedDataSourceInfo.type == "string_concat_function">
        StringBuilder sb = new StringBuilder();
        <#list uiSpec.jobInfo.linkTo.children as segDsInfo>
            <#if segDsInfo.type="const_string">
        sb.append("${segDsInfo.dataSourceExpression}");
                <#continue>
            </#if>
                <@utils.gen_string_concat_builder_part uiSpec, segDsInfo, "_"+(segDsInfo?index+1)/>
        </#list>
        return sb.toString();
        <#elseif uiSpec.bindedDataSourceInfo.type == "model_path">
            <@utils.gen_get_model_path_data_part uiSpec, uiSpec.bindedDataSourceInfo, '' />
        <#else>
            // TODO: ${uiSpec.bindedDataSourceInfo.type} 类型的数据读取还没完成
        </#if>
    }
    </#if>
    <#-- link to url 的获取 -->
    <#if uiSpec.jobInfo.hasLinkUrl && uiSpec.jobInfo.linkTo.type=="string_concat_function">
    protected String getLinkToUrl4${uiSpec.jobInfo.methodName}(<@utils.makeRenderMethodCallParametersDeclaration uiSpec/>${uiSpec.jobInfo.localDataCheckDeclare}) {
        StringBuilder sb = new StringBuilder();
        <#list uiSpec.jobInfo.linkTo.children as segDsInfo>
            <#if segDsInfo.type="const_string">
        sb.append("${segDsInfo.dataSourceExpression}");
                <#continue>
            </#if>
                <@utils.gen_string_concat_builder_part uiSpec, segDsInfo, "_"+(segDsInfo?index+1)/>
        </#list>
        return sb.toString();
    }
    </#if>
    <#-- 获取图像src url 的函数 -->
    <#if uiSpec.elementTypeName == "image" && uiSpec.srcUrlDataSourceInfo.type != "const_string">
    // type = ${uiSpec.srcUrlDataSourceInfo.type}
    protected String getSrcUrl4${uiSpec.jobInfo.methodName}(<@utils.makeRenderMethodCallParametersDeclaration uiSpec/>${uiSpec.jobInfo.localDataCheckDeclare}) {
    	<#if uiSpec.srcUrlDataSourceInfo.type == "model_path">
    	StringBuilder sb = new StringBuilder();
    		<@utils.gen_string_concat_builder_part uiSpec, uiSpec.srcUrlDataSourceInfo, ""/>
    	return sb.toString();
	
    	<#else>
    	StringBuilder sb = new StringBuilder();
        	<#list uiSpec.srcUrlDataSourceInfo.children as segDsInfo>
            	<#if segDsInfo.type="const_string">
        sb.append("${segDsInfo.dataSourceExpression}");
                	<#continue>
            	</#if>
                	<@utils.gen_string_concat_builder_part uiSpec, segDsInfo, "_"+(segDsInfo?index+1)/>
        	</#list>
        return sb.toString();
        </#if>
    }
    </#if>
    <#-- 判断元素是否需要渲染的函数 -->
    protected boolean isNeedRender${uiSpec.jobInfo.methodName}(<@utils.makeRenderMethodCallParametersDeclaration uiSpec/>${uiSpec.jobInfo.localDataCheckDeclare}) {
        <#if uiSpec.elementTypeName != "field" && uiSpec.jobInfo.hasLocalVariable >
        if (data == null){
            return false;
        }
        </#if>
        return true;
    }
</#list>
}


