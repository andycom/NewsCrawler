package cn.edu.zjicm.spider.core;

import java.net.URL;
import java.text.BreakIterator;
import java.util.ArrayList;

/**
 * �������ɣ�����ren
 * 
 * @author zjicm
 * 
 */

public class zjicmSpider implements Runnable {

	private ArrayList<URL> urls; // ��ʼurls�б�
	private int gatherNum = 10; // �����߳�
	public boolean htmlSave = false; // ��ȡ�Ƿ�������ҳ
	public String Task = "2118"; // ��ȡ����������Ϣ,�������ƣ������ڴ����ݿ��·��
	public int deep = -1; // ��ȡ��ץȡ����ҳ��ȣ�-1������������ơ�
	public int s;

	public zjicmSpider() {

	}

	public zjicmSpider(int s, ArrayList<URL> urls, String taskName, int deepth) {
		this.urls = urls;
		this.deep = deepth;
		this.s = s;
	}

	public void run() {

		System.out.println("zjicmSpider�����ﱨѭ��KKKKKK");
		dispatcher4 disp = null;
		try {
			disp = new dispatcher4(urls, Task, deep);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} // ���̹߳���һ��������
		for (int i = 0; i < gatherNum; i++) {
			Thread gather = new Thread(new Gather(String.valueOf(i), disp, urls));
			gather.start();
		} // TODO Auto-generated method stub

	}

	/**
	 * 
	 */

}
