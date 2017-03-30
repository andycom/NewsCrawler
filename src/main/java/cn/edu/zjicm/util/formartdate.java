package cn.edu.zjicm.util;

import java.lang.String;

public class formartdate {
	 public String date(String PageTime)
	{
//		 �������ڸ�ʽ
	try{PageTime=PageTime.replace("��", "-");
	PageTime=PageTime.replace("��", "-");
	PageTime=PageTime.replace("��", " ");	}
	catch  (Exception e){
		System.out.println("formartdate�������ó���");
	}
		return PageTime;		
	}

}
