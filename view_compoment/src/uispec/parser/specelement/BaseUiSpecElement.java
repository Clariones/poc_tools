package uispec.parser.specelement;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonIgnore;

import poc.utils.TextUtil;
import uispec.parser.datasource.DataSourceInfo;

public class BaseUiSpecElement {
    protected String elementTypeName;
    protected String elementNameSpace;
    protected int elementDeclaredLineNumber;
    protected String elementTextContent;

    protected String showIf;
    protected String dataSource;
    protected boolean ignoreWhenGeneration;
    protected boolean shouldBeRender = true;
    protected String id;
    protected String tag;
    protected String cssClass;
    protected boolean selfHanleListInput = false;
    protected boolean visible = true;

    protected List<BaseUiSpecElement> children;
    protected Map<String, Object> jobInfo;
    @JsonIgnore
    protected DataSourceInfo bindedDataSourceInfo;
    @JsonIgnore
    protected DataSourceInfo ancestDataSourceInfo;
    protected String linkToUrl;
    @JsonIgnore
    protected DataSourceInfo linkToDataSourceInfo;
    protected String statInPage;

    
    public boolean isVisible() {
        return visible;
    }

    public void setVisible(boolean visible) {
        this.visible = visible;
    }

    public String getStatInPage() {
        return statInPage;
    }

    public void setStatInPage(String statInPage) {
        this.statInPage = statInPage;
    }

    public boolean isShouldBeRender() {
        return shouldBeRender;
    }

    public void setShouldBeRender(boolean shouldBeRender) {
        this.shouldBeRender = shouldBeRender;
    }

    public boolean isSelfHanleListInput() {
        return selfHanleListInput;
    }

    public void setSelfHanleListInput(boolean selfHanleListInput) {
        this.selfHanleListInput = selfHanleListInput;
    }

    public DataSourceInfo getLinkToDataSourceInfo() {
        return linkToDataSourceInfo;
    }

    public void setLinkToDataSourceInfo(DataSourceInfo linkToDataSourceInfo) {
        this.linkToDataSourceInfo = linkToDataSourceInfo;
    }

    public DataSourceInfo getAncestDataSourceInfo() {
        return ancestDataSourceInfo;
    }

    public void setAncestDataSourceInfo(DataSourceInfo ancestDataSourceInfo) {
        this.ancestDataSourceInfo = ancestDataSourceInfo;
    }

    public String getElementTextContent() {
        return elementTextContent;
    }

    public void setElementTextContent(String elementTextContent) {
        this.elementTextContent = elementTextContent;
    }

    public String getDataSource() {
        return dataSource;
    }

    public void setDataSource(String dataSource) {
        this.dataSource = dataSource;
    }

    public boolean isIgnoreWhenGeneration() {
        return ignoreWhenGeneration;
    }

    public void setIgnoreWhenGeneration(boolean ignoreWhenGeneration) {
        this.ignoreWhenGeneration = ignoreWhenGeneration;
    }

    public String getElementTypeName() {
        return elementTypeName;
    }

    public void setElementTypeName(String elementTypeName) {
        this.elementTypeName = elementTypeName;
    }

    public String getElementNameSpace() {
        return elementNameSpace;
    }

    public void setElementNameSpace(String elementNameSpace) {
        this.elementNameSpace = elementNameSpace;
    }

    public int getElementDeclaredLineNumber() {
        return elementDeclaredLineNumber;
    }

    public void setElementDeclaredLineNumber(int elementDeclaredLineNumber) {
        this.elementDeclaredLineNumber = elementDeclaredLineNumber;
    }

    public List<BaseUiSpecElement> getChildren() {
        return children;
    }

    public String getShowIf() {
        return showIf;
    }

    public void setShowIf(String showIf) {
        this.showIf = showIf;
    }

    public void setChildren(List<BaseUiSpecElement> children) {
        this.children = children;
    }

    protected String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public String getCssClass() {
        return cssClass;
    }

    public void setCssClass(String cssClass) {
        this.cssClass = cssClass;
    }

    public String getLinkToUrl() {
        return linkToUrl;
    }

    public void setLinkToUrl(String linkToUrl) {
        this.linkToUrl = linkToUrl;
    }

    /**
     * 有多个数据源表达式的组件，需要重载这个函数，例如 carouselUiSpec
     * 
     * @return
     */
    public List<String> getDataSourceExpressionList() {
        if (dataSource != null) {
            return Arrays.asList(dataSource);
        }
        return null;
    }

    /**
     * 有多个数据源表达式的组件，需要重载这个函数，例如 carouselUiSpec
     * 
     * @return
     */
    public void setDataSourceExpressionResultList(List<DataSourceInfo> dataSourceInfoList) {
        if (dataSourceInfoList == null || dataSourceInfoList.isEmpty()) {
            bindedDataSourceInfo = null;
            return;
        }
        bindedDataSourceInfo = dataSourceInfoList.get(0);
    }

    public Map<String, Object> getJobInfo() {
        return jobInfo;
    }

    public void setJobInfo(Map<String, Object> jobInfo) {
        this.jobInfo = jobInfo;
    }

    public DataSourceInfo getBindedDataSourceInfo() {
        return bindedDataSourceInfo;
    }

    public void setBindedDataSourceInfo(DataSourceInfo bindedDataSourceInfo) {
        this.bindedDataSourceInfo = bindedDataSourceInfo;
    }

    public Map<String, String> getAdditonalExpressions() {
        if (TextUtil.isBlank(this.getLinkToUrl())) {
            return null;
        }
        Map<String, String> result = new HashMap<String, String>();
        result.put("linkToUrl", this.getLinkToUrl());
        return result;
    }

    public boolean setAdditionalDataSourceInfo(String key, DataSourceInfo dsInfo) {
        if (key.equals("linkToUrl")) {
            this.setLinkToDataSourceInfo(dsInfo);
            return true;
        }
        return false;
    }

}
