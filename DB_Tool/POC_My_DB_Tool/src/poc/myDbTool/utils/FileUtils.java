package poc.myDbTool.utils;

import java.io.File;
import java.io.IOException;
import java.util.regex.Pattern;

public class FileUtils {
	private static final org.apache.commons.io.FileUtils apacheFU = null;
	public static String readFileAsString(File inputFile) throws IOException {
		// TODO Auto-generated method stub
		return apacheFU.readFileToString(inputFile, "UTF-8");
	}
	public static void write(File file, String jsonStr) throws IOException {
		apacheFU.write(file, jsonStr, "UTF-8");
	}
	
	

}
