package viewcomponent;

/**
 * 表单按钮组件。
 * <p>
 * componentType=form-action
 * </p>
 * 
 * content 按钮显示的文本。
 * actionUrl 按钮触发的操作对应的URL。
 * level 可以为： default, info, warning, error.
 * 
 * @author clariones
 *
 */
public class FormActionViewComponent extends BaseViewComponent {
    /** 按钮触发的操作对应的URL */
    protected String actionUrl;
    /** 按钮的级别。 可以为： default, info, warning, error. 默认情况下使用level指定按钮外观，不使用classes。*/
    protected String level = "default";
    
    
    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public String getActionUrl() {
        return actionUrl;
    }

    public void setActionUrl(String actionUrl) {
        this.actionUrl = actionUrl;
    }

    public FormActionViewComponent() {
	this("#","");
    }
    
    public FormActionViewComponent(String actionUrl, String content) {
	this(actionUrl, content, null);
    }

    public FormActionViewComponent(String actionUrl, String content, String level) {
	this(actionUrl,content, level, null);
    }

    public FormActionViewComponent(String actionUrl, String content, String level, String tag) {
	super();
	this.setActionUrl(actionUrl);
	this.setContent(content);
	this.setTag(tag);
	if (level != null) {
	    this.setLevel(level);
	}
	this.setComponentType("form-action");
    }
    
}
