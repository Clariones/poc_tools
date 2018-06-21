package uispec.parser.specelement;

public class DateUiSpec extends BaseUiSpecElement {
    protected String format = "yyyy-MM-dd HH:mm:ss";

    public String getFormat() {
        return format;
    }

    public void setFormat(String format) {
        this.format = format;
    }
    
}
