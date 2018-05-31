package sample.shuxiang.deprecatedcomponent;

import viewcomponent.BaseViewComponent;

/**
 * 
 * @author clariones
 *
 * type="link"
 * content 是页面的title。
 * clickToBiggerView 点击后是否需要显示大图。 默认true。
 */
public class LinkViewComponent extends BaseViewComponent {
    protected String url;
    
    public String getUrl() {
        return url;
    }
    public void setUrl(String url) {
        this.url = url;
    }
    public LinkViewComponent() {
	this(null);
    }
    public LinkViewComponent(String url) {
	this(url, null);
    }
    public LinkViewComponent(String url, String classes) {
	this(url, classes, null);
    }
    public LinkViewComponent(String url, String classes, String tag) {
	this.setUrl(url);
	this.setClasses(classes);
	this.setTag(tag);
	this.setComponentType("link");
    }

    
}
