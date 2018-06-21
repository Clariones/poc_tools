package uispec.parser.specelement;

public class OptionUiSpec extends BaseUiSpecElement {
    protected String value;
    protected String displayText;
    protected Boolean checked;
    public String getValue() {
        return value;
    }
    public void setValue(String value) {
        this.value = value;
    }
    public String getDisplayText() {
        return displayText;
    }
    public void setDisplayText(String displayText) {
        this.displayText = displayText;
    }
    public Boolean getChecked() {
        return checked;
    }
    public void setChecked(Boolean checked) {
        this.checked = checked;
    }
    public OptionUiSpec() {
        super();
        this.setShouldBeRender(false);
    }
    
    
}
