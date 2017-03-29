package cn.edu.zjicm.util;

import java.net.URL;
import java.sql.Timestamp;

/**
 * 
 * @author Xuexian Wu
 *
 */
public class urls {
	public urls() {

	}

	private URL oriUrl;
	private String url;
	private int urlNo;
	private int hitNum;
	private int statusCode;
	private String charSet;
	private int weight;
	private int layer;
	private String type;
	private String[] urlRefrences;
	private Timestamp lastUpdateTime;
	private String serverType;

	public int getLayer() {
		return layer;
	}

	public void setLayer(int layer) {
		this.layer = layer;
	}

	public URL getoriUrl() {
		return oriUrl;
	}

	public void setoriUrl(URL oriUrl) {
		this.oriUrl = oriUrl;
	}

	public String getUrl() {
		return url;
	}

	public void setCharSet(String charSet) {
		this.charSet = charSet;
	}

	public String getCharSet() {
		return charSet;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public int getStatusCode() {
		return statusCode;
	}

	public void setStatusCode(int statusCode) {
		this.statusCode = statusCode;
	}

	public int getWeight() {
		return weight;
	}

	public void setWeight(int weight) {
		this.weight = weight;
	}

}
