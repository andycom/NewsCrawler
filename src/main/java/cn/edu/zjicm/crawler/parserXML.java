package cn.edu.zjicm.crawler;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

/**
 * @author winglive E-mail: fiveme@126.com
 * @version ����ʱ�䣺2014-1-11 ����1:31:32 ��˵�� ���������������Ϣ
 */
public class parserXML {
	// ����XML������Ϣ������XML�ļ���·��
	protected spiderconfig readXMLFile(String file) throws Exception {
		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
		DocumentBuilder builder = dbf.newDocumentBuilder();
		Document doc = builder.parse(file); // ��ȡ��xml�ļ�

		// ���濪ʼ��ȡ
		Element root = doc.getDocumentElement(); // ��ȡ��Ԫ��
		// System.out.println("��Ԫ��Ϊ:" + root.getTagName()); //
		// ��Ԫ�ص���Ϣ��ʾ����
		NodeList spiders = root.getElementsByTagName("spider");
		// һ��ȡ��ÿһ��ѧ��Ԫ��
		Element spidernode = (Element) spiders.item(0);
		// ����һ������������Ϣ����
		spiderconfig spid = new spiderconfig(); // �½�һ��spiderconfig����

		NodeList names = spidernode.getElementsByTagName("name");
		Element e = (Element) names.item(0);
		Node t = e.getFirstChild();
		spid.setSpiderName(t.getNodeValue());

		NodeList id = spidernode.getElementsByTagName("id");
		e = (Element) id.item(0);
		t = e.getFirstChild();
		spid.setSpiderId(Integer.parseInt(t.getNodeValue()));

		NodeList ip = spidernode.getElementsByTagName("Manageip");
		e = (Element) ip.item(0);
		t = e.getFirstChild();
		spid.setSpiderIp(t.getNodeValue());

		NodeList type = spidernode.getElementsByTagName("type");
		e = (Element) type.item(0);
		t = e.getFirstChild();
		spid.setSpiderType(Integer.parseInt(t.getNodeValue()));

		NodeList user = spidernode.getElementsByTagName("localDBuserName");
		e = (Element) user.item(0);
		t = e.getFirstChild();
		spid.setLocalUsername(t.getNodeValue());

		NodeList passWord = spidernode.getElementsByTagName("localDBpassWord");
		e = (Element) passWord.item(0);
		t = e.getFirstChild();
		spid.setlocalUserpassWord(t.getNodeValue());

		return spid; // ����һ������������Ϣ����
	}

}
