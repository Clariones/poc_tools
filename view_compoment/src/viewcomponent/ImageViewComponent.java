package viewcomponent;

/**
 * 图像显示组件。
 * <p>
 * containerType=image
 * </p>
 * 
 * 这个是显示图像的，不包括上传、删除等。 需要编辑的，需要使用 type=image的form-field。
 * 
 * content 是 image 的URL。 clickToBiggerView 点击后是否需要显示大图。 默认true。
 * 
 * @author clariones
 *
 */
public class ImageViewComponent extends BaseViewComponent {
    public ImageViewComponent(String url) {
	this(url, null);
    }

    public ImageViewComponent(String url, String classes) {
	this(url, classes, true);
    }

    public ImageViewComponent(String url, String classes, boolean viewBigger) {
	this.setContent(url);
	this.setClasses(classes);
	this.setClickToBiggerView(viewBigger);
	this.setComponentType("image");
    }

    /** 点击后是否需要显示大图。 */
    boolean clickToBiggerView = true;

    public boolean isClickToBiggerView() {
	return clickToBiggerView;
    }

    public void setClickToBiggerView(boolean clickToBiggerView) {
	this.clickToBiggerView = clickToBiggerView;
    }

}
