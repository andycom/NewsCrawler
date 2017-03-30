
package cn.edu.zjicm.crawler;

import java.net.URL;
import java.util.ArrayList;

import cn.edu.zjicm.spider.core.Gather;
import cn.edu.zjicm.spider.core.zjicmSpider;

/**
 * @author winglive E-mail: fiveme@126.com
 * @version 创建时间：2014-1-12 下午7:23:03 类说明
 */
public class control {
	private Thread SubSpider;

	public void start(int s, ArrayList<URL> urls, String taskName, int deepth) throws Exception {
		ArrayList<URL> seed = urls;
		Gather.getSignal(2);
		SubSpider = new Thread(new zjicmSpider(s, seed, taskName, deepth));
		SubSpider.start(); // 任务控制start()
	}

	@SuppressWarnings("deprecation")
	public void stop() throws Exception {
		Gather.getSignal(0);
	}

	public void reStar() {
		SubSpider.notify();
	}

}
