package cn.edu.zjicm.spider.downloder;

import java.text.SimpleDateFormat;
import java.util.Date;

import cn.edu.zjicm.util.OperateFile;

public class HtmlLoad {
	/**
	 * ִ����ҳ����֮ǰ��ҳ��Դ�봦��������������ҳ��ȥ�롾��桿��ҳ���ַ��Ĵ����������ҳ��ѹ������
	 * ���õײ㱣���ַ�����
	 * 
	 * @param htmldoc
	 * @return doc num;
	 *         �ļ�����������ʱ��ȷ���룬֮���������һ�����������������һλ�����ת�����ַ�֮
	 *         ��ƴ�ӣ�Ŀǰ������ȷ�����㹻����ʹ�á�
	 */
	public long download(String htmldoc) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddhhmmss");
		Date current = new Date();
		String c = sdf.format(current);
		long date = Long.parseLong(c);
		String path = c + ".txt";
		OperateFile f = new OperateFile();
		boolean f1 = f.htmlwrite(path, htmldoc);
		// System.out.println(date);
		return date;
	}

}
