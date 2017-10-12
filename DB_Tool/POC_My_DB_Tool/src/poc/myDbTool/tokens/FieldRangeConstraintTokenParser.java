package poc.myDbTool.tokens;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import poc.myDbTool.parsers.TokenParser;
import poc.myDbTool.utils.ClaStringUtils;

public class FieldRangeConstraintTokenParser extends BaseTokenParser {
	protected static final Pattern ptnStart = Pattern
			.compile("^\\[([^,\\(\\)\\[\\]]*),?([^,\\(\\)\\[\\]]+)\\]", Pattern.CASE_INSENSITIVE);
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
		Matcher m = ptnRange.matcher(startToken);
		m.find();
		String startType = m.group(1);
		String endType = m.group(5);
		String startValue = m.group(2);
		String endValue = m.group(4);
		String diffFlag = m.group(3);
		Map<String, Object> rangeData = new HashMap<String, Object>();
		if (!ClaStringUtils.isEmpty(startType)){
			rangeData.put("equalMin", startType.equals("["));
		}
		if (!ClaStringUtils.isEmpty(endType)){
			rangeData.put("equalMax", endType.equals("]"));
		}
		if (ClaStringUtils.isEmpty(diffFlag)){
			rangeData.put("maxValue", startValue);
			rangeData.put("minValue", startValue);
		}else{
			if (ClaStringUtils.isEmpty(endValue)){
				rangeData.put("maxValue", "null");
			}else{
				rangeData.put("maxValue", endValue);
			}
			if (ClaStringUtils.isEmpty(startValue)){
				rangeData.put("minValue", "null");
			}else{
				rangeData.put("minValue", startValue);
			}
		}
		result.put("range", rangeData);
		return result;
	}

	protected static final Pattern ptnRange= Pattern.compile("([\\(\\[])([^,]*)(,?)([^,]*)([\\]\\)])");
	@Override
	protected Pattern getStartToken() {
		return ptnStart;
	}

}
