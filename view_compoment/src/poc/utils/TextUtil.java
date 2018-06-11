package poc.utils;

public class TextUtil {

    public static boolean isBlank(String value) {
	if (value == null || value.isEmpty()) {
	    return true;
	}
	for(char c : value.toCharArray()) {
	    if (!Character.isWhitespace(c)) {
		return false;
	    }
	}
	return true;
    }
    public static String hyphenCaseToBigCamelName(String name) {
        if (name == null || name.isEmpty()) {
            return name;
        }
        String[] nameSegs = name.split("[\\-_]");
        return TextUtil.toBigCamelCase(nameSegs);
    }

    public static String toBigCamelCase(String[] nameSegs) {
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

    public static String capFirst(String str) {
        if (str == null || str.isEmpty()) {
            return str;
        }
        return Character.toUpperCase(str.charAt(0)) + str.substring(1);
    }

    public static String uncapFirst(String str) {
        if (str == null || str.isEmpty()) {
            return str;
        }
        return Character.toLowerCase(str.charAt(0)) + str.substring(1);
    }

}
