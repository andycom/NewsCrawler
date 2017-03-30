package cn.edu.zjicm.crawler;

/**
 * @author winglive E-mail: fiveme@126.com
 * @version ����ʱ�䣺2014-1-11 ����1:51:33 ��˵��
 */
public class spiderconfig {
	// ���������

	private String spiderName; // ��������
	private String localIp; // �������ip
	private int spiderId; // ������
	private int spiderType; // ��������/΢�������š���̳
	private String localDBuserName; //���汾�����ݿ��û���
	private String localDBuserpassword;// ���汾�����ݿ�����

	public int getSpiderId() {
		return spiderId;
	}

	public void setSpiderId(int spiderId) {
		this.spiderId = spiderId;
	}

	public int getSpiderType() {
		return spiderType;
	}

	public void setSpiderType(int spiderType) {
		this.spiderType = spiderType;
	}

	public void setSpiderName(String name) {
		this.spiderName = name;
	}

	public String getSpiderName() {
		return spiderName;
	}

	public void setSpiderIp(String ip) {
		this.localIp = ip;
	}

	public String getSpiderIp() {
		return localIp;
	}

	public String getLocalUsername() {
		return localDBuserName;
	}

	public void setLocalUsername(String DBusername) {
		this.localDBuserName = DBusername;
	}

	public String getlocalUserpassWord() {
		return localDBuserpassword;
	}

	public void setlocalUserpassWord(String DBpassword) {
		this.localDBuserpassword = DBpassword;
	}

}
