package uispec.parser.specelement;

public class MoneyUiSpec extends BaseUiSpecElement {
    protected String format = "￥#,##0.##";

    public String getFormat() {
        return format;
    }

    public void setFormat(String format) {
        this.format = format;
    }
    
}
