package cn.edu.zjicm.analyzer.util;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.htmlparser.NodeFilter;
import org.htmlparser.Parser;
import org.htmlparser.filters.TagNameFilter;
import org.htmlparser.util.NodeList;

public class GetURL {
	
	/**
	 * ��ȡ��������дһ��url����֮��ѭ������Դ�ļ������ֻ�ȡЧ���д���ߣ�����û����Ծ��Ե�url������ƣ����Ҳ�ñ�д��
	 * @param htmlDoc
	 * @return
	 */
	//URL����Ҫ���Ĺ�����ȥ��һЩ�������ӣ��޸�һЩ���·��������
	public ArrayList<String> urlDetector(String htmlDoc)
	{
		final String patternString = "<[a|A]\\s+href=([^>]*\\s*>)";   		
		Pattern pattern = Pattern.compile(patternString,Pattern.CASE_INSENSITIVE);   		
		ArrayList<String> allURLs = new ArrayList<String>();
		Matcher matcher = pattern.matcher(htmlDoc);
		String tempURL;
		//����ƥ�䵽��url�����磺<a href="http://bbs.life.sina.com.cn/" target="_blank">
		//Ϊ�ˣ���Ҫ������һ���Ĵ�����������url��ȡ���������Զ���ǰ����"֮��Ĳ��ֽ��м�¼�õ�url
		while(matcher.find())
		{
			tempURL = matcher.group();			
			tempURL = tempURL.substring(tempURL.indexOf("\"")+1);			
			if(!tempURL.contains("\""))
				continue;
			
			tempURL = tempURL.substring(0, tempURL.indexOf("\""));					
//				System.out.println("tempURL:"+tempURL);
			//��ʹ��֮ǰ�Ĵ����£������п��ܷ�������ģ����磬�����õ�����Ե�url
			//����������ַ����Ͳ���������url�ĳ�ʼ���������Ȱ��ⲿ��ʡ�Բ�����
			//֮�����дһ������host�ķ�������Щurl����
			if(tempURL.startsWith("http"))
			allURLs.add(tempURL);
			//System.out.println("���ػ�ȡ��URL");
		}
		return allURLs;	
		
	}
	/**
	 * getnewURL ����ʹ��httpPaeser
	 * �������µ�����A��ǩ
	 * @param htmlDoc
	 * @return
	 */
	public ArrayList<URL> urlDetector2(String htmlDoc)
	{
     	ArrayList<URL> allURLs = new ArrayList<URL>();
		/* 
		 * ����TagNameFilter�÷� 
		 */  
			try {  
				NodeFilter filter = new TagNameFilter("a");  
//				Parser parser = new Parser(htmlDoc);  
				Parser parser = new Parser();  
				parser.setURL("http://tianya.cn/");  
				parser.setEncoding(parser.getEncoding());  
				NodeList list = parser.extractAllNodesThatMatch(filter);  
				for (int i = 0; i < list.size(); i++) {  
					//System.out.println(list.elementAt(i).toHtml());
					String s1=list.elementAt(i).toHtml();
					ParserURL u2=new ParserURL();
					u2.parseUrl(s1);
					u2.parseUrlText(s1);
					
				}  
			} catch (Exception e) {  
				e.printStackTrace();  
			}  
	 
		return allURLs;	
		
	}

}
