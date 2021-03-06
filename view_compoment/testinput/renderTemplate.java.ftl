<#import "lib/utils.ftl" as utils>
<#include "lib/component_temp.java.ftl" />
<#assign java_primitive_types=["boolean","int","long","double"]/>
package com.terapico.shuxiang.wxalayout;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import com.terapico.caf.DateTime;
import com.terapico.caf.viewcomponent.*;
import com.terapico.shuxiang.ShuxiangUserContext;
<#list pageSpec.page.importedModels as modelName>
import com.terapico.shuxiang.${modelName?lower_case}.${modelName};
</#list>


public class ${pageSpec.className}BaseRender extends BasicRender{
    protected ${pageSpec.viewModelName} viewModel;

    public ${pageSpec.viewModelName} getViewModel(){
        return viewModel;
    }
    public void setViewModel(${pageSpec.viewModelName} viewModel){
        this.viewModel = viewModel;
    }
    
    public PageViewComponent doRendering(${userContext} userContext, ${pageSpec.viewModelName} viewModel, String pageRefreshUrl) throws Exception{
        PageViewComponent me = doRendering(userContext, viewModel);
        me.setLinkToUrl(pageRefreshUrl);
        return me;
    }

    public PageViewComponent doRendering(${userContext} userContext, ${pageSpec.viewModelName} viewModel) throws Exception{
        setViewModel(viewModel);

        // 渲染page对象
        PageViewComponent me = createPageViewComponent("${pageSpec.page.title}");
        RenderingContext context = initRenderingContext(userContext);
        <#if pageSpec.page.cssClass?has_content>me.setClasses("${pageSpec.page.cssClass}");</#if>
        <#if pageSpec.page.frontColor?has_content>me.setFrontColor("${pageSpec.page.frontColor}");</#if>
        <#if pageSpec.page.backgroundColor?has_content>me.setBackgroundColor("${pageSpec.page.backgroundColor}");</#if>
        <@utils.makePageChildrenCall pageSpec.page/>
        return me;
    }

	protected PageViewComponent createPageViewComponent(String pageTitle){
		return new PageViewComponent(pageTitle);
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
            renderEach${uiSpec.jobInfo.methodName}(<@utils.makeRenderMethodCallParameters uiSpec/>, i, dataList.get(i));
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
    <#assign component_render_macro="gen_component_"+uiSpec.elementTypeName?replace("-","_") />
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
        <#list uiSpec.bindedDataSourceInfo.children as segDsInfo>
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
    <#-- share button share title 的获取 -->
    <#if uiSpec.sharingTitleDataSourceInfo?? && uiSpec.sharingTitleDataSourceInfo.type != "const_string">
    // type = ${uiSpec.sharingTitleDataSourceInfo.type}
    protected String getShareTitle4${uiSpec.jobInfo.methodName}(<@utils.makeRenderMethodCallParametersDeclaration uiSpec/>${uiSpec.jobInfo.localDataCheckDeclare}) {
        <#if uiSpec.sharingTitleDataSourceInfo.type == "model_path">
        StringBuilder sb = new StringBuilder();
            <@utils.gen_string_concat_builder_part uiSpec, uiSpec.sharingTitleDataSourceInfo, ""/>
        return sb.toString();
    
        <#else>
        StringBuilder sb = new StringBuilder();
            <#list uiSpec.sharingTitleDataSourceInfo.children as segDsInfo>
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
    <#-- share button image url 的获取 -->
    <#if uiSpec.imageUrlDataSourceInfo?? && uiSpec.imageUrlDataSourceInfo.type != "const_string">
    // type = ${uiSpec.imageUrlDataSourceInfo.type}
    protected String getImageUrl4${uiSpec.jobInfo.methodName}(<@utils.makeRenderMethodCallParametersDeclaration uiSpec/>${uiSpec.jobInfo.localDataCheckDeclare}) {
        <#if uiSpec.imageUrlDataSourceInfo.type == "model_path">
        StringBuilder sb = new StringBuilder();
            <@utils.gen_string_concat_builder_part uiSpec, uiSpec.imageUrlDataSourceInfo, ""/>
        return sb.toString();
    
        <#else>
        StringBuilder sb = new StringBuilder();
            <#list uiSpec.imageUrlDataSourceInfo.children as segDsInfo>
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
    <#-- share button call-back url 的获取 -->
    <#if uiSpec.callBackUrlDataSourceInfo?? && uiSpec.callBackUrlDataSourceInfo.type != "const_string">
    // type = ${uiSpec.imageUrlDataSourceInfo.type}
    protected String getCallBackUrl4${uiSpec.jobInfo.methodName}(<@utils.makeRenderMethodCallParametersDeclaration uiSpec/>${uiSpec.jobInfo.localDataCheckDeclare}) {
        <#if uiSpec.callBackUrlDataSourceInfo.type == "model_path">
        StringBuilder sb = new StringBuilder();
            <@utils.gen_string_concat_builder_part uiSpec, uiSpec.callBackUrlDataSourceInfo, ""/>
        return sb.toString();
    
        <#else>
        StringBuilder sb = new StringBuilder();
            <#list uiSpec.callBackUrlDataSourceInfo.children as segDsInfo>
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
    <#-- activeTab 的获取 -->
    <#if uiSpec.activeTabDataSourceInfo?? && uiSpec.activeTabDataSourceInfo.type!="const_string">
    protected String getActiveTab4${uiSpec.jobInfo.methodName}(<@utils.makeRenderMethodCallParametersDeclaration uiSpec/>${uiSpec.jobInfo.localDataCheckDeclare}) {
        <#if uiSpec.activeTabDataSourceInfo.type == "model_path">
        StringBuilder sb = new StringBuilder();
            <@utils.gen_string_concat_builder_part uiSpec, uiSpec.activeTabDataSourceInfo, ""/>
        return sb.toString();
        <#else>
        StringBuilder sb = new StringBuilder();
            <#list uiSpec.activeTabDataSourceInfo.children as segDsInfo>
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
    <#-- 获取图像src url 的函数 -->
    <#if uiSpec.elementTypeName == "image" && uiSpec.srcUrlDataSourceInfo.type != "const_string">
    // type = ${uiSpec.srcUrlDataSourceInfo.type}
    protected String getSrcUrl4${uiSpec.jobInfo.methodName}(<@utils.makeRenderMethodCallParametersDeclaration uiSpec/>${uiSpec.jobInfo.localDataCheckDeclare}) {
        <@gen_get_string_exp_value uiSpec, uiSpec.srcUrlDataSourceInfo />
    }
    </#if>
    <#-- 获取container target-id 的函数 -->
    <#if uiSpec.targetIdDataSourceInfo?? && uiSpec.targetIdDataSourceInfo.type != "const_string">
    protected String getTargetId4${uiSpec.jobInfo.methodName}(<@utils.makeRenderMethodCallParametersDeclaration uiSpec/>${uiSpec.jobInfo.localDataCheckDeclare}) {
        <@gen_get_string_exp_value uiSpec, uiSpec.targetIdDataSourceInfo/>
    }
    </#if>
    <#-- 判断元素是否需要渲染的函数 -->
    protected boolean isNeedRender${uiSpec.jobInfo.methodName}(<@utils.makeRenderMethodCallParametersDeclaration uiSpec/>${uiSpec.jobInfo.localDataCheckDeclare}) {
        <#if uiSpec.elementTypeName != "field" && uiSpec.jobInfo.hasLocalVariable >
            <#t><#if java_primitive_types?seq_contains(uiSpec.jobInfo.localDataTypeName)>
                <#t><#if uiSpec.jobInfo.hasInputData>
        if (inputData == null){
            return false;
        }</#if>
        return true;<#else>
        if (data == null){
            return false;
        }
        return true;</#if>
        <#t><#else> <#-- 没有本地变量 --><#if uiSpec.jobInfo.hasInputData>
        if (inputData == null){
            return false;
        }</#if>
        return true;</#if>
    }
</#list>
}

<#macro gen_get_string_exp_value uiSpec, dataSrcInfo>
	<#if dataSrcInfo.type == "model_path">
        StringBuilder sb = new StringBuilder();
            <@utils.gen_string_concat_builder_part uiSpec, dataSrcInfo, ""/>
        return sb.toString();
    
        <#else>
        StringBuilder sb = new StringBuilder();
            <#list dataSrcInfo.children as segDsInfo>
                <#if segDsInfo.type="const_string">
        sb.append("${segDsInfo.dataSourceExpression}");
                    <#continue>
                </#if>
                    <@utils.gen_string_concat_builder_part uiSpec, segDsInfo, "_"+(segDsInfo?index+1)/>
            </#list>
        return sb.toString();
        </#if>
</#macro>
