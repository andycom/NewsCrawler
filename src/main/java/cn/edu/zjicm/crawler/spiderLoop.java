
package cn.edu.zjicm.crawler;

import java.util.Timer;
import java.util.TimerTask;

import org.omg.CORBA.PUBLIC_MEMBER;

import cn.edu.zjicm.pipeline.sql2005;

/**
 * @author winglive E-mail: fiveme@126.com
 * @version ����ʱ�䣺2014-1-11 ����1:27:19 ��˵�� ������ѯ����
 */
public class spiderLoop {
	private int signal;

	/**
	 * @param args
	 */
	public int ManageDBConnection(final sql2005 dbcooection) {
		// TODO Auto-generated method stub
		Timer timer = new java.util.Timer(false);
		TimerTask task = new java.util.TimerTask() {
			private int k = 0, l = 0, s = 0, v = 0;
			String sql = "1"; // �Ժ�������ļ���ȡ���ݿ��ַ��Ϣ�����ţ���������
			String ip = "127.0.0.1";

			@Override
			public void run() {
				System.out.println("��Ҫ��ʱִ�е�����..." + k);
				try // ������ʾ�õ�try �����Ϊʲô������
				{
					l = dbcooection.insert(sql, ip);
				} catch (Exception e) {
					System.out.println("�������ó���");
				}
				k++;
				if (l > 1 && s < 1) {
					System.out.println("�������棡��");
					s = 2;
					// ���������״λ�ȡ�ź� �������棬֮�����źţ����ٵ�����������
				} else if (l < 1 && s > 1) {
					System.out.println("�ź����ı�ֹͣ����");
					s = 0;
					// ֹͣ�������źţ��ı�һֱֹͣ���棬�����Ҫ��������б�����״̬���źţ�����Ҫһֱֹͣ������������

				} else {
					System.out.println("�ֽ׶ο����ź�" + l + "����״̬�ź�" + s);
					signal = l;
				}

			}
		};
		java.util.Date time = new java.util.Date();
		long delay = 500; // 3��ѯ��һ��
		// ������ʱ��������ִ��Ҽ�Σ�Ȼ��ÿ������ִ��Ҽ��
		timer.schedule(task, time, delay);
		return signal;

	}

}
