package poc.myDbTool.tokens;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import poc.myDbTool.parsers.TokenParser;

public class PKeyFieldNameTokenParser extends BaseTokenParser {

	protected static final Pattern ptnStart = Pattern.compile("^(.+)?\\s*:");
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
		Matcher m = ptnStart.matcher(startToken);
		m.find();
		String dataType = m.group(1);
		result.put("fieldNames", Arrays.asList(dataType.trim().split("\\s*,\\s*")));
		return result;
	}

	@Override
	protected Pattern getStartToken() {
		return ptnStart;
	}

}
