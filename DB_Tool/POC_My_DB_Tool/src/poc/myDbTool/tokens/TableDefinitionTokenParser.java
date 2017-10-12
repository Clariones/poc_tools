package poc.myDbTool.tokens;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import poc.myDbTool.parsers.TokenParser;
import poc.myDbTool.utils.MapUtils;

public class TableDefinitionTokenParser extends BaseTokenParser {
	protected static final Pattern ptnStart = Pattern.compile("^table\\s+([a-zA-Z_0-9]+)\\s*\\{", Pattern.CASE_INSENSITIVE);
	protected static final Pattern ptnEnd = Pattern.compile("^\\}");
	
	protected static final List<TokenParser> childParsers = new ArrayList<>();
	static {
		childParsers.add(new FieldsTokenParser());
		childParsers.add(new TablePKeyTokenParser());
	}
	
	
	@Override
	List<TokenParser> getChildTokenParsers() {
		return childParsers;
	}


	@Override
	protected Pattern getEndToken() {
		return ptnEnd;
	}


	@Override
	protected Map<String, Object> addParseResult(Map<String, Object> result, String startToken) {
		System.out.println("start token is : " + startToken);
		Matcher m = ptnStart.matcher(startToken);
		m.find();
		String tableName = m.group(1);
		Map<String, Object> tblData = new HashMap<String, Object>();
		tblData.put("name", tableName);
		MapUtils.addIntoList(result, tblData, "tables");
		System.out.println(result);
		return tblData;
	}


	@Override
	protected Pattern getStartToken() {
		return ptnStart;
	}

	

}
