package poc.myDbTool.parsers;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import poc.myDbTool.tokens.TableDefinitionTokenParser;
import poc.myDbTool.utils.ClaStringUtils;

public class TableDefinitionParser {
	protected static final List<TokenParser> parsers = new ArrayList<TokenParser>();
	static {
		parsers.add(new TableDefinitionTokenParser());

	}

	public void parse(Map<String, Object> result, String fileContent) {

		ParseContext ctx = new ParseContext();
		try {
			ctx.setBeParsed(false);
			fileContent = ClaStringUtils.removeLineNumber(ctx, fileContent);
			ctx.setLeftContent(fileContent);

			while (!(fileContent == null || fileContent.isEmpty())) {
				for (TokenParser tokenParser : parsers) {
					tokenParser.tryToParse(ctx, result);
				}
				if (ctx.isBeParsed()) {
					ctx.setBeParsed(false);
					continue;
				}
				break;
			}
		} finally {
			System.out.println(ctx.getLeftContent());
			System.out.println(result);
			Gson gson = new GsonBuilder().setPrettyPrinting().create();
			String jsonStr = gson.toJson(result);
			System.out.println(jsonStr);
		}
	}

}
