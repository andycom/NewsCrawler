package cn.edu.zjicm.spider.core;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import cn.edu.zjicm.analyzer.util.GetURL;
import cn.edu.zjicm.spider.core.Dispatcher;
import cn.edu.zjicm.spider.zjicmspider.zjicmspider;
import cn.edu.zjicm.util.rangeHost;
import cn.edu.zjicm.util.urls;

public class Gather implements Runnable {
	Logger logger = LoggerFactory.getLogger("");

	private dispatcher4 disp;
	private String ID;
	private Client client = new Client();
	private GetURL UrlAnalyzer = new GetURL();
	private File file;
	public rangeHost range = new rangeHost();
	private BufferedWriter bfWriter;
	public ArrayList<URL> rangeUrls;
	protected static int signal = 2;

	public Gather(String ID, dispatcher4 disp3, ArrayList<URL> rangeurls) // �����߳�ID
	{
		this.ID = ID;
		this.disp = disp3;
		// zjicmspider.logger.info("�����߳�[Gather] in running: " + ID);
		this.rangeUrls = rangeurls;

	}

	public static void getSignal(int s) {
		signal = s;
	}

	// ע�⣬��û��ܹ�̽�����ӽ�����ʱ�䣬�Ӷ��ѳ���ʱ��̫��������ֱ��kill��
	// ��ֹ��һ��url��ͣ�����õ�����
	@SuppressWarnings("static-access")
	public void run() {
		// �˴������ź�������

		int counter = 0;
		String htmlDoc = "";
		while (true) {
			counter++;
			if (signal == 0) {
				break;// ����ֹͣ
			}
			urls url = null;
			try {
				url = disp.getURL(ID);
				System.out.println("��ȡ��url" + url.getoriUrl());
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			try {
				System.out.println("��ȡurl����Ϣ" + url.toString());// ������ʾurl
				htmlDoc = client.HttpClient(url.getoriUrl().toString());
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			logger.info("Gather:" + ID + "��" + counter + "��ѭ���� get url: " + url.toString()); // ������ʾ����
			if (htmlDoc.length() != 0) {
				ArrayList<String> newURL = UrlAnalyzer.urlDetector(htmlDoc); // �˷���Ϊ����ƥ�����
				if (newURL.size() != 0)
					logger.info("Gather" + ID + "---�»�ȡ��newURL�Ĵ�С" + newURL.size()); // ������ʾ����
				// �˴�����һ��ɸѡ���̣����Ʋ����url,ͬʱ�������ӵ����
				try {
					newURL = range.range2(newURL, url.getoriUrl(), url.getLayer());
				} catch (MalformedURLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				logger.info("Gather" + ID + "-----ɸѡ֮��newURL�Ĵ�С" + newURL.size()); // ������ʾ����
				// �˴�����һ��ɸѡ���̣����Ʋ����url
				disp.insert1(newURL);
			}

		}

	}

}