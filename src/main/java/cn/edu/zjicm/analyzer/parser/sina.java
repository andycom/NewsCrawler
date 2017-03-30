package cn.edu.zjicm.analyzer.parser;

import org.htmlparser.*;
import org.htmlparser.filters.AndFilter;
import org.htmlparser.filters.HasAttributeFilter;
import org.htmlparser.filters.NodeClassFilter;
import org.htmlparser.filters.TagNameFilter;
import org.htmlparser.nodes.TagNode;
import org.htmlparser.tags.AppletTag;
import org.htmlparser.tags.InputTag;
import org.htmlparser.tags.MetaTag;
import org.htmlparser.tags.SelectTag;
import org.htmlparser.tags.TableTag;
import org.htmlparser.tags.TitleTag;
import org.htmlparser.util.NodeList;
import org.htmlparser.util.ParserException;

import cn.edu.zjicm.pipeline.sql2005;

public class sina {
	public static void sina_parser(String url) {

		if (url.contains("news") || url.contains("blog")) {

			if (url.contains("news"))
				sina_news(url);
			else if (url.contains("blog"))
				sina_blog(url);
		} else {
			System.out.println("");
		}
	}

	private static void sina_blog(String url) {
		// TODO Auto-generated method stub
		Parser parser = new Parser();
		try {
			int i;
			parser.setURL(url);
			parser.setEncoding(parser.getEncoding());

			String title = "title: ", time = "time: ", keywords = "keywords: ", arti = "arti: ",
					arti_title = "arti_title: ", writer = "writer: "; // ��������

			NodeFilter filter_title = new NodeClassFilter(TitleTag.class); // ����
			NodeList list = parser.extractAllNodesThatMatch(filter_title);

			for (i = 0; i < list.size(); i++)
				title += list.elementAt(i).toPlainTextString();
			parser.reset();

			NodeFilter filter_writer = new HasAttributeFilter("id", "blognamespan"); // ����
			list = parser.extractAllNodesThatMatch(filter_writer);
			for (i = 0; i < list.size(); i++)
				writer += list.elementAt(i).toPlainTextString();
			parser.reset();

			NodeFilter filter_time = new HasAttributeFilter("class", "time SG_txtc"); // ʱ��
			list = parser.extractAllNodesThatMatch(filter_time);

			for (i = 0; i < list.size(); i++)
				time += list.elementAt(i).toPlainTextString();
			parser.reset();

			NodeFilter filter_keywords = new NodeClassFilter(MetaTag.class); // keywords
			NodeFilter filter_keywords2 = new HasAttributeFilter("name", "keywords");
			NodeFilter filter_and = new AndFilter(filter_keywords, filter_keywords2);
			list = parser.extractAllNodesThatMatch(filter_and);
			for (i = 0; i < list.size(); i++)
				keywords += ((TagNode) list.elementAt(i)).getAttribute("content");
			parser.reset();

			NodeFilter filter_c_title = new HasAttributeFilter("class", "titName SG_txta"); // ���ı���
			list = parser.extractAllNodesThatMatch(filter_c_title);
			for (i = 0; i < list.size(); i++)
				arti_title += list.elementAt(i).toPlainTextString();
			parser.reset();

			NodeFilter filter_content = new HasAttributeFilter("class", "articalContent  "); // ����
			list = parser.extractAllNodesThatMatch(filter_content);

			for (i = 0; i < list.size(); i++)
				arti += list.elementAt(i).toPlainTextString();
			parser.reset();

			System.out.println(title);
			System.out.println(writer);
			System.out.println(time);
			System.out.println(keywords);
			System.out.println(arti_title);
			System.out.println(arti);
			String sql = "INSERT INTO PageInfo (PageTitle,PageKeyWords,PageContextTitle,PageContext,GraspTime,UpdateTime) VALUES ('"
					+ title + "','" + keywords + "','" + arti_title + "','" + arti + "','" + time + "',getdate()" + ")";
			sql2005 test = new sql2005();
			try {
				// test.insert(sql);
			} catch (Exception e) {
				System.out.println("");
			}

		} catch (ParserException e) {

			e.printStackTrace();
		}

	}

	private static void sina_news(String url) {
		Parser parser = new Parser();
		try {
			int i;
			parser.setURL(url);
			parser.setEncoding("gb2312");

			String title = "", time = "", keywords = " ", arti = "", arti_title = "", writer = ""; // ��������

			// NodeFilter filter_title=new TagNameFilter("title"); //����
			NodeFilter filter_title = new NodeClassFilter(TitleTag.class);
			NodeList list = parser.extractAllNodesThatMatch(filter_title);
			// System.out.print("title: ");
			for (i = 0; i < list.size(); i++)
				/*
				 * System.out.print(list.elementAt(i).toPlainTextString());
				 * System.out.println();
				 */
				title += list.elementAt(i).toPlainTextString();
			parser.reset();

			NodeFilter filter_writer = new HasAttributeFilter("id", "media_name"); // ����
			list = parser.extractAllNodesThatMatch(filter_writer);
			for (i = 0; i < list.size(); i++)
				writer += list.elementAt(i).toPlainTextString();
			parser.reset();

			NodeFilter filter_time = new HasAttributeFilter("id", "pub_date"); // ʱ��
			list = parser.extractAllNodesThatMatch(filter_time);
			// System.out.print("time: ");
			for (i = 0; i < list.size(); i++)
				/*
				 * System.out.print(list2.elementAt(i).toPlainTextString());
				 * System.out.println();
				 */
				time += list.elementAt(i).toPlainTextString();
			parser.reset();

			NodeFilter filter_keywords = new NodeClassFilter(MetaTag.class); // keywords
			NodeFilter filter_keywords2 = new HasAttributeFilter("name", "keywords");
			NodeFilter filter_and = new AndFilter(filter_keywords, filter_keywords2);
			list = parser.extractAllNodesThatMatch(filter_and);
			for (i = 0; i < list.size(); i++)
				// System.out.println("keywords: "+( (TagNode)
				// list.elementAt(i)).getAttribute("content"));
				keywords += ((TagNode) list.elementAt(i)).getAttribute("content");
			parser.reset();

			NodeFilter filter_c_title = new HasAttributeFilter("id", "artibodyTitle"); // ���ı���
			list = parser.extractAllNodesThatMatch(filter_c_title);
			// System.out.print("���ı���: ");
			for (i = 0; i < list.size(); i++)
				// System.out.print(list.elementAt(i).toPlainTextString());
				// System.out.println();
				arti_title += list.elementAt(i).toPlainTextString();
			parser.reset();

			NodeFilter filter_content = new HasAttributeFilter("id", "artibody"); // ����
			list = parser.extractAllNodesThatMatch(filter_content);
			// System.out.print("content: ");
			for (i = 0; i < list.size(); i++)
				/*
				 * System.out.print(list.elementAt(i).toPlainTextString());
				 * System.out.println();
				 */
				arti += list.elementAt(i).toPlainTextString();
			parser.reset();

			System.out.println(title);
			System.out.println(writer);
			System.out.println(time);
			System.out.println(keywords);
			System.out.println(arti_title);
			System.out.println(arti);
			String sql = "INSERT INTO PageInfo (PageTitle,PageKeyWords,PageContextTitle,PageContext,GraspTime,UpdateTime) VALUES ('"
					+ title + "','" + keywords + "','" + arti_title + "','" + arti + "','" + time + "',getdate()" + ")";
			sql2005 test = new sql2005();
			try {

			} catch (Exception e) {
				System.out.println("�������ݳ���");
			}
		} catch (ParserException e) {

			e.printStackTrace();
		}

	}
}
