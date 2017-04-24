package cn.edu.zjicm.spider.core;

import java.io.File;
import java.net.URL;
import java.util.ArrayList;

import java.util.Iterator;
import java.util.Queue;
import java.util.concurrent.LinkedBlockingQueue;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import cn.edu.zjicm.data.berkeleyDB.BdbPersistentQueue;
import cn.edu.zjicm.spider.zjicmspider.zjicmspider;

public class dispatcher3 {
	Logger logger = LoggerFactory.getLogger("");

	static Queue<String> memoryQueue;
	static Queue<String> persistentQueue;
	BloomFliter filter = new BloomFliter(); // ȫ��BloomFliter ������

	private static ArrayList<URL> visitedURLs = new ArrayList<URL>(); // �������ڴ����ݿ�
	public static ArrayList<URL> rangeURLs = new ArrayList<URL>(); // �������ڴ����ݿ�
	public static ArrayList<URL> IsrangeURLs = new ArrayList<URL>(); // �������ڴ����ݿ�

	protected static void setUp(String DBPath) throws Exception {

		memoryQueue = new LinkedBlockingQueue<String>();
		String dbDir = "BDB//2//" + DBPath; // �������ݿ��·��,�����Ҫ����һ������
											// DBPath
		File file = new File(dbDir);
		if (!file.exists() || !file.isDirectory()) {
			file.mkdirs();
		}
		persistentQueue = new BdbPersistentQueue(dbDir, "pq", String.class);
	}

	dispatcher3(ArrayList<URL> urls, String taskName, int deepth) throws Exception {
		setUp(taskName);
		for (URL url : urls) {
			fill(memoryQueue, url.toString());
			rangeURLs.add(url); // �����б��Ƿ�Χ�б�
		}
		System.out.println("��ʾץȡ���****" + deepth);

	}

	public synchronized URL getURL(String gather) throws Exception // ����gather��ţ��������ܵ���
	{

		while (memoryQueue.size() == 0) {
			try {
				wait();
				System.out.println("Queue��,[Gather]" + gather
						+ "---�̵߳ȴ���������������������������������������������������������������������������������������������������list�ա�");

			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		this.notify(); // ������������ã�Wakes up a single thread that is waiting
						// on this object's monitor.
		URL url3 = new URL(memoryQueue.poll());
		// String urltemp=memoryQueue.poll().toString();
		// String urldeep=urltemp.substring(0, 1);
		// int URLdeep=Integer.parseInt(urldeep);
		// zjicmspider.logger.info("----------------------------------****-朽����:"+urldeep);
		// URL url3=new URL(memoryQueue.poll().toString().substring(1));
		logger.info("--��ץȡ��URL:" + memoryQueue.size());
		logger.info("�̡߳�gather��" + gather + "��ȡURL:" + url3);
		filter.add(url3.toString());
		visitedURLs.add(url3);// ���뵽�Ѿ����ʵ�vistedURL
		logger.info("--------------------------------------�Ѿ�ץȡ��URL:" + visitedURLs.size());
		return url3;
		// return URLdeep;
	}

	public synchronized void insert(URL url)

	{
		// zjicmspider.logger.info("--------------------------------------�ж�1��URL:"+url);
		if (!filter.contains(url.toString())) // &&!rangeURLs.contains(url)
		{
			memoryQueue.add(url.toString());
		} else {
			logger.info("-------------fliter�ж��ظ���URL:" + url);
		}
		// zjicmspider.logger.info("--------------------------------------�ж�1��URL�Ƿ�ͨ��:"+memoryQueue.peek()+"���д�С��"+memoryQueue.size());
	}

	public synchronized void insert(ArrayList<URL> analyzedURL) {

		for (URL url : analyzedURL) {
			// zjicmspider.logger.info("---�ж�2��URL:"+url+"=====����:"+url.getHost());
			if (!filter.contains(url.toString()))// &&!rangeURLs.contains(url)
			{
				memoryQueue.add(url.toString());
			} else {
				logger.info("-------------fliter�ж��ظ���URL:" + url);
			}
			// url.getHost()
			// zjicmspider.logger.info("---�ж�2��URL�Ƿ�ͨ��:"+memoryQueue.peek()+"=======���д�С"+memoryQueue.size());

		}
	}

	public ArrayList<URL> range(ArrayList<URL> analyzedURL) // �ж������Ƿ��ڷ�Χ֮��
	{

		for (URL url : analyzedURL) {
			// zjicmspider.logger.info("---�ж�3��URL:"+url+"=====����:"+url.getHost());
			if (rangeURLs.contains(url))// &&!rangeURLs.contains(url)
				IsrangeURLs.add(url);
			// zjicmspider.logger.info("---�ж�3��ͨ����URL����:"+IsrangeURLs.size()+"=======���д�С"+memoryQueue.size());

		}
		return IsrangeURLs;
	}

	protected void tearDown() throws Exception { // ���ݿ�رգ������һЩ����
		memoryQueue.clear();
		memoryQueue = null;
		persistentQueue.clear();
		persistentQueue = null;
	}

	public static void fill(Queue<String> queue, String url) { // ���ԣ�����һ��url
		queue.add(url);
	}

}
