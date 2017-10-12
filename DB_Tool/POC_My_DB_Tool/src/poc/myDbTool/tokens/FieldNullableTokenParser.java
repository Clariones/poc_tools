package poc.myDbTool.tokens;

import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

import poc.myDbTool.parsers.TokenParser;

public class FieldNullableTokenParser extends BaseTokenParser {
	protected static final Pattern ptnStart = Pattern
			.compile("^\\bmust\\b", Pattern.CASE_INSENSITIVE);
	protected static final Pattern ptnEnd = Pattern.compile("[\\s,\n\r]*");

	@Override
	List<TokenParser> getChildTokenParsers() {
		return null;
	}

	@Override
	protected Pattern getEndToken() {
		return ptnEnd;
	}

	@Override
	protected Map<String, Object> addParseResult(Map<String, Object> result, String startToken) {
		result.put("nullable", false);
		return result;
	}

	@Override
	protected Pattern getStartToken() {
		return ptnStart;
	}

}
