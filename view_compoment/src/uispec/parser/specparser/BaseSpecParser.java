package uispec.parser.specparser;

import java.io.File;
import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.terapico.util.TextUtil;

import uispec.parser.specelement.BaseUiSpecElement;
import uispec.parser.specelement.PageUiSpec;

public abstract class BaseSpecParser {
    public abstract List<PageUiSpec> parseFile(File file) throws Exception;
    
    public static void dumpObjectToJson(String prompt, Object dataResult) throws Exception {
        if (!TextUtil.isBlank(prompt)) {
            System.out.println(prompt);
        }
        ObjectMapper mapper = new ObjectMapper();
        mapper.setSerializationInclusion(Include.NON_NULL);
        String str = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(dataResult);
        System.out.println(str);
    }

    // 首字母大写，中划线转驼峰，后面加 UiSpec
    protected String toSpecElementClassName(String elementName) {
        String name = hyphenCaseToBigCamelName(elementName);
        return name +"UiSpec";
    }

    protected String toJavaVariableName(String name) {
	return uncapFirst(hyphenCaseToBigCamelName(name));
    }
    protected String toJavaClassName(String name) {
	return hyphenCaseToBigCamelName(name);
    }
    private String hyphenCaseToBigCamelName(String name) {
	if (name == null || name.isEmpty()) {
	    return name;
	}
	String[] nameSegs = name.split("-");
	return toBigCamelCase(nameSegs);
    }

    private String toBigCamelCase(String[] nameSegs) {
	if (nameSegs == null) {
	    return null;
	}
	if (nameSegs.length == 0) {
	    return "";
	}
	
	StringBuilder sb = new StringBuilder();
	for(String seg : nameSegs) {
	    if (seg == null || seg.isEmpty()) {
		continue;
	    }
	    sb.append(capFirst(seg.trim().toLowerCase()));
	}
	return sb.toString();
    }

    private String capFirst(String str) {
	if (str == null || str.isEmpty()) {
	    return str;
	}
	return Character.toUpperCase(str.charAt(0)) + str.substring(1);
    }
    private String uncapFirst(String str) {
	if (str == null || str.isEmpty()) {
	    return str;
	}
	return Character.toLowerCase(str.charAt(0)) + str.substring(1);
    }

    // 首字母大写，中划线转驼峰，前面加set
    protected String toSetterName(String propertyName) {
        return "set" + hyphenCaseToBigCamelName(propertyName);
    }

    protected String toClassMemberName(String propertyName) {
        return toJavaVariableName(propertyName);
    }

    protected boolean isStandardNamespace(String nameSpace) {
        return nameSpace != null && nameSpace.endsWith("//clariones.doublechaintech.com/uischema.xml");
    }

    protected Object preprocessPropertyValue(BaseUiSpecElement uiSpecElement, String propertyName, Object propertyValue, Field field) throws Exception {
        if (field.getType().equals(Boolean.TYPE) || field.getType().equals(boolean.class)) {
            Boolean newValue = Boolean.valueOf(String.valueOf(propertyValue));
            return newValue;
        }
        return propertyValue;
    }

    protected Field findField(String propertyName, Class clazz) throws NoSuchFieldException {
        Class curClass = clazz;
        NoSuchFieldException e = null;
        while (!curClass.equals(Object.class)) {
            try {
        	Field field = curClass.getDeclaredField(propertyName);
        	return field;
            } catch (NoSuchFieldException ei) {
        	e = ei;
            }
            curClass = curClass.getSuperclass();
        }
        throw e;
    }

    protected String getViewModelPackageName() {
        String name = System.getProperty("VIEW_MODEL_PACKAGE");
        if (name != null) {
            return name;
        }
        name = System.getenv("VIEW_MODEL_PACKAGE");
        if (name != null) {
            return name;
        }
        return "poc.viewmodel";
    }

}
