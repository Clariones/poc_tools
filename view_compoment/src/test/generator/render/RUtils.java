package test.generator.render;

import poc.utils.TextUtil;
import uispec.parser.specelement.BaseUiSpecElement;

public class RUtils {

    public static String calcElementRenderName(RenderTemplatePreprocessContext jobContext, BaseUiSpecElement element) {
	String name = calcSimpleElementRenderName(element);
	if (!jobContext.getSectionNameMap().containsKey(name)) {
	    jobContext.getSectionNameMap().put(name, 1);
	    return name;
	}
	int cnt = jobContext.getSectionNameMap().get(name);
	cnt++;
	jobContext.getSectionNameMap().put(name, cnt);
	return name + "_" + cnt;
    }

    private static String calcSimpleElementRenderName(BaseUiSpecElement element) {
	// TODO Auto-generated method stub
	String typeName = TextUtil.hyphenCaseToBigCamelName(element.getElementTypeName());
	if (!TextUtil.isBlank(element.getName())) {
	    return typeName + TextUtil.hyphenCaseToBigCamelName(element.getName());
	}
	return typeName+"L" + element.getElementDeclaredLineNumber();
    }

}
