package cn.edu.zjicm.spider.zjicmspider;

import java.net.URL;
import java.util.ArrayList;

import org.slf4j.LoggerFactory;

public class zjicmspider {

	public static void main(String[] args) throws Exception {
		org.slf4j.Logger logger = LoggerFactory.getLogger("");
		ArrayList<URL> seedUrls = new ArrayList<URL>();
		String taskName = "zjicm0813";
		int deepth = 5;
		// zjicmSpider spider = new zjicmSpider(seedUrls, taskName,deepth); //

		logger.info("start " + taskName + "\tURLS" + seedUrls);
		// spider.start();

	}
}
