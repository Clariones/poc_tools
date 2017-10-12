package poc.myDbTool.utils;

import java.util.List;
import java.util.Map;

public class MapUtils {

	public static void addIntoList(Map<String, Object> result, Object newData, String key) {
		if (result == null){
			throw new RuntimeException("ASSERT FAIL: target map object is null");
		}
		Object existedObj = result.get(key);
		if (existedObj == null){
			List<Object> list = ClaCollectionUtils.asList(newData);
			result.put(key, list);
			return;
		}
		
		if (existedObj instanceof List){
			((List<Object>) existedObj).add(newData);
			return;
		}
		
		throw new RuntimeException("Why [" + key +"] is not a list? It's a " + existedObj.getClass());
	}

}
