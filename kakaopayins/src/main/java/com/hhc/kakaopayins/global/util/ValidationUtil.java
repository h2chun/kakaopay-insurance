package com.hhc.kakaopayins.global.util;

import java.text.SimpleDateFormat;

public class ValidationUtil {
	/**
     * �Էµ� ���ڰ� ��¥������ üũ�Ͽ� ��¥ ������ �����Ѵ�.
     * @param date
     * @return chkDateFormat
     */
	public static String chkDateFormat(String date) {
		try {
			
			String chkDateFormat = "";
			int len = date.length();
			SimpleDateFormat dateFormatParser;
			if(len < 8) {
				return null;
				
			}else if(len == 8) {
				chkDateFormat = "yyyyMMdd";
			}else if(len == 10) {
				
				String[] dateArr = date.split("-");
				if(dateArr.length == 3) {
					chkDateFormat = "yyyy-MM-dd";
					
				}else {
					dateArr = date.split("/");
					if(dateArr.length == 3) {
						chkDateFormat = "yyyy/MM/dd";
					}else {
						return null;
					}
				}
			}else {
				return null;
			}
			
			dateFormatParser = new SimpleDateFormat(chkDateFormat);
			dateFormatParser.setLenient(false);
            dateFormatParser.parse(date);
            return chkDateFormat;
		}catch (Exception e) {
			return null;
		}
	}
	
	/**
     * �Էµ� ���ڰ� �������� Ȯ���Ѵ�.
     * @param numStr
     * @return boolean
     */
	public static boolean chkNumberType(String numStr) {
		return numStr.matches("[+-]?\\d*(\\.\\d+)?");
	}
}