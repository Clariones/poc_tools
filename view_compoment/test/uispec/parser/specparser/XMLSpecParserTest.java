package uispec.parser.specparser;

import java.io.File;
import java.util.List;

import org.junit.Test;

import uispec.parser.datasource.DataSourceInfo;
import uispec.parser.specelement.PageUiSpec;

public class XMLSpecParserTest {

    private XMLSpecParser parser;

    @Test
    public void test() throws Exception {
	List<PageUiSpec> pages = parseTestFile();
	XMLSpecParser.dumpObjectToJson("读取的文件初步分析结果", pages);
    }

    private List<PageUiSpec> parseTestFile() throws Exception {
	String baseFolder = "/works/jobs/hot_poc/prj_x/ui-spec";
	String fileName = "wxa_discuss.xml";
	
	parser = new XMLSpecParser();
	return parser.parseFile(new File(new File(baseFolder),fileName));
    }

    @Test
    public void testCollectionDataSource() throws Exception {
	List<PageUiSpec> pages = parseTestFile();
	DataSourceInfo dataSrcInfo = parser.collectDataSources(pages.get(0));
	XMLSpecParser.dumpObjectToJson("视图页的数据源分析结果", dataSrcInfo);

    }

}
