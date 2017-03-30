package cn.edu.zjicm.spider.core;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Queue;
import java.util.concurrent.LinkedBlockingQueue;

import cn.edu.zjicm.data.berkeleyDB.BdbPersistentQueue;
import cn.edu.zjicm.spider.zjicmspider.zjicmspider;

public class dispatcherUrl {
	    static Queue<String> memoryQueue;
	    static Queue<String> persistentQueue;
	    BloomFliter filter=new  BloomFliter(); //ȫ��BloomFliter ������
	    static Queue<String> getList; //���Ա���
		private static ArrayList<URL> visitedURLs = new ArrayList<URL>(); //�������ڴ����ݿ�
	    
	    protected  void setUp(String DBPath) throws Exception {
	    	
	        memoryQueue=new LinkedBlockingQueue<String>();
	        String dbDir="BDB//"+DBPath;          //�������ݿ��·��,�����Ҫ����һ������ DBPath
	        File file=new File(dbDir);
	        if(!file.exists()||!file.isDirectory()){
	            file.mkdirs();
	        }
	        persistentQueue=new BdbPersistentQueue(dbDir,"pq",String.class);
	    }

	       dispatcherUrl(ArrayList<URL> urls,String DBPath) throws Exception {  
	    	//setUp("zjicm");//����ʱѭ��д���ڴ����ݿ�
			while(urls.isEmpty()){ 
				 for(int i=0;i<urls.size();i++){				 
					 fill(memoryQueue,urls.get(i).toString());
					 
				 }
			}
			 Iterator it = memoryQueue.iterator();
		        while(it.hasNext())
		        {
		            System.out.println(it.next());
		        }

			
		}
	    public synchronized URL getURL(String gather) throws Exception	//����gather��ţ��������ܵ���	
		{
	    	
			while(memoryQueue.size()==0){ 
				try{ 
					wait();		
					System.out.println("Queue��,[Gather]"+gather+"---�̵߳ȴ�����������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������list�ա�");
				} catch (InterruptedException e) { 
					e.printStackTrace(); 
				} 
			}
				this.notify(); //������������ã�Wakes up a single thread that is waiting on this object's monitor. 
//				String url = memoryQueue.poll();//��ȡList���׸�URL��ַ
//				URL url2= new URL(url); //����URL
				URL url3=new URL(memoryQueue.poll());
				zjicmspider.logger.info("**********************************************************��ץȡ��URL:"+memoryQueue.size());
				zjicmspider.logger.info("�̡߳�gather��"+gather+"��ȡURL:"+url3);
//				visitedURLs.add(url);//���뵽�Ѿ����ʵ�vistedURL 				
				filter.add(url3.toString());
				visitedURLs.add(url3);//���뵽�Ѿ����ʵ�vistedURL 
				zjicmspider.logger.info("**********************************************************�Ѿ�ץȡ��URL:"+visitedURLs.size());	
				return url3;	
			}	
	    public synchronized void insert(URL url)
		{
			if( !filter.contains(url.toString()))
				{
				memoryQueue.add(url.toString());
		}
		}

		public synchronized void insert(ArrayList<URL> analyzedURL)
		{

			for(URL url : analyzedURL)
			{
				if( !filter.contains(url.toString()))
					memoryQueue.add(url.toString());
			}
		}
	    protected void tearDown() throws Exception {   //���ݿ�رգ������һЩ����
//	        memoryQueue.clear();
//	        memoryQueue=null;
//	        persistentQueue.clear();
//	        persistentQueue=null;
	    }
	    
	    public static void fill(Queue<String> queue,String url){   //���ԣ�����һ��url	        
	            queue.add(url);	 
	            System.out.println(queue.size());
	    }
	    public String get(Queue<String> queue){           //���ԣ���ȡһ��url
	     String url3="werwr";
      url3=queue.peek();	 
      System.out.println("-----------------------��ȡ�����ݣ�"+queue.peek());
      return url3;
}
	    public void addurl(ArrayList<URL> urls,String DBPath) throws Exception{
//	    	 System.out.println("1.����url����");
//	    	 String url="http://www.baidu.com";
//	    	 long time=0;
//	         long start=System.nanoTime();
//	         fill(memoryQueue,url);	      
//	         time=System.nanoTime()-start;
//	         System.out.println("\t��� "+url+" �����ݺ�ʱ: "+(double)time+" ����");
//	         String url2=get(memoryQueue);
		       //  fill(memoryQueue,url);
//		         time=System.nanoTime()-start;
//		         System.out.println("\t��ȡ "+url2+" �����ݺ�ʱ: "+(double)time/1000000+" ����");
	    	
	    	setUp("zjicm");//����ʱѭ��д���ڴ����ݿ�
			while(urls.isEmpty()){ 
				 for(int i=0;i<urls.size();i++){				 
					 fill(memoryQueue,urls.get(i).toString());
					 
				 }
			}
//			   
	    }
	   public void geturl(){
		     System.out.println("2.����url��ȡ");
		     long time=0;
	         long start=System.nanoTime();
	         String url2=get(memoryQueue);
	         time=System.nanoTime()-start;
	         System.out.println(memoryQueue.size()+"\t��ȡ "+url2+" �����ݺ�ʱ: "+(double)time+" ����");
		   
	   }
		
		/*public static void main(String []args) throws Exception{
			//String DBPath="dsjs";
			//setUp(DBPath); //��ʼ���ڴ����ݿ⣬�����������Ҫ��������������һ�������ݿ��·����һ�������ݿ�����ƣ�֮�����������Ҫ�����������������
			ArrayList<URL> seedUrls = new ArrayList<URL>();// ����վ��
			ArrayList<String> seedUrls2 = new ArrayList<String>();// ����վ��
			try {

//				 �˲�����ƴ����ݿ��ж�ȡ�������õ�վ���б�
				 seedUrls.add(new URL("http://focus.tianya.cn/"));
				 seedUrls.add(new URL("http://www.zjicm.edu.cn"));
				 seedUrls.add(new URL("http://bbs.163.com"));
				 seedUrls.add(new URL("http://bbs.voc.com.cn"));
				 seedUrls.add(new URL("http://club.china.com"));
				 seedUrls.add(new URL("http://bbs.ifeng.com"));
				 seedUrls.add(new URL("http://news.sina.com.cn/"));
				 seedUrls.add(new URL("http://blog.sina.com.cn/"));
				 seedUrls.add(new URL("http://culb.dayoo.com/"));
				 seedUrls.add(new URL("http://bbs.qianlong.com/"));
				 seedUrls.add(new URL("http://culb.tom.com/"));
				 seedUrls.add(new URL("http://bbs.tiexue.net/"));
				 seedUrls.add(new URL("http://bbs.qq.com/"));
				 seedUrls.add(new URL("http://bbs.xici.com/"));
				 seedUrls.add(new URL("http://tt.mop.com/"));
				seedUrls.add(new URL("http://www.tianya.cn"));
			} catch (MalformedURLException e) {
				e.printStackTrace();
			}
			
			 for(int i=0;i<seedUrls.size();i++){				 
				 fill(memoryQueue,seedUrls.get(i).toString());	  //ѭ��д���ڴ����ݿ�
			 }
			 
			/*
			 * ��������
			 */
				
		/*	 @SuppressWarnings("rawtypes")
			    Iterator it = memoryQueue.iterator();
		        while(it.hasNext())
		        {
		            System.out.println(it.next());
		        }


				 for(int i=0;i<memoryQueue.size();i++){				 
					 seedUrls2.add(memoryQueue.poll());
				 }
				 
				 for(int i=0;i<seedUrls2.size();i++){				 
					 System.out.println("��ȡ��url��"+seedUrls2.get(i).toString());	  //ѭ��д���ڴ����ݿ�
				 }
			
		}*/

}
