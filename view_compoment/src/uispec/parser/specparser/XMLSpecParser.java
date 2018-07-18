package uispec.parser.specparser;

import java.io.File;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.terapico.util.FileUtils;
import com.terapico.util.XMLDecodeUtils;
import com.terapico.utils.DebugUtil;

import uispec.parser.specelement.BaseUiSpecElement;
import uispec.parser.specelement.DataSourceUiSpec;
import uispec.parser.specelement.PageUiSpec;

public class XMLSpecParser extends BaseSpecParser {

    public List<PageUiSpec> parseFile(File file) throws Exception {
        String fileContent = FileUtils.readFileAsString(file);
        Map<String, Object> dataResult = XMLDecodeUtils.decodeXmlString(fileContent);
        DebugUtil.dumpAsJson(dataResult, true);

        Map<String, Object> root = (Map<String, Object>) dataResult.get("root");
        List<Map<String, Object>> pages = (List<Map<String, Object>>) root.get("children");
        List<PageUiSpec> pageSpecs = new ArrayList<PageUiSpec>();

        if(pageSpecs == null || pageSpecs.isEmpty()) {
            return null;
        }
        for (Map<String, Object> page : pages) {
            PageUiSpec pageSpec = (PageUiSpec) parseUiSpecElement(root, page);
            if (pageSpec != null) {
                pageSpecs.add(pageSpec);
            }
        }
        return pageSpecs;
    }

    private BaseUiSpecElement parseUiSpecElement(Map<String, Object> root, Map<String, Object> specData)
            throws Exception {
        List<Map<String, Object>> children = (List<Map<String, Object>>) specData.get("children");
        String elementName = (String) specData.get("__name");

        String elementSpecClassName = BaseUiSpecElement.class.getPackage().getName();
        elementSpecClassName += "." + toSpecElementClassName(elementName);
        // System.out.println(elementSpecClassName);
        BaseUiSpecElement uiSpecElement = (BaseUiSpecElement) Class.forName(elementSpecClassName).newInstance();
        if (uiSpecElement.isIgnoreWhenGeneration()) {
            return null;
        }
        String nameSpace = (String) specData.get("__namespace");
        if (nameSpace != null) {
            nameSpace = getNameSpaceUrl(nameSpace, (Map<String, String>) root.get("__namespace"));
            specData.put("__namespace", nameSpace);
        }

        for (String key : specData.keySet()) {
            try {
                if (isStandardNamespace(nameSpace)) {
                    assignProperty(uiSpecElement, key, specData.get(key));
                } else {
                    throw new UnsupportedOperationException("Unknown namespace " + nameSpace);
                }
            } catch (Exception e) {
                throw new Exception("Exception at line " + specData.get("__line"), e);
            }
        }

        if (children == null) {
            return uiSpecElement;
        }

        List<BaseUiSpecElement> childSpecs = new ArrayList<BaseUiSpecElement>();
        for (Map<String, Object> child : children) {
            BaseUiSpecElement childSpec = parseUiSpecElement(root, child);
            if (childSpec == null) {
                continue;
            }
            childSpecs.add(childSpec);
        }
        uiSpecElement.setChildren(childSpecs);
        return uiSpecElement;
    }

    private String getNameSpaceUrl(String nameSpace, Map<String, String> nsMap) {
        assert (nameSpace != null);
        return nsMap.get(nameSpace);
    }

    private void assignProperty(BaseUiSpecElement uiSpecElement, String propertyName, Object propertyValue)
            throws Exception {
        if (propertyName.equals("class")) {
            propertyName = "css-class";
        }
        if (propertyName.equals("children")) {
            return;
        }
        if (propertyName.equals("__namespace")) {
            uiSpecElement.setElementNameSpace((String) propertyValue);
            return;
        }
        if (propertyName.equals("__name")) {
            uiSpecElement.setElementTypeName(((String) propertyValue));
            return;
        }
        if (propertyName.equals("__text")) {
            uiSpecElement.setElementTextContent(((String) propertyValue + "").trim());
            return;
        }
        if (propertyName.equals("__line")) {
            uiSpecElement.setElementDeclaredLineNumber(Integer.parseInt((String) propertyValue));
            return;
        }
        if (propertyName.startsWith("__")) {
            throw new RuntimeException("Undefined reserve key: " + propertyName);
        }
        if (uiSpecElement instanceof DataSourceUiSpec) {
            DataSourceUiSpec dsSpec = (DataSourceUiSpec) uiSpecElement;
            dsSpec.handlePropertyAssignment(propertyName, propertyValue);
            return;
        }
        assignPropertyToBean(uiSpecElement, propertyName, propertyValue);
    }

    private void assignPropertyToBean(BaseUiSpecElement uiSpecElement, String propertyName, Object propertyValue)
            throws Exception {
        Field field = findField(toClassMemberName(propertyName), uiSpecElement.getClass());
        propertyValue = preprocessPropertyValue(uiSpecElement, propertyName, propertyValue, field);
        String setterName = toSetterName(propertyName);
        Method setterMethod = uiSpecElement.getClass().getMethod(setterName, field.getType() );
        setterMethod.invoke(uiSpecElement, propertyValue);
    }

}
