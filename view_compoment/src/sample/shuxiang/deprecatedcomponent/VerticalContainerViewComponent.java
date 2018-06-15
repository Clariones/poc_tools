package sample.shuxiang.deprecatedcomponent;

import com.terapico.caf.viewcomponent.ContainerViewComponent;

/**
 * 
 * @author clariones
 *
 * type="vertical-container"
 * 竖向container
 */
public class VerticalContainerViewComponent extends ContainerViewComponent {

    public VerticalContainerViewComponent() {
	this(null);
    }

    public VerticalContainerViewComponent(String classes) {
	super(classes);
	this.setComponentType("vertical-container");
    }
    
}
