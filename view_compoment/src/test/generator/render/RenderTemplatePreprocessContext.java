package test.generator.render;

import java.util.HashMap;
import java.util.Map;

public class RenderTemplatePreprocessContext {
    protected Map<String, Integer> sectionNameMap;

    private void initMembers() {
	sectionNameMap = new HashMap<String, Integer>();
	
    }
    
    public RenderTemplatePreprocessContext() {
	super();
	initMembers();
    }

    

    public Map<String, Integer> getSectionNameMap() {
        return sectionNameMap;
    }

    public void setSectionNameMap(Map<String, Integer> sectionNameMap) {
        this.sectionNameMap = sectionNameMap;
    }
    
    
    
}
