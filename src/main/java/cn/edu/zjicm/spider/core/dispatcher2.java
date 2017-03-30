package cn.edu.zjicm.spider.core;


import java.net.URL;
import java.util.ArrayList;
import java.util.HashSet;


public class dispatcher2 {
	
	private static ArrayList<URL> urls = new ArrayList<URL>();
	private static ArrayList<URL> visitedURLs = new ArrayList<URL>(); //�������ڴ����ݿ�
	private static ArrayList<URL> targetURLs = new ArrayList<URL>(); //ָ����Χ��URL�б�
	private static ArrayList<URL> unvisitedURLs = new ArrayList<URL>();  //����URL 
	BloomFliter filter=new  BloomFliter(); //ȫ��BloomFliter ������
	
	public dispatcher2(ArrayList<URL> urls) {    
		this.urls = urls; 
	}    
	
	public synchronized URL getURL(String gather)	//����gather��ţ��������ܵ���	
	{
		while(urls.isEmpty()){ 
			try{ 
				wait();
//				Thread.yield();			
				System.out.println("urls��,[Gather]"+gather+"---�̵߳ȴ�����������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������list�ա�");
			} catch (InterruptedException e) { 
				e.printStackTrace(); 
			} 	 
			
		}		
		this.notify(); //������������ã�Wakes up a single thread that is waiting on this object's monitor. 
		URL url = urls.get(0);//��ȡList���׸�URL��ַ
		System.out.println("**********************************************************��ץȡ��URL:"+urls.size());
		System.out.println("�̡߳�gather��"+gather+"��ȡURL:"+url);
//		visitedURLs.add(url);//���뵽�Ѿ����ʵ�vistedURL 
		
		filter.add(url.toString());
		visitedURLs.add(url);//���뵽�Ѿ����ʵ�vistedURL 
		System.out.println("**********************************************************�Ѿ�ץȡ��URL:"+visitedURLs.size());
		urls.remove(url); //�Ӵ�ץȡ��URL��ɾ��		
	    return url; 
	}

	public synchronized void insert(URL url)
	{
		if( !filter.contains(url.toString()))
			{
			urls.add(url);//ȥ�أ�ֱ����list�Աȣ����������Ҫ��������
	}
	}

	public synchronized void insert(ArrayList<URL> analyzedURL)
	{

		for(URL url : analyzedURL)
		{
			if( !filter.contains(url.toString()))
			urls.add(url);
		}
	}


}
