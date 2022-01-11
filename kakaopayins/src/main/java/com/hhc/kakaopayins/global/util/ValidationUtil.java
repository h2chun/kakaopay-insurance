package com.hhc.kakaopayins.global.util;

import java.text.SimpleDateFormat;
import java.util.List;

import com.hhc.kakaopayins.global.entity.CvrInfo;
import com.hhc.kakaopayins.global.exception.ErrCode;
import com.hhc.kakaopayins.global.exception.KakaoException;

public class ValidationUtil {
	/**
	 * 입력된 문자가 날짜형인지 체크하여 날짜 포멧을 리턴한다.
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
	 * 입력된 문자가 숫자인지 확인한다.
     * @param numStr
     * @return boolean
     */
	public static boolean chkNumberType(String numStr) {
		return numStr.matches("[+-]?\\d*(\\.\\d+)?");
	}
	
	/**
	 * 담보가 중복되었는지 체크한다.
	 * @param cvrInfo
	 */
	public static void chkDupCvrInfo(String cvrInfo) {
		
		String[] cvrArr = cvrInfo.split(",");
		
		if(cvrArr.length > 1) {
			for(int i = 0; i < cvrArr.length; i++) {
				for(int k = 0; k < cvrArr.length; k++) {
					
					if(i == k) continue;
					if(cvrArr[i].equals(cvrArr[k])) {
						throw new KakaoException(ErrCode.E1005.getErrMsg(), ErrCode.E1005);	//담보가 중복 입력되었습니다.
					}
				}
			}
		}
	}
	
	/**
	 * 기간 중복 체크
	 * @param cvrInfoList
	 * @param contPrd
	 */
	public static void chkContPrd(List<CvrInfo> cvrInfoList, int contPrd) {
		
		if(cvrInfoList == null) {
			throw new KakaoException(ErrCode.E1000.getErrMsg(), ErrCode.E1000);	//상품/담보 코드가 정확하지 않습니다.
		}
		
		//계약기간 체크
		int minContPrd = cvrInfoList.get(0).getPrdtInfo().getMinContPrd();
		int maxContPrd = cvrInfoList.get(0).getPrdtInfo().getMaxContPrd();
		if(minContPrd > contPrd || contPrd > maxContPrd) {
			throw new KakaoException(ErrCode.E1001.getErrMsg(), ErrCode.E1001);	//해당 상품에 계약기간을 확인바랍니다.
		}
	}
}