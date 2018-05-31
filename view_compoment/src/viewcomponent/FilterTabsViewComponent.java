package viewcomponent;

import java.util.ArrayList;
import java.util.List;

import sample.shuxiang.customer.MapUtil;
import static sample.shuxiang.customer.MapUtil.$;

/**
 * Tabs 组件.
 * <p>
 * componentType=filter-tabs
 * </p>
 * 
 * <p>每个tab对应一个Filter，用户过滤用户数据。</p>
 * 
 * <ul>
 * <li>content是一个列表。 每个列表元素有以下属性：
 * <ol>
 * <li>link：点击触发的url</li>
 * <li>text: 要显示的tab名称。暂时不支持图像。 如果有需要，content 格式会重新定义。</li>
 * <li>selected: 是否被选中。</li>
 * <li>count: 对应的内容总条目数。有些情况下，需要显示一个数量在Tab标题上。</li>
 * </ol>
 * </li>
 * <li>Tab和内容无关，约定其互动规则为：点击tab后，返回新的页面。对应Tab的内容由后台提供。</li>
 * </ul>
 * 
 * @author clariones
 */
public class FilterTabsViewComponent extends BaseViewComponent {
    /** 是否自动播放 */
    protected boolean autoPlay = true;
    /** 如果是自动播放，切换周期是多少毫秒 */
    protected int autoPlayPeriodInMs = 1000;
    /** 是否支持手工控制。 桌面版是指鼠标悬停时是否暂停自动播放。移动端忽略此属性。 */
    protected boolean manuallyControl = true;
    /** 滚动方向。 有两个值：horizontal 和 vertical。 默认是 horizontal */
    protected String rollingDirection = "horizontal";

    public FilterTabsViewComponent() {
	super();
	this.setComponentType("filter-tabs");
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
