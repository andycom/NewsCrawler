package cn.edu.zjicm.spider.zjicmspider;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

import org.apache.logging.log4j.Logger;

import cn.edu.zjicm.spider.core.zjicmSpider;

public class zjicmspider {

	public static void main(String[] args) throws Exception {

		ArrayList<URL> seedUrls = new ArrayList<URL>();
		String taskName = "zjicm0813";
		int deepth = 5;
		PropertyConfigurator.configure("src/log4j.properties");
		zjicmspider.logger = Logger.getLogger(zjicmspider.class);
		// zjicmSpider spider = new zjicmSpider(seedUrls, taskName,deepth); //

		zjicmspider.logger.info("start " + taskName + "\tURLS" + seedUrls);
		// spider.start();

	}
}
