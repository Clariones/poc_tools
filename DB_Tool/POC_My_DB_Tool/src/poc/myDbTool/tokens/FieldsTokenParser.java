package poc.myDbTool.tokens;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

import poc.myDbTool.parsers.TokenParser;

public class FieldsTokenParser extends BaseTokenParser {
	protected static final Pattern ptnStart = Pattern.compile("^fields\\s*:\\s*\\[", Pattern.CASE_INSENSITIVE);
	protected static final Pattern ptnEnd = Pattern.compile("^\\]");
	
	protected static final List<TokenParser> childParsers = new ArrayList<>();
	static {
		childParsers.add(new FieldDataTokenParser());
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
		result.put("fields", new ArrayList<Object>());
		return result;
	}

	@Override
	protected Pattern getStartToken() {
		return ptnStart;
	}

	
	

}
