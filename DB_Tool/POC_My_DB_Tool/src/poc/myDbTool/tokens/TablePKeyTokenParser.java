package poc.myDbTool.tokens;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import poc.myDbTool.parsers.TokenParser;
import poc.myDbTool.utils.MapUtils;

public class TablePKeyTokenParser extends BaseTokenParser {
	protected static final Pattern ptnStart = Pattern.compile("^pkey\\s*:\\s*\\{", Pattern.CASE_INSENSITIVE);
	protected static final Pattern ptnEnd = Pattern.compile("^\\}");
	
	protected static final List<TokenParser> childParsers = new ArrayList<>();
	static {
		childParsers.add(new PKeyFieldNameTokenParser());
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
		Map<String, Object> data = new HashMap<String, Object>();
		result.put("primaryKey", data);
		return data;
	}

	@Override
	protected Pattern getStartToken() {
		return ptnStart;
	}

}
