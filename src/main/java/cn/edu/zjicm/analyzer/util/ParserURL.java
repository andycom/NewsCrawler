package cn.edu.zjicm.analyzer.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ParserURL {
	/**
	 * ���ߣ�winglive ʱ�䣺2013.08.01 ˵������Ҫ����httpParser ��������A��ǩ��ȡ�����ӵ�ַ��ê�ı�
	 */
	public static final String patternString1 = "http://([^>]*\\s*)";
	public static final String patternString2 = "[^>]*>([^<]*)</a>";
	// http://([\w-]+\.)+[\w-]+(/[\w- ./?%&=]*)?
	public static Pattern pattern1 = Pattern.compile(patternString1,
			Pattern.DOTALL);
	public static Pattern pattern2 = Pattern.compile(patternString2,
			Pattern.DOTALL);

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
				System.out.println("��ӡ���������" + result);

			}

		}
		return result;

	}
/**
 * �˷�������ê�ı�������Щ���ӵĴ�Сд���⣬�����Ҫ֮ǰһ�δ����Сдת��
 * ������֮ǰ����Ҫ�����url���з�Χ�޶���
 * @param var
 * @return
 */
	public String parseUrlText(String var) {
		Matcher matcher = null;
		String resultText = null;

		matcher = pattern2.matcher(var);
		while (matcher != null && matcher.find()) {
			int a = matcher.groupCount();
			while ((a--) > 0) {
				resultText = matcher.group(a);
				 if(!resultText.contains(">"))
				 continue;
				 resultText=resultText. substring(
				 resultText.indexOf("\">")+1);
				//System.out.println("��ӡ���������org.zjicm.analyzer.util+ParserURL" + resultText);

			}

		}
		return resultText;

	}

}
