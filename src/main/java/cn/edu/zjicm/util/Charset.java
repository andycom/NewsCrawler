package cn.edu.zjicm.util;

public final class Charset {
	/**
	 * 
	 * @param doc
	 * @return
	 */
	public String setChar(String doc) {
		String encode = "UTF-8";
		String line = doc;
		while (!("".equals(line))) {
			if (line.indexOf("charset=") != -1) {
				int start = line.indexOf("charset=");
				String tmp = line.substring(start + 8, start + 11);
				if ("ISO".equals(tmp) || "iso".equals(tmp)) {
					encode = "ISO-8859-1";
				} else if ("UTF".equals(tmp) || "utf".equals(tmp)) {
					encode = "UTF-8";
				} else if ("GBK".equals(tmp) || "gbk".equals(tmp)) {
					encode = "GBK";
				} else if ("GB2".equals(tmp) || "gb2".equals(tmp)) {
					encode = "GB2312";
				} else {
					encode = "UTF-8";
				}
				return encode;
			}
		}
		return encode;
	}

}
