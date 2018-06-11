package poc.utils;

import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.terapico.util.TextUtil;

public class DebugUtil {

    public static void dumpObjectToJson(String prompt, Object dataResult) {
        if (!TextUtil.isBlank(prompt)) {
            System.out.println(prompt);
        }
        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        mapper.setSerializationInclusion(Include.NON_NULL);
        String str;
	try {
	    str = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(dataResult);
	    System.out.println(str);
	} catch (JsonProcessingException e) {
	    e.printStackTrace();
	}
    }

}
