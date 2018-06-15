package test.generator.render;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.util.List;
import java.util.Map;

import org.junit.Test;

import poc.utils.DebugUtil;
import uispec.parser.specelement.PageUiSpec;
import uispec.parser.specparser.XMLSpecParser;

public class RenderTemplateGeneratorTest {
    private XMLSpecParser parser;
    
    private List<PageUiSpec> parseTestFile() throws Exception {
	String baseFolder = "/works/jobs/sx_shequ/workspace/poc_ui_codegen/template/spec/";
	String fileName = "shuxiang-spec.xml";
	
	parser = new XMLSpecParser();
	return parser.parseFile(new File(new File(baseFolder),fileName));
    }
    
    @Test
    public void test() throws Exception {
	//String inputFileName = "/works/jobs/sx_shequ/workspace/poc_ui_codegen/test_ui_specs.json";
	String templateFolder = "/works/jobs/sx_shequ/workspace/poc_ui_codegen/template";
	File baseFolder = new File("/works/jobs/sx_shequ/workspace/poc_ui_codegen");
	File outputBaseFolder = new File("/works/jobs/sx_shequ/workspace/shuxiang_backend/shuxiang_code/shuxiang_custom_src/com/terapico/shuxiang/wxalayout");
	
	List<PageUiSpec> pages = parseTestFile();
	DebugUtil.dumpObjectToJson("初步读取结果：", pages);
	
	RenderTemplateGenerator worker = new RenderTemplateGenerator();
	worker.setOutputBaseFolder(outputBaseFolder);
	worker.setPackageBaseName("poc.shuxiang.wxalayout");
	worker.setPages(pages);
	worker.setTemplateBaseFolder(new File(templateFolder));
	
	Map<String, Object> data = worker.preprocess();
//	DebugUtil.dumpObjectToJson("JOBs", data);
	System.out.println("开始生成代码");
	
	String outputFile = "/works/jobs/sx_shequ/eclips_ws/POC_viewComponent/test/test/generator/render/CustomerHomePageRender.java";
	
	worker.produce(data, null);//new OutputStreamWriter(new FileOutputStream(outputFile)));
    }

}
