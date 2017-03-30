
package cn.edu.zjicm.crawler;

import java.net.URL;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;
import org.omg.CORBA.PUBLIC_MEMBER;

import com.wingspider.Initialization.readSeedURL;
import com.wingspider.database.sql2005;

import cn.edu.zjicm.spider.core.Gather;
import cn.edu.zjicm.spider.core.zjicmSpider;
import cn.edu.zjicm.spider.zjicmspider.zjicmspider;

/**
 * @author winglive E-mail: fiveme@126.com
 * @version ����ʱ�䣺2014-1-11 ����1:24:34 ��˵��:������ڣ���������
 */
public class wingspider {
	public static int signal;
	public static Logger logger; // log4j��־
	public static ArrayList<URL> seed;
	public static control sc;

	public static void main(String[] args) throws Exception {
		// ������־
		PropertyConfigurator.configure("src/log4j.properties");
		zjicmspider.logger = Logger.getLogger(zjicmspider.class);
		// ��ȡ����������Ϣ
		String xmlFile = "xml/crawler.xml";
		spiderconfig wingspider = new spiderconfig();// ����������Ϣ����
		parserXML spidgetconfig = new parserXML();
		wingspider = spidgetconfig.readXMLFile(xmlFile);
		System.out.println("��������������Ϣ��ȡ������������������");
		System.out.println("spiderName: " + wingspider.getSpiderName() + "\t spiderType: " + wingspider.getSpiderType()
				+ "\t ManageSystem_DB_IP: " + wingspider.getSpiderIp() + "\t localuserName: "
				+ wingspider.getLocalUsername() + "\t password: " + wingspider.getlocalUserpassWord());
		// �˴���Ϣ��Ҫ���ܣ��������ݿ���û�����������Լ����һ�׼��ܻ���

		// ManageDB��ʼ��
		final sql2005 manageConnection = new sql2005();// �½�һ��sql2005����
		final spiderLoop loop = new spiderLoop(); // �½�һ����ѯ������
		// signal = loop.ManageDBConnection(manageConnection); // ������ѯ����

		// star the ask loop

		// getSeedsURL
		readSeedURL rsUrl = new readSeedURL();
		// /ArrayList<URL> seed = new ArrayList<>();
		String sql = "select SiteEnterURL from GatherWebsite";
		seed = rsUrl.getSeedlist(wingspider.getSpiderIp(), wingspider.getLocalUsername(),
				wingspider.getlocalUserpassWord(), sql);// �������

		// zjicmSpider spider = new zjicmSpider(seed,
		// wingspider.getSpiderName(),wingspider.getSpiderId()); //
		// ÿ��ץȡ�����ȡ��ͬ��urls
		zjicmspider.logger.info("�������ƣ�" + wingspider.getSpiderName() + "�����б�" + seed);
		// control sc=new control();
		// sc.start(seed, wingspider.getSpiderName(),
		// wingspider.getSpiderType());
		final sql2005 getSignal = new sql2005();// �½�һ��sql2005����
		Timer timer = new java.util.Timer(false);
		TimerTask task = new java.util.TimerTask() {
			private int k = 0, l = 0, s = 0, v = 0;

			// String sql = "1"; // �Ժ�������ļ���ȡ���ݿ��ַ��Ϣ�����ţ���������
			// String ip = "127.0.0.1";
			@Override
			public void run() {
				System.out.println("��Ҫ��ʱִ�е�����..." + k);
				try // ������ʾ�õ�try �����Ϊʲô������
				{
					l = getSignal.insert("1", "127.0.0.1");
				} catch (Exception e) {
					System.out.println("�������ó���");
				}
				k++;
				if (l > 1 && s < 1) {
					System.out.println("�������棡��");
					s = 2;
					sc = new control();
					try {
						sc.start(l, seed, "58", 5); // break��ͨ
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					// ���������״λ�ȡ�ź� �������棬֮�����źţ����ٵ�����������
				} else if (l < 1 && s > 1) {
					System.out.println("�ź����ı�ֹͣ����");
					s = 0;
					try {
						sc.stop();
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					// ֹͣ�������źţ��ı�һֱֹͣ���棬�����Ҫ��������б�����״̬���źţ�����Ҫһֱֹͣ������������

				} else {
					System.out.println("�ֽ׶ο����ź�" + l + "����״̬�ź�" + s);
				}

			}
		};
		java.util.Date time = new java.util.Date();
		long delay = 3000; // 3��ѯ��һ��
		// ������ʱ��������ִ��Ҽ�Σ�Ȼ��ÿ������ִ��Ҽ��
		timer.schedule(task, time, delay);
		System.out.println("ÿ��3���ȡ�ź���" + signal);
	}

}
