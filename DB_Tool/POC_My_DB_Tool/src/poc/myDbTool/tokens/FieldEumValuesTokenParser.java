package poc.myDbTool.tokens;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import poc.myDbTool.parsers.TokenParser;

public class FieldEumValuesTokenParser extends BaseTokenParser {
	protected static final String ENUM_VALUE = "[a-zA-Z][^,\\|\r\n]+";
	protected static final String ENUM_VAL_SEP = "\\s*\\|\\|\\s*";
	protected static final Pattern ptnStart = Pattern
			.compile("^"+ENUM_VALUE+ENUM_VAL_SEP+ENUM_VALUE+"(" + ENUM_VAL_SEP+ENUM_VALUE+")*", Pattern.CASE_INSENSITIVE);
	protected static final Pattern ptnEnd = Pattern.compile("^[\\s,\n\r]*");

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
		String[] values = startToken.trim().split("\\s*\\|\\|\\s*");
		result.put("valueList", Arrays.asList(values));
		return result;
	}

	@Override
	protected Pattern getStartToken() {
		return ptnStart;
	}
	
}
