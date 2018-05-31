package viewcomponent;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

/**
 * 基础组件类。
 * 
 * 这里只是集中了公共字段。 具体每个组件会有特殊的字段，请参见各组件文档。
 * @author clariones
 *
 */
@JsonPropertyOrder({ "componentType", "content" })
public abstract class BaseViewComponent {
    /** 组件对象的ID。这个ID设计用于交互时，用来关联参与交互的对象。不涉及交互的组件对象，该值为空。*/
    protected String id;
    /** 组件类型名称。MUST。 各种组件的具体值，详见各组件文档 */
    protected String componentType;
    /** 组件使用的CSS类名称列表，用空格分开。例如“btn btn-primary”, 有些没有class，例如“form”,“hidden”这种不可见元素。 */
    protected String classes;
    /** 互动操作。 目前暂不使用 */
    protected Interaction interaction;
    /** 组件的内容。例如对"Text"就是要显示的文字，对“IMAGE”就是图片的URL。对 FormField 就是“缺省值”。 详见各组件具体文档 */
    protected Object content;
    /** 子组件列表 */
    protected List<BaseViewComponent> children;
    /** 组件的语义化名称。暂时不考虑，因为目前JSP渲染部分有用到，就把这个名字先保留下来，这里只是占个名字。 */
    protected String tag;
    /** 是否可见。MUST. 大部分组件是true。有些组件出于互动需要会初始为false，例如pop的消息。 */
    protected boolean visiable = true;
    /** 是否容器类组件。container，form等组件会为true。详见各个组件具体文档 */
    protected boolean beContaniner = false;
    /** 该组件点击后跳转的url. 这个只是简单的跳转，有条件的跳转或者需要计算的跳转，不在这里表示，而是使用 interaction */
    protected String linkToUrl;
    /** 内部使用。不会传递到前台 */
    @JsonIgnore
    protected transient boolean inputable = false; // MUST。这个随各个具体的元素变化，form field会是true。其他都是false。
						   // 方便检查后台传递的数据是否正常的。和显示无关。
    /** i18n 使用。暂时未使用 */
    protected String localKey;

    public boolean isBeContaniner() {
	return beContaniner;
    }

    public void setBeContaniner(boolean beContaniner) {
	this.beContaniner = beContaniner;
    }

    public String getLocalKey() {
	return localKey;
    }

    public void setLocalKey(String localKey) {
	this.localKey = localKey;
    }

    public String getId() {
	return id;
    }

    public void setId(String id) {
	this.id = id;
    }

    public String getComponentType() {
	return componentType;
    }

    public void setComponentType(String componentType) {
	this.componentType = componentType;
    }

    public String getClasses() {
	return classes;
    }

    public void setClasses(String classes) {
	this.classes = classes;
    }

    public Interaction getInteraction() {
	return interaction;
    }

    public void setInteraction(Interaction interaction) {
	this.interaction = interaction;
    }

    public Object getContent() {
	return content;
    }

    public void setContent(Object content) {
	this.content = content;
    }

    public List<BaseViewComponent> getChildren() {
	return children;
    }

    public void setChildren(List<BaseViewComponent> children) {
	this.children = children;
    }

    public String getTag() {
	return tag;
    }

    public void setTag(String tag) {
	this.tag = tag;
    }

    public boolean isVisiable() {
	return visiable;
    }

    public void setVisiable(boolean visiable) {
	this.visiable = visiable;
    }

    public boolean isInputable() {
	return inputable;
    }

    public void setInputable(boolean inputable) {
	this.inputable = inputable;
    }

    // 包装一些setter
    public BaseViewComponent assignId(String id) {
	this.setId(id);
	return this;
    }

    public BaseViewComponent assignClasses(String classes) {
	this.setClasses(classes);
	return this;
    }

    public BaseViewComponent assignContent(Object content) {
	this.setContent(content);
	return this;
    }

    public BaseViewComponent assignTag(String tag) {
	this.setTag(tag);
	return this;
    }
    public BaseViewComponent assignLinkToUrl(String url) {
	this.setLinkToUrl(url);
	return this;
    }

    // 下面是一些语法糖
    
    public String getLinkToUrl() {
        return linkToUrl;
    }

    public void setLinkToUrl(String linkToUrl) {
        this.linkToUrl = linkToUrl;
    }

    /**
     * 添加一个子组件。
     * 子组件将会添加到本组件的子组件列表的最后，并返回本组件。
     * @param childComponent
     * @return
     */
    public BaseViewComponent addChild(BaseViewComponent childComponent) {
	ensureChilder();
	children.add(childComponent);
	return this;
    }

    /**
     * 新建一个子组件。
     * 子组件将会添加到本组件的子组件列表的最后，并返回新添加的子组件。
     * @param childComponent
     * @return 子组件
     */
    public BaseViewComponent newChild(BaseViewComponent childComponent) {
	ensureChilder();
	children.add(childComponent);
	return childComponent;
    }

    protected void ensureChilder() {
	if (children != null) {
	    return;
	}
	children = new ArrayList<BaseViewComponent>();
    }

}
