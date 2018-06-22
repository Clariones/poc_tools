package uispec.parser.specelement;

public class MoneyUiSpec extends BaseUiSpecElement {
    protected String format = "￥#,###.##";

    public String getFormat() {
        return format;
    }

    public void setFormat(String format) {
        this.format = format;
    }
    
}
