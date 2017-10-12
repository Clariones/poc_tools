package poc.myDbTool.parsers;

import java.util.Map;

public interface TokenParser {

	void tryToParse(ParseContext ctx, Map<String, Object> result);

}
