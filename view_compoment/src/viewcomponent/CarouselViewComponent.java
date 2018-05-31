package viewcomponent;

import java.util.ArrayList;
import java.util.List;

import sample.shuxiang.customer.MapUtil;
import static sample.shuxiang.customer.MapUtil.$;

/**
 * Carousel 组件.
 * <p>
 * componentType=carousel
 * </p>
 * 
 * <p>滑动的图像列表组件，并且点击后可以跳转到一个新的视图。</p>
 * 
 * <ul>
 * <li>content是一个列表。 每个列表元素有3个属性：
 * <ol>
 * <li>link：要跳转的目标视图URL</li>
 * <li>image: 要展示的图像URL</li>
 * <li>tips: 获得焦点后显示的文字。仅在桌面版有效。</li>
 * </ol>
 * </li>
 * </ul>
 * 
 * @author clariones
 */
public class CarouselViewComponent extends BaseViewComponent {
    /** 是否自动播放 */
    protected boolean autoPlay = true;
    /** 如果是自动播放，切换周期是多少毫秒 */
    protected int autoPlayPeriodInMs = 1000;
    /** 是否支持手工控制。 桌面版是指鼠标悬停时是否暂停自动播放。移动端忽略此属性。 */
    protected boolean manuallyControl = true;
    /** 滚动方向。 有两个值：horizontal 和 vertical。 默认是 horizontal */
    protected String rollingDirection = "horizontal";

    public CarouselViewComponent() {
	super();
	this.setComponentType("carrousel");
    }

    public void addItem(String image, String url, String tips) {
	List<Object> list = ensureContent();
	list.add(MapUtil.newMap($("link", url), $("image", image), $("tips", tips)));
    }

    private List<Object> ensureContent() {
	if (this.content == null) {
	    this.content = new ArrayList<Object>();
	}
	return (List<Object>) this.content;
    }

}
