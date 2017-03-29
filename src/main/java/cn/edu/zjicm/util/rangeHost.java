package cn.edu.zjicm.util;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

public class rangeHost {

	public static ArrayList<URL> IsrangeURLs = new ArrayList<URL>();

	public static ArrayList<URL> range(ArrayList<URL> analyzedURL, ArrayList<URL> rangeURLs) // �ж������Ƿ��ڷ�Χ֮��
	{

		for (URL url : analyzedURL) {
			if (rangeURLs.contains(url))// &&!rangeURLs.contains(url)
				IsrangeURLs.add(url);
		}
		return IsrangeURLs;
	}

	/**
	 * 
	 * @throws MalformedURLException
	 * 
	 */
	public static ArrayList<String> range2(ArrayList<String> analyzedURL, URL rangeURL, int deep)
			throws MalformedURLException {
		ArrayList<String> IsrangeURLs2 = new ArrayList<String>();
		deep = deep + 1;
		for (String url : analyzedURL) {
			// System.out.println("rangeHost--------the Page Range URL is:
			// "+rangeURL.getHost());
			// System.out.println("rangeHost--------fliter URL
			// is"+url.toString());
			// System.out.println("rangeHost--------fliter URL Host
			// is"+url.getHost());
			if (url.toString().indexOf(rangeURL.getHost().toString()) != -1) {
				// System.out.println(" URL is in !!!");
				String tem = Integer.toString(deep) + url;
				// =Integer.toString(deep)+tem;
				// URL url2=new URL(tem);
				IsrangeURLs2.add(tem);
				// System.out.println("URL information"+tem);
			} else {

				// System.out.println("URL is Droped!");
			}
		}
		return IsrangeURLs2;

	}

}
