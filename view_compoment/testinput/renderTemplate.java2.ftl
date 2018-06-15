
<#import "lib/utils.ftl" as utils>
<#include "lib/component_temp.java.ftl" />
package test.generator.render;

import java.util.List;

import com.terapico.shuxiang.ShuxiangUserContext;
import com.terapico.shuxiang.store.Store;
import com.terapico.shuxiang.storeslide.StoreSlide;


import viewcomponent.*;

//import ${pageSpec.className}ViewModel;
//import ${pageSpec.className}ViewModelFactory;

public class ${pageSpec.className}Render extends BasicRender{
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
        ${''}<@utils.makePageChildrenCall pageSpec.page/>        return me;
    }


<#list pageSpec.members as uiSpec>

    // ///////////////// render ${uiSpec.elementTypeName} ${uiSpec.name!} defined line ${uiSpec.elementDeclaredLineNumber} //////////////////
    <#if uiSpec.elementTypeName != "carousel" && uiSpec.bindedDataSourceInfo?has_content && uiSpec.bindedDataSourceInfo.list >
    protected BaseViewComponent render${uiSpec.jobInfo.methodName} (<@utils.makeRenderMethodCallParametersDeclaration uiSpec/>) throws Exception {
        <@getReturnedDataType uiSpec/> dataList = get${uiSpec.jobInfo.methodName}DataFromViewModel(<@utils.makeRenderMethodCallParameters uiSpec/>);
        for(int i=0;i<dataList.size();i++){
            parent.addChild(renderEach${uiSpec.jobInfo.methodName}(<@utils.makeRenderMethodCallParameters uiSpec/>, i, dataList.get(i))));
        }
        return null;
    }
    protected BaseViewComponent renderEach${uiSpec.jobInfo.methodName} (<@utils.makeRenderMethodCallParametersDeclaration uiSpec/>, int index, ${uiSpec.bindedDataSourceInfo.javaTypeName} inputData) throws Exception {
        context.setIndex("${uiSpec.jobInfo.methodName}", index);
    <#else>
    protected BaseViewComponent render${uiSpec.jobInfo.methodName} (<@utils.makeRenderMethodCallParametersDeclaration uiSpec/>) throws Exception {
    </#if>
    <#if uiSpec.bindedDataSourceInfo?has_content>
        <@getReturnedDataType uiSpec/> data = get${uiSpec.jobInfo.methodName}DataFromViewModel(<@utils.makeRenderMethodCallParameters uiSpec/>);
        boolean isNeedRender = isNeedRender${uiSpec.jobInfo.methodName}(<@utils.makeRenderMethodCallParameters uiSpec/>, data);
    <#else>
        boolean isNeedRender = isNeedRender${uiSpec.jobInfo.methodName}(<@utils.makeRenderMethodCallParameters uiSpec/>);
    </#if>
        if (!isNeedRender){
            return null;
        }

    <#assign component_render_macro="gen_component_"+uiSpec.elementTypeName />
    <#if .vars[component_render_macro]??>
        // render by ${component_render_macro} 
        <@.vars[component_render_macro] uiSpec/>
    <#else>
        // TODO: ${component_render_macro} 未定义
    </#if>
    <#if uiSpec.linkToDataSourceInfo??>
        <#if uiSpec.bindedDataSourceInfo?has_content>
            <@getReturnedDataType uiSpec/> data = get${uiSpec.jobInfo.methodName}DataFromViewModel(<@utils.makeRenderMethodCallParameters uiSpec/>);
        set${uiSpec.jobInfo.methodName}LinkToUrl(<@utils.makeRenderMethodCallParameters uiSpec/>, data);
        <#else>
        set${uiSpec.jobInfo.methodName}LinkToUrl(<@utils.makeRenderMethodCallParameters uiSpec/>);
        </#if>
    </#if>
        return me;
    }
    
    <#if uiSpec.linkToDataSourceInfo??>
        <#if uiSpec.bindedDataSourceInfo?has_content>
    protected void set${uiSpec.jobInfo.methodName}LinkToUrl(<@utils.makeRenderMethodCallParametersDeclaration uiSpec/>, <@getReturnedDataType uiSpec/> data ){
        <#else>
    protected void set${uiSpec.jobInfo.methodName}LinkToUrl(<@utils.makeRenderMethodCallParametersDeclaration uiSpec/>){
        </#if>
        StringBuilder sb = new StringBuilder();
        <#list uiSpec.bindedDataSourceInfo.children as segmentDsInfo>
        	<#if segmentDsInfo.elementTypeName == "const_string">
        sb.append("${segmentDsInfo.dataSourceExpression}");
        	</#if>
        </#list>
    }
    </#if>
    
    <#if uiSpec.bindedDataSourceInfo?has_content>
    protected <@getReturnedDataType uiSpec/>  get${uiSpec.jobInfo.methodName}DataFromViewModel(<@utils.makeRenderMethodCallParametersDeclaration uiSpec/>) throws Exception{
           <#if uiSpec.bindedDataSourceInfo.type == "java_variable">
        return viewModel.get${uiSpec.bindedDataSourceInfo.variableName?cap_first}();
           <#elseif uiSpec.bindedDataSourceInfo.type == "string_concat_function">
        StringBuilder sb = new StringBuilder();
           // TODO: 拼字符串还没做
        return null;
           <#elseif uiSpec.bindedDataSourceInfo.type == "model_path">
               <#list uiSpec.bindedDataSourceInfo.children as dsInfo>
                   <#if dsInfo?index == 0>
                       <#if dsInfo.varScope == "page">
        ${dsInfo.javaTypeName} data_1 = viewModel.get${dsInfo.variableName?cap_first}();
                       <#else>
        ${dsInfo.javaTypeName} data_1 = inputData.get${dsInfo.variableName?cap_first}();
                       </#if>
        if (data_1 == null){
            return null;
        }
                   <#else>
                       <#if dsInfo?has_next>
        ${dsInfo.javaTypeName} data_${dsInfo?index+1} = data${dsInfo?index}.get${dsInfo.variableName?cap_first}();
        if (data_${dsInfo?index+1} == null){
            return null;
        }
                    <#else>
        return data_${dsInfo?index}.get${dsInfo.variableName?cap_first}();
                       </#if>
                   </#if>
               </#list>
           </#if>
    }
    </#if>
    
    <#if uiSpec.bindedDataSourceInfo?has_content>
    protected boolean isNeedRender${uiSpec.jobInfo.methodName}(<@utils.makeRenderMethodCallParametersDeclaration uiSpec/>, <@getReturnedDataType uiSpec/> data) {
        <#if uiSpec.elementTypeName != "field">
        if (data == null){
            return false;
        }
        </#if>
    <#else>
    protected boolean isNeedRender${uiSpec.jobInfo.methodName}(<@utils.makeRenderMethodCallParametersDeclaration uiSpec/>) {
        // even this is MUST to show, just leave this method as customization pointer to control your view
    </#if>
        return true;
    }
</#list>
}

<#macro getReturnedDataType uiSpec>
<@compress single_line=true>
<#if uiSpec.bindedDataSourceInfo.list>
    List<${uiSpec.bindedDataSourceInfo.javaTypeName}>
<#else>
    ${uiSpec.bindedDataSourceInfo.javaTypeName}
</#if>
</@>
</#macro>
