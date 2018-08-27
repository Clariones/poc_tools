import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URLDecoder;
import java.net.URLEncoder;

public class UrlEncode {
	protected static String encodeUrl(String string){
		if(string == null){
			return null;
		}
		try {
			int pos = string.indexOf("://");
			String protocol = "";
			if (pos > 0) {
				protocol = string.substring(0, pos+3);
				string = string.substring(pos + 3);
			}
			
			StringBuilder sb = new StringBuilder(protocol);
			int lastPos = 0;
			System.out.println("==>prefix: " + protocol);
			while(pos < string.length()) {
				pos = string.indexOf("/", lastPos);
				if (pos < 0) {
					break;
				}
				String seg = string.substring(lastPos, pos);
				System.out.println("==>seg: " + seg);
				sb.append(URLEncoder.encode(seg, "utf-8"));
				sb.append("/");
				lastPos = pos+1;
			}
			if (lastPos < string.length()) {
				sb.append(URLEncoder.encode(string.substring(lastPos), "utf-8"));
			}
			
			return sb.toString();
		} catch (UnsupportedEncodingException e) {
			return string;
		}
		
	}
	
	public static void main(String[] args) {
		String test = "http://abc.com.cn/中文/example.jpg/";
		
		String result = encodeUrl(test);
		System.out.println(test);
		System.out.println(result);
		
		String inUrl = "http://127.0.0.1:8280/public/example/product/shores/girls/pid456/skuid456/255/130/245/46/%E4%B8%AD%E6%96%87%E6%96%87%E4%BB%B6%E5%90%8D.jpg";
		try {
			System.out.println(URLDecoder.decode(result, "UTF-8"));
			System.out.println(URLDecoder.decode(inUrl, "UTF-8"));
			foo();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static void foo() throws MalformedURLException, URISyntaxException {
//		URL a = new URL("http://abc.com.cn/中文/example.jpg/");
		URI a = new URI("http://abc.com.cn/中文/example.jpg/");
		System.out.println(a.toASCIIString());
		
	}
}
