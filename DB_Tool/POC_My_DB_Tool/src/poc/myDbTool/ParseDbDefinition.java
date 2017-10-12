package poc.myDbTool;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import poc.myDbTool.parsers.TableDefinitionParser;
import poc.myDbTool.utils.ClaStringUtils;
import poc.myDbTool.utils.FileUtils;

public class ParseDbDefinition {
	static final String testFilePath="./testinput/data_pre_process_definition.txt";
	static final String outFilePath="./testoutput/";
	
	public static void main(String[] args) throws Exception{
		File inputFile = new File(testFilePath);
		String fileContent = FileUtils.readFileAsString(inputFile);
//		System.out.println(fileContent);
		fileContent = ClaStringUtils.addLineNumber(fileContent);
//		System.out.println(fileContent);
		fileContent = ClaStringUtils.removeBlankLines(fileContent);
		System.out.println(fileContent);
		
		TableDefinitionParser rootParser = new TableDefinitionParser();
		Map<String, Object> result = new HashMap<String, Object>();
		rootParser.parse(result, fileContent);
		Gson gson = new GsonBuilder().setPrettyPrinting().create();
		String jsonStr = gson.toJson(result.get("tables"));
		FileUtils.write(new File(outFilePath+"tables.json"), jsonStr);
	}
}
