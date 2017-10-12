package poc.myDbTool.utils;

import java.util.ArrayList;
import java.util.List;

public class ClaCollectionUtils {

	public static <T> List<T> asList(T newData) {
		List<T> list = new ArrayList<T>();
		list.add(newData);
		return list;
	}

}
