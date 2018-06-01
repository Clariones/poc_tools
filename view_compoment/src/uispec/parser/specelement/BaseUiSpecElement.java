package uispec.parser.specelement;

import java.util.Arrays;
import java.util.List;

public class BaseUiSpecElement {
    protected String elementTypeName;
    protected String elementNameSpace;
    protected int elementDeclaredLineNumber;
    protected String elementTextContent;
    
    protected String showIf;
    protected String dataSource;
    protected boolean ignoreWhenGeneration;
    protected String id;
    protected String tag;
    protected String cssClass;

    protected List<BaseUiSpecElement> children;
    
    
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
    
    public List<String> getDataSourceExpressionList() {
	if (dataSource != null) {
	    return Arrays.asList(dataSource);
	}
	return null;
    }
    
    
    
}
