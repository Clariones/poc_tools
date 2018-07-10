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

    private List<PageUiSpec> parseTestFile(String fileName) throws Exception {
        String baseFolder = "/works/jobs/sx_shequ/eclips_ws/POC_viewComponent/testinput/spec";
        parser = new XMLSpecParser();
        return parser.parseFile(new File(new File(baseFolder), fileName));
    }

    @Test
    public void test1() throws Exception {
        List<PageUiSpec> pages = parseTestFile("shuxiang-spec.xml");
        String fileName = "shuxiang-spec-working.xml";
        genCodeBySpecFile(fileName);
    }
    
//    @Test
//    public void test2() throws Exception {
//        String fileName = "shuxiang-spec-cla.xml";
//        genCodeBySpecFile(fileName);
//    }

    private void genCodeBySpecFile(String fileName) throws Exception {
        // String inputFileName =
        // "/works/jobs/sx_shequ/workspace/poc_ui_codegen/test_ui_specs.json";
        String templateFolder = "/works/jobs/sx_shequ/eclips_ws/POC_viewComponent/testinput/";
        File outputBaseFolder = new File("/works/jobs/sx_shequ/workspace/shuxiang-biz-suite/bizcore/WEB-INF/shuxiang_custom_src/com/terapico/shuxiang/wxalayout");
        System.setProperty("skynet.model", "shuxiang");
        System.setProperty("skynet.output.basefolder", "/works/jobs/sx_shequ/workspace");
        
        List<PageUiSpec> pages = parseTestFile(fileName);
        //DebugUtil.dumpObjectToJson("初步读取结果：", pages);

        RenderTemplateGenerator worker = new RenderTemplateGenerator();
        worker.setOutputBaseFolder(outputBaseFolder);
        worker.setPackageBaseName("poc.shuxiang.wxalayout");
        worker.setUserContextName("ShuxiangUserContext");
        worker.setPages(pages);
        worker.setTemplateBaseFolder(new File(templateFolder));

        Map<String, Object> data = worker.preprocess();
        // DebugUtil.dumpObjectToJson("JOBs", data);
        System.out.println("开始生成代码");

//        String outputFile = "/works/jobs/sx_shequ/eclips_ws/POC_viewComponent/test/test/generator/render/CustomerHomePageRender.java";

        worker.produce(data, null);// new OutputStreamWriter(new FileOutputStream(outputFile)));
    }

}
