package uispec.parser.specparser;

import java.io.File;
import java.lang.reflect.Field;
import java.util.List;

import uispec.parser.specelement.BaseUiSpecElement;
import uispec.parser.specelement.PageUiSpec;

public abstract class BaseSpecParser {
    public abstract List<PageUiSpec> parseFile(File file) throws Exception;
    
    // 首字母大写，中划线转驼峰，后面加 UiSpec
    protected String toSpecElementClassName(String elementName) {
        String name = poc.utils.TextUtil.hyphenCaseToBigCamelName(elementName);
        return name +"UiSpec";
    }

    protected String toJavaVariableName(String name) {
	return poc.utils.TextUtil.uncapFirst(poc.utils.TextUtil.hyphenCaseToBigCamelName(name));
    }
    protected String toJavaClassName(String name) {
	return poc.utils.TextUtil.hyphenCaseToBigCamelName(name);
    }
    // 首字母大写，中划线转驼峰，前面加set
    protected String toSetterName(String propertyName) {
        return "set" + poc.utils.TextUtil.hyphenCaseToBigCamelName(propertyName);
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
