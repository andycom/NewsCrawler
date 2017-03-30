package cn.edu.zjicm.spider.core;

import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.HttpClient;    //httpclient
import org.apache.http.client.methods.HttpGet; //httpclient
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.apache.http.params.HttpProtocolParams;
import org.apache.http.util.EntityUtils;

import cn.edu.zjicm.spider.zjicmspider.zjicmspider;

import org.apache.http.client.params.HttpClientParams; //httpclient
import org.apache.http.impl.client.DefaultHttpClient; //httpclient

public class Client {
	private static String userAgent = "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.2; SV1; .NET CLR 1.1.4322; InfoPath.1; .NET CLR 2.0.50727)";

	public String HttpClient(String url) {
		
//		zjicmspider.logger.info("httpclient���̵߳���");
		
		String doc="";
		String charset="";
		// ���� HttpParams ���������� HTTP ��������һ���ֲ��Ǳ���ģ�
		HttpParams params = new BasicHttpParams();

		// �������ӳ�ʱ�� Socket ��ʱ���Լ� Socket �����С
		HttpConnectionParams.setConnectionTimeout(params, 20 * 1000);
		HttpConnectionParams.setSoTimeout(params, 20 * 1000);
		HttpConnectionParams.setSocketBufferSize(params, 8192);

		// �����ض���ȱʡΪ true
		HttpClientParams.setRedirecting(params, true);

		// ���� user agent
		HttpProtocolParams.setUserAgent(params, userAgent);

		// ����һ�� HttpClient ʵ��
		HttpClient httpClient = new DefaultHttpClient(params);
		try {
			// ���� HttpGet �������÷������Զ����� URL ��ַ���ض���
			HttpGet httpGet = new HttpGet(url);
			HttpResponse response = httpClient.execute(httpGet);
			// zjicmspider.logger.info(" ��ȡ����״̬��"+response.getStatusLine().getStatusCode());
			if (response.getStatusLine().getStatusCode() != HttpStatus.SC_OK) {
				// ��������������ڸ�������������ǰ�����ж�
				httpGet.abort();				
				System.out.println("httpClient ***************************************************************�������ִ���1����");				
			}
			// ��ȡ������Ϣ
			Header[] headers = response.getAllHeaders();
			
			for (int i = 0; i < headers.length; i++) {
                System.out.println(headers[i]);
            }
			HttpEntity entity = response.getEntity();
			charset=EntityUtils.getContentCharSet(entity);
			if(charset==null)
			{
				//zjicmspider.logger.info("httpclient----------------------ҳ��ͷ����Ϣ���ޱ�����Ϣ��"); //��ȡͷ����Ϣ�е�charset�����û�з���null
			}
			else{
			// zjicmspider.logger.info("httpclient----------------------ҳ��ͷ����Ϣ[charset]��"+charset); //��ȡͷ����Ϣ�е�charset�����û�з���null
			}
			//Header header = response.getFirstHeader("Content-Type");
			//System.out.println(header);
		  /* if (entity != null) {
				System.out.println(EntityUtils.toString(entity, "utf-8"));
				EntityUtils.consume(entity);
			}	*/		
			if (entity != null) {
				doc=EntityUtils.toString(entity, "utf-8"); //����ʶ����
				EntityUtils.consume(entity);
			}	
			//return doc;
		} catch (Exception ee) {
			System.out.println("httpClient ***************************************************************�������ִ���2����"+ee);
			//  
		} finally {
			// �ͷ�����
			httpClient.getConnectionManager().shutdown();
		}
		return doc;
	}

}
