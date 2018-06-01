package uispec.parser.specelement;

public class PageUiSpec extends BaseUiSpecElement {
    protected String title;
    protected String name;
    
    
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
    
    
}
