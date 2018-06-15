package sample.shuxiang;

import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.terapico.caf.viewcomponent.PageViewComponent;

import sample.shuxiang.customer.TestSite2;

public class TestMain {

    public static void main(String[] args) throws Exception {
	//ViewPageFactory factory = new BookSharing();
//	ViewPageFactory factory = new TestSite1();
	ViewPageFactory factory = new TestSite2();
//	ViewPageFactory factory = new HomePage();
	PageViewComponent page = factory.produce();
	page.fixLayoutTypeNames();
	dumpPage(page);
    }

    private static void dumpPage(PageViewComponent page) throws Exception{
//	Gson gson = new GsonBuilder().setPrettyPrinting().setDateFormat("yyyy-MM-dd'T'hh:mm:ss").create();
//	String jsonStr = gson.toJson(page);
	ObjectMapper mapper = new ObjectMapper();
	mapper.setSerializationInclusion(Include.NON_NULL);
	String jsonStr = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(page);
	System.out.println(jsonStr);
    }

}
