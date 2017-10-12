package poc.myDbTool.parsers;

public class ParseContext {
	protected String leftContent;
	protected boolean beParsed;
	protected int lineNum;
	
	public int getLineNum() {
		return lineNum;
	}
	public void setLineNum(int lineNum) {
		this.lineNum = lineNum;
	}
	public String getLeftContent() {
		return leftContent;
	}
	public void setLeftContent(String leftContent) {
		this.leftContent = leftContent;
	}
	public boolean isBeParsed() {
		return beParsed;
	}
	public void setBeParsed(boolean beParsed) {
		this.beParsed = beParsed;
	}
	
	
}
