package test.generator.render;

import java.io.File;
import java.io.FileOutputStream;
import java.io.FilenameFilter;
import java.io.OutputStreamWriter;
import java.util.List;
import java.util.Map;

import org.junit.Test;

import poc.utils.DebugUtil;
import uispec.parser.specelement.PageUiSpec;
import uispec.parser.specparser.XMLSpecParser;

public class RenderTemplateGeneratorTest {
    private XMLSpecParser parser;
    private static String baseFolder = "./testinput/spec";

    private List<PageUiSpec> parseTestFile(String fileName) throws Exception {
        
        parser = new XMLSpecParser();
        return parser.parseFile(new File(new File(baseFolder), fileName));
    }

    @Test
    public void test1() throws Exception {
//        List<PageUiSpec> pages = parseTestFile("shuxiang-spec.xml");
//        String fileName = "shuxiang-spec-working.xml";
//        String fileName = "some-manually-work.xml";
//        String fileName = "shuxiang-spec-v2.xml";
    	String fileName = "shuxiang-spec-order.xml";
        genCodeBySpecFile(fileName);
    }
    
    @Test
    public void test_all() throws Exception {
        File inputFolder = new File(baseFolder);
        File[] files = inputFolder.listFiles(new FilenameFilter() {

            @Override
            public boolean accept(File dir, String name) {
                return name.matches("^shuxiang\\-spec\\-.+\\.xml$");
            }
            
        });
//        List<PageUiSpec> pages = parseTestFile("shuxiang-spec.xml");
//        String fileName = "shuxiang-spec-working.xml";
//        String fileName = "shuxiang-spec-working.xml";
//        String fileName = "shuxiang-spec-v2.xml";
        for(File file : files) {
        String fileName = file.getName();
        genCodeBySpecFile(fileName);
        }
    }
    
//    @Test
//    public void test2() throws Exception {
//        String fileName = "shuxiang-spec-cla.xml";
//        genCodeBySpecFile(fileName);
//    }

    private void genCodeBySpecFile(String fileName) throws Exception {
        // String inputFileName =
        // "/works/jobs/sx_shequ/workspace/poc_ui_codegen/test_ui_specs.json";
        String templateFolder = "./testinput/";
        System.setProperty("skynet.model", "shuxiang");
        String workspacePath = "/works/jobs/shuxiang_v2/workspace";
		System.setProperty("skynet.output.basefolder", workspacePath);
        File outputBaseFolder = new File(workspacePath + "/shuxiang-biz-suite/bizcore/WEB-INF/shuxiang_custom_src/com/terapico/shuxiang/wxalayout");
        
        List<PageUiSpec> pages = parseTestFile(fileName);
        if (pages == null || pages.isEmpty()) {
            return;
        }
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
