package poc.myDbTool.tokens;

import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import poc.myDbTool.parsers.ParseContext;
import poc.myDbTool.parsers.TokenParser;
import poc.myDbTool.utils.ClaStringUtils;

public abstract class BaseTokenParser implements TokenParser {

	abstract List<TokenParser> getChildTokenParsers();
	
	@Override
	public void tryToParse(ParseContext ctx, Map<String, Object> result) {
		String strContent = ctx.getLeftContent();
		String startToken = tryToFindStartToken(strContent);
		if (startToken == null){
			return;
		}
		System.out.println(this.getClass()+" found \"" + startToken+"\"");
		strContent = removeStartToken(strContent, startToken, ctx);
		Map<String, Object> currentTokenResult = addParseResult(result, startToken);
		System.out.println("Current result: " + currentTokenResult);
		ctx.setLeftContent(strContent);
		if (getChildTokenParsers() != null){
			while(ctx.getLeftContent() != null && ctx.getLeftContent().isEmpty()==false){
				ctx.setBeParsed(false);
				for(TokenParser subToken : getChildTokenParsers()){
					subToken.tryToParse(ctx, currentTokenResult);
				}
				if (ctx.isBeParsed()){
					continue;
				}
				break;
			}
			
		}
		ctx.setBeParsed(true);
		strContent = ctx.getLeftContent();
		String endToken = tryToFindEndToken(strContent);
		if (endToken == null){
			throw new RuntimeException("debug later @line " + ctx.getLineNum());
		}
		strContent = removeEndToken(strContent, endToken, ctx);
		ctx.setLeftContent(strContent);
	}


	protected String removeEndToken(String strContent, String endToken, ParseContext ctx) {
		return removeStrFromHead(strContent, endToken, ctx);
	}

	
	protected String removeStrFromHead(String srcStr, String removeTarget, ParseContext ctx) {
		String rst = srcStr.substring(removeTarget.length()).trim();
		rst = ClaStringUtils.removeLineNumber(ctx, rst);
		return rst.trim();
	}

	

	protected String tryToFindEndToken(String strContent) {
		Pattern ptnEndToken = getEndToken();
		Matcher m = ptnEndToken.matcher(strContent);
		if (!m.find()){
			return null;
		}
		return m.group();
	}

	abstract protected Pattern getEndToken();

	abstract protected Map<String, Object> addParseResult(Map<String, Object> result, String startToken);

	protected String removeStartToken(String strContent, String startToken, ParseContext ctx) {
		return removeStrFromHead(strContent, startToken, ctx);
	}

	protected String tryToFindStartToken(String strContent) {
		Pattern ptnEndToken = getStartToken();
		Matcher m = ptnEndToken.matcher(strContent);
		if (!m.find()){
			return null;
		}
		return m.group();
	}

	abstract protected Pattern getStartToken();

}
