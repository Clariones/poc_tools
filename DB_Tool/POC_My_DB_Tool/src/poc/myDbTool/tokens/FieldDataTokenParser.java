package poc.myDbTool.tokens;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import poc.myDbTool.parsers.TokenParser;
import poc.myDbTool.utils.MapUtils;

public class FieldDataTokenParser extends BaseTokenParser {

	protected static final Pattern ptnStart = Pattern.compile("^(.+)?\\s*:", Pattern.CASE_INSENSITIVE);
	protected static final Pattern ptnEnd = Pattern.compile("^\\]");
	
	protected static final List<TokenParser> childParsers = new ArrayList<>();
	static {
		childParsers.add(new FieldNullableTokenParser());
		childParsers.add(new FieldDataTypeTokenParser());
		childParsers.add(new FieldRangeConstraintTokenParser());
		childParsers.add(new FieldEumValuesTokenParser());
		childParsers.add(new FieldVerifyRuleTokenParser());
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
		String fieldName = m.group(1);
		Map<String, Object> fieldData = new HashMap<String, Object>();
		fieldData.put("name", fieldName);
		MapUtils.addIntoList(result, fieldData, "fields");
		System.out.println(result);
		return fieldData;
	}

	@Override
	protected Pattern getStartToken() {
		return ptnStart;
	}

	@Override
	protected String tryToFindEndToken(String strContent) {
		return "";
	}

	
}
