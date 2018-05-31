package sample.shuxiang.deprecatedcomponent;

import viewcomponent.ContainerViewComponent;

/**
 * 
 * @author clariones
 *
 * type="horizontal-container"
 * 横向container
 */
public class HorizontalContainerViewComponent extends ContainerViewComponent {

    public HorizontalContainerViewComponent() {
	this(null);
    }

    public HorizontalContainerViewComponent(String classes) {
	super(classes);
	this.setComponentType("horizontal-container");
    }

    
}
