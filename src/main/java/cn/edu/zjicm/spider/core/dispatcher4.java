package cn.edu.zjicm.spider.core;


import java.io.File;
import java.net.URL;
import java.util.ArrayList;

import java.util.Iterator;
import java.util.Queue;
import java.util.concurrent.LinkedBlockingQueue;

import org.zjicm.spider.util.urls;

import cn.edu.zjicm.data.berkeleyDB.BdbPersistentQueue;
import cn.edu.zjicm.spider.zjicmspider.zjicmspider;


public class dispatcher4 {
	
	    static Queue<String> memoryQueue;
	    static Queue<String> memoryQueue2;
	    static Queue<String> persistentQueue;
	    BloomFliter filter=new  BloomFliter();                             //ȫ��BloomFliter ������
	 
		private static ArrayList<URL> visitedURLs = new ArrayList<URL>();   //�������ڴ����ݿ� ����ȥ��
		public static ArrayList<URL> rangeURLs = new ArrayList<URL>();     //��ʱList �������ڴ����ݿ�
		public static ArrayList<URL> IsrangeURLs = new ArrayList<URL>();   //��ʱlist �������ڴ����ݿ�
	    protected static  void setUp(String DBPath) throws Exception {
	    	
	        memoryQueue=new LinkedBlockingQueue<String>();
	        memoryQueue2=new LinkedBlockingQueue<String>();
	        String dbDir="BDB//"+DBPath;          //�������ݿ��·��,�����Ҫ����һ������ DBPath
	        File file=new File(dbDir);
	        if(!file.exists()||!file.isDirectory()){
	            file.mkdirs();
	        }
	        persistentQueue=new BdbPersistentQueue(dbDir,"pq",String.class);
	        //persistentQueue2=new BdbPersistentQueue(dbDir,"pq",String.class);
	        
	    }

	       dispatcher4(ArrayList<URL> urls,String taskName,int deepth) throws Exception {  
	             setUp(taskName); 	
				 for(URL url : urls){				 
					 fill(memoryQueue,"0"+url.toString());
					 rangeURLs.add(url);        //�����б��Ƿ�Χ�б�
				 }	
				  System.out.println("��ʾץȡ���****"+deepth);
			 
		}
	    public synchronized  urls getURL(String gather) throws Exception	//����gather��ţ��������ܵ���	
		{
	    	
			while(memoryQueue.size()==0){ 
				try{ 
						wait();
						System.out.println("Queue��,[Gather]"+gather+"---�̵߳ȴ���������������������������������������������������������������������������������������������������list�ա�");
					
				} catch (InterruptedException e) { 
					e.printStackTrace(); 
				} 
			}
				this.notify();     //������������ã�Wakes up a single thread that is waiting on this object's monitor. 
				 // URL url3=new URL(memoryQueue.poll());
				 urls conurl=new urls(); //�������ݶ���
				 String urltemp=memoryQueue.poll().toString();
				 String urldeep=urltemp.substring(0, 1);
				 int URLdeep=Integer.parseInt(urldeep);
				 zjicmspider.logger.info("----------------------------------****-朽����:"+urldeep);	
				 URL url3=new URL(urltemp.substring(1));
				 conurl.setLayer(URLdeep); //д���������
				 conurl.setoriUrl(url3);  //д��URL urls����Ԫ�ؿ�����չ
				 zjicmspider.logger.info("--��ץȡ��URL:"+memoryQueue.size());
				 zjicmspider.logger.info("�̡߳�gather��"+gather+"��ȡURL:"+url3);		
				 filter.add(url3.toString());
				 visitedURLs.add(url3);//���뵽�Ѿ����ʵ�vistedURL 
				 zjicmspider.logger.info("--------------------------------------�Ѿ�ץȡ��URL:"+visitedURLs.size());	
				 return conurl;
				 //return URLdeep;
			}	
	    /**
	     * ���߳̽�������Ӵ˴���ȡ�������Ķ���
	     * @param newURL
	     */
	    
	    public synchronized  String  analyzergetURL(String analyzer) throws Exception	//����gather��ţ��������ܵ���	
	  		{
	  	    	
	  			while(memoryQueue2.size()==0){ 
	  				try{ 
	  						wait();
	  						System.out.println("Queue��,[analyzer]"+analyzer+"---�̵߳ȴ�++++++++++++++++++list�ա�");
	  					
	  				} catch (InterruptedException e) { 
	  					e.printStackTrace(); 
	  				} 
	  			}
	  				this.notify();     //������������ã�Wakes up a single thread that is waiting on this object's monitor. 
	  				 // URL url3=new URL(memoryQueue.poll());
//	  				 urls conurl=new urls(); //�������ݶ���
	  				 String urltemp=memoryQueue2.poll().toString();
	  				 String urldeep=urltemp.substring(0, 1);
	  				 int URLdeep=Integer.parseInt(urldeep);
//	  				 zjicmspider.logger.info("----------------------------------****-朽����:"+urldeep);	
	  				 URL url3=new URL(urltemp.substring(1));
//	  				 conurl.setLayer(URLdeep); //д���������
//	  				 conurl.setoriUrl(url3);  //д��URL urls����Ԫ�ؿ�����չ
//	  				 zjicmspider.logger.info("--��ץȡ��URL:"+memoryQueue.size());
	  				 zjicmspider.logger.info("�̡߳�analyzer��"+analyzer+"��ȡURL:"+url3);		
	  				 filter.add(url3.toString());
	  				 visitedURLs.add(url3);//���뵽�Ѿ����ʵ�vistedURL 
	  				 zjicmspider.logger.info("--------------------------------------�Ѿ�ץȡ��URL:"+visitedURLs.size());	
//	  				 return conurl;
	  				 return urltemp.substring(1);
	  				 //return URLdeep;
	  			}	
	    public synchronized  void insert(String newURL)
	    
		{
	    	//zjicmspider.logger.info("--------------------------------------�ж�1��URL:"+url);
			if( !filter.contains(newURL))		//&&!rangeURLs.contains(url)		
				{memoryQueue.add(newURL);}
			else{
				zjicmspider.logger.info("-------------fliter�ж��ظ���URL:"+newURL);
			}
			//zjicmspider.logger.info("--------------------------------------�ж�1��URL�Ƿ�ͨ��:"+memoryQueue.peek()+"���д�С��"+memoryQueue.size());
		}

		public synchronized void insert1(ArrayList<String> analyzedURL)
		{

			for(String url : analyzedURL)
			{
//				zjicmspider.logger.info("---�ж�2��URL:"+url+"=====����:"+url.getHost());
				if( !filter.contains(url.toString()))//&&!rangeURLs.contains(url)
					{  memoryQueue.add(url.toString());
					   memoryQueue2.add(url.toString());}
				else{
					zjicmspider.logger.info("-------------fliter�ж��ظ���URL:"+url);
				}
				//url.getHost()
				zjicmspider.logger.info("---�ж�2��URL�Ƿ�ͨ��:"+memoryQueue.peek()+"=======���д�С"+memoryQueue.size());
				zjicmspider.logger.info("-------------�ȴ��������еĴ�С"+memoryQueue2.size());
			}
		}
		public ArrayList<URL> range(ArrayList<URL> analyzedURL)        //�ж������Ƿ��ڷ�Χ֮��**********
		{

			for(URL url : analyzedURL)
			{
				zjicmspider.logger.info("---�ж�3��URL:"+url+"=====����:"+url.getHost());
				if(rangeURLs.contains(url))//&&!rangeURLs.contains(url)
				  IsrangeURLs.add(url);
				zjicmspider.logger.info("---�ж�3��ͨ����URL����:"+IsrangeURLs.size()+"=======���д�С"+memoryQueue.size());
				
			}
			return IsrangeURLs;
		}
	    protected void tearDown() throws Exception {   //���ݿ�رգ������һЩ����
	        memoryQueue.clear();
	        memoryQueue=null;
	        persistentQueue.clear();
	        persistentQueue=null;
	    }
	    
	    public static void fill(Queue<String> queue,String url){   //���ԣ�����һ��url	        
	            queue.add(url);	 	         
	    }


	
		
}
