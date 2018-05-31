package viewcomponent;

/**
 * 文本组件
 * <p>
 * componentType=text
 * </p>
 * 
 * content为要显示的内容。
 * @author clariones
 */
public class TextViewComponent extends BaseViewComponent {

    public TextViewComponent() {
	this(null);
    }
    
    public TextViewComponent(String content) {
	this(content, null);
    }
    
    public TextViewComponent(String content, String classes) {
	this(content, classes, null);
    }
    
    public TextViewComponent(String content, String classes, String tag) {
	this.setContent(content);
	this.setClasses(classes);
	this.setTag(tag);
	this.setComponentType("text");
    }
}
