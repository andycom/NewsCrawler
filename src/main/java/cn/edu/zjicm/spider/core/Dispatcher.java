package cn.edu.zjicm.spider.core;

import java.net.URL;
import java.util.ArrayList;
import java.util.HashSet;

public class Dispatcher {
	
	private static ArrayList<URL> urls = new ArrayList<URL>();
	private static ArrayList<URL> visitedURLs = new ArrayList<URL>();
	private static ArrayList<URL> unvisitedURLs = new ArrayList<URL>();
	
	public Dispatcher(ArrayList<URL> urls) {    
		this.urls = urls; 
	}    
	
	public synchronized URL getURL()		
	{
		//��ջ�����ݣ����ܳ�ջ
		while(urls.isEmpty()){ 
			try{ 
			   	wait(); // �ȴ�������д������ 
				System.out.println("urls��---�̵߳ȴ���������������");
			} catch (InterruptedException e) { 
				e.printStackTrace(); 
			} 
		}
		
		this.notify(); //������������ã�Wakes up a single thread that is waiting on this object's monitor. 
		URL url = urls.get(0);//��ȡList���׸�URL��ַ
		//System.out.println("��ȡURL:"+url);
		visitedURLs.add(url);//���뵽�Ѿ����ʵ�vistedURL 
		urls.remove(url); //�Ӵ�ץȡ��URL��ɾ��		
	    return url; 
	}

	public synchronized void insert(URL url)
	{
		if(!urls.contains(url) && !visitedURLs.contains(url))
			{//public boolean ds=urls.toString().extractorUrl();
			urls.add(url);//ȥ�أ�ֱ����list�Աȣ����������Ҫ��������
	}
	}

	public synchronized void insert(ArrayList<URL> analyzedURL)
	{
		for(URL url : analyzedURL)
		{
			if(!urls.contains(url) && !visitedURLs.contains(url))
			urls.add(url);
		}
	}

}
