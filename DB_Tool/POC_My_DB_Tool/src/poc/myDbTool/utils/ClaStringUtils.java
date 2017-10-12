package poc.myDbTool.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import poc.myDbTool.parsers.ParseContext;

public class ClaStringUtils {

	public static String addLineNumber(String fileContent) {
		boolean metLineEnd = false;
		StringBuilder sb = new StringBuilder("@line 1 ");
		int pos = 0;
		int size = fileContent.length();
		int lineNum = 1;
		char lastLE = 0;
		while(pos < size){
			char c = fileContent.charAt(pos++);
			if (c == '\r' || c == '\n'){
				if (metLineEnd ==  true && c != lastLE){
				}else{
					sb.append("\n@line ").append(++lineNum).append(" ");
					metLineEnd = true;
					lastLE = c;
				}
				continue;
			}
			metLineEnd = false;
			sb.append(c);
		}
		return sb.toString();
	}

	private static final Pattern ptnLineNumber = Pattern.compile("^@line (\\d+) ");
	public static String removeLineNumber(ParseContext ctx, String rst) {
		if (!rst.startsWith("@line ")){
			return rst;
		}
		Matcher m = ptnLineNumber.matcher(rst);
		if (!m.find()){
			return rst;
		}
		int lineNumber = Integer.parseInt(m.group(1));
		ctx.setLineNum(lineNumber);
		rst = rst.substring(m.group().length());
		return rst;
	}

	private static final Pattern ptnBlankLine = Pattern.compile("@line \\d+ [ \t]*\n");
	private static final Pattern ptnSingleLineComments = Pattern.compile("@line \\d+ \\s*//[^\n]*\n");
	private static final Pattern ptnLineEndComments = Pattern.compile("\\s*//[^\n]*\n");
	public static String removeBlankLines(String fileContent) {
		boolean dump = false;
		fileContent = ptnBlankLine.matcher(fileContent).replaceAll("");
		if (dump) System.out.println("===after remove blank line===\n" + fileContent);
		fileContent = ptnSingleLineComments.matcher(fileContent).replaceAll("");
		if (dump) System.out.println("===after remove single line comments===\n" + fileContent);
		fileContent = ptnLineEndComments.matcher(fileContent).replaceAll("\n");
		if (dump) System.out.println("===after remove line end comments===\n" + fileContent);
		return fileContent.trim();
	}
	public static boolean isEmpty(String str) {
		if (str == null){
			return true;
		}
		return str.isEmpty();
	}
}