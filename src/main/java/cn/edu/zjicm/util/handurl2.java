package cn.edu.zjicm.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 
 * @author Xuexian Wu
 *
 */
public class handurl2 {
	/**
	 * 
	 */
	// public static final String patternString1="http://([^>]*\\s*)";
	public static final String patternString1 = "http://([^>]*\\s*)";
	// http://([\w-]+\.)+[\w-]+(/[\w- ./?%&=]*)?
	public static Pattern pattern1 = Pattern.compile(patternString1, Pattern.DOTALL);

	public String parseUrl(String var) {
		Matcher matcher = null;
		String result = null;

		matcher = pattern1.matcher(var);
		while (matcher != null && matcher.find()) {
			int a = matcher.groupCount();
			while ((a--) > 0) {
				result = matcher.group(a);
				if (!result.contains("\""))
					continue;
				result = result.substring(0, result.indexOf("\""));

			}

		}
		return result;

	}

}
