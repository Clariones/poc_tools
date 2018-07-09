package uispec.parser.specelement;

public class FieldUiSpec extends BaseUiSpecElement {
    protected String label;
    protected String type;
    protected String placeholder;
    protected boolean required = false;
    protected boolean disabled = false;
    protected int maxLine = -1;
    protected String minValue;
    protected String maxValue;
    protected String name;
    
    
    public int getMaxLine() {
        return maxLine;
    }
    public void setMaxLine(int maxLine) {
        this.maxLine = maxLine;
    }
    public String getMinValue() {
        return minValue;
    }
    public void setMinValue(String minValue) {
        this.minValue = minValue;
    }
    public String getMaxValue() {
        return maxValue;
    }
    public void setMaxValue(String maxValue) {
        this.maxValue = maxValue;
    }
    public String getLabel() {
        return label;
    }
    public void setLabel(String label) {
        this.label = label;
    }
    public String getType() {
        return type;
    }
    public void setType(String type) {
        this.type = type;
    }
    public String getPlaceholder() {
        return placeholder;
    }
    public void setPlaceholder(String placeholder) {
        this.placeholder = placeholder;
    }
    public boolean isRequired() {
        return required;
    }
    public void setRequired(boolean required) {
        this.required = required;
    }
    public boolean isDisabled() {
        return disabled;
    }
    public void setDisabled(boolean disabled) {
        this.disabled = disabled;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    
    
}
