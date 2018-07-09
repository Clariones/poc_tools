package uispec.parser.specelement;

public class TextUiSpec extends BaseUiSpecElement {
    protected int maxLine = -1;
    protected String format;
    
    
    public String getFormat() {
        return format;
    }

    public void setFormat(String format) {
        this.format = format;
    }

    public int getMaxLine() {
        return maxLine;
    }

    public void setMaxLine(int maxLine) {
        this.maxLine = maxLine;
    }
    
}
