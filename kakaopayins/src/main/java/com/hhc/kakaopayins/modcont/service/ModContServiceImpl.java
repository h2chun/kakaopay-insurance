package com.hhc.kakaopayins.modcont.service;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.NoSuchElementException;

import org.springframework.stereotype.Service;

import com.hhc.kakaopayins.global.entity.ContMst;
import com.hhc.kakaopayins.global.entity.CvrInfo;
import com.hhc.kakaopayins.global.exception.ErrCode;
import com.hhc.kakaopayins.global.exception.KakaoException;
import com.hhc.kakaopayins.global.util.CalculationUtil;
import com.hhc.kakaopayins.global.util.ValidationUtil;
import com.hhc.kakaopayins.makecont.repository.CvrInfoRepository;
import com.hhc.kakaopayins.modcont.repository.ModContRepository;

@Service
public class ModContServiceImpl implements ModContService {
	
	private ModContRepository modContRepository;
	private CvrInfoRepository cvrInfoRepository;
	
	public ModContServiceImpl(ModContRepository modContRepository, CvrInfoRepository cvrInfoRepository) {
		super();
		this.modContRepository = modContRepository;
		this.cvrInfoRepository = cvrInfoRepository;
	}
	
	/**
	 * 담보수정 -> 담보수정 후 총보험금도 수정함.
	 * @param contNo 계약번호
	 * @param insCvr 보험담보
	 * @return ContMst
	 */
	@Override
	public ContMst modInsCvr(String contNo, String insCvr) {
		
		ValidationUtil.chkDupCvrInfo(insCvr);	//중복입력여부 체크
		
		ContMst cm = modContRepository.findById(contNo).orElseThrow(() ->new KakaoException(ErrCode.E1002.getErrMsg(), ErrCode.E1002));
		
		if("기간만료".equals(cm.getContStat())){
			throw new KakaoException(ErrCode.E1003.getErrMsg(), ErrCode.E1003);	//이미 기간이 만료된 계약입니다.
		}
		
		cm.setInsCvr(insCvr);
		cm.setTotInsPrem(CalculationUtil.calTotInsPrem(cm.getPrdtInfo(), insCvr, cm.getContPrd(), cvrInfoRepository));
		return setHnglNm(modContRepository.save(cm));
	}
	
	/**
	 * 계약기간 수정 -> 계약기간 수정 후 보험종료일자, 총보험료도 수정함.
	 * @param contNo 계약번호
	 * @param contPrd 계약기간
	 * @return ContMst
	 */
	@Override
	public ContMst modContPrd(String contNo, String contPrd) throws Exception {
		
		
		ContMst cm = modContRepository.findById(contNo).orElseThrow(() ->new KakaoException(ErrCode.E1002.getErrMsg(), ErrCode.E1002));
		if("기간만료".equals(cm.getContStat())){
			throw new KakaoException(ErrCode.E1003.getErrMsg(), ErrCode.E1003);	//이미 기간이 만료된 계약입니다.
		}
		
		cm.setContPrd(Integer.parseInt(contPrd));
		
		List<CvrInfo> cvrInfoList = cvrInfoRepository.findPrdtInfo(cm.getPrdtInfo());
		ValidationUtil.chkContPrd(cvrInfoList, Integer.parseInt(contPrd));
		
		//계약시작일 -> 현재일자 + 1일
		SimpleDateFormat format = new SimpleDateFormat ( "yyyyMMdd");
		Calendar cal = Calendar.getInstance();
		cal.setTime(cm.getInsStdt());
		cal.add(Calendar.MONTH, cm.getContPrd());
		cal.add(Calendar.DATE, -1);
		String insEndDt	= format.format(cal.getTime());
		format = new SimpleDateFormat ( "yyyyMMddHHmmss");
		
		cm.setInsEnddt(format.parse(insEndDt + "235959"));
		cm.setTotInsPrem(CalculationUtil.calTotInsPrem(cm.getPrdtInfo(), cm.getInsCvr(), cm.getContPrd(), cvrInfoRepository));
		return setHnglNm(modContRepository.save(cm));
	}
	
	/**
	 * 계약상태 수정
	 * @param contNo 계약번호
	 * @param contPrd 계약상태
	 * @return ContMst
	 */
	@Override
	public ContMst modContStat(String contNo, String contStat) {
		ContMst cm = modContRepository.findById(contNo).orElseThrow(() ->new KakaoException(ErrCode.E1002.getErrMsg(), ErrCode.E1002));
		
		if("기간만료".equals(cm.getContStat())){
			throw new KakaoException(ErrCode.E1003.getErrMsg(), ErrCode.E1003);	//이미 기간이 만료된 계약입니다.
		}
		
	    int compare = new Date().compareTo(cm.getInsEnddt());
	    System.out.println("compare>>>>>"+compare);
		if(compare >= 0) {
			//보험종료일자가 지났다면 기간만료로 본다.
			throw new KakaoException(ErrCode.E1004.getErrMsg(), ErrCode.E1004);	//이미 계약이 종료된 건입니다.
		}
		
		cm.setContStat(contStat);
		return setHnglNm(modContRepository.save(cm));
	}
	
	
	/**
	 * 리턴되는 정보에 한글 상품명/담보명 셋팅
	 * @param ContMst
	 * @return ContMst
	 */
	public ContMst setHnglNm(ContMst contMst) {
		
		CvrInfo cvrInfo = null;
		StringBuffer sb = new StringBuffer();
		String[] cvrArr = contMst.getInsCvr().split(",");
		for(String cvr : cvrArr) {
			cvrInfo = cvrInfoRepository.findCvrInfo(cvr, contMst.getPrdtInfo());
			sb.append(cvrInfo.getCvrNm()+",");
		}
		
		contMst.setInsCvr(sb.toString().substring(0, sb.toString().length()-1));
		contMst.setPrdtInfo(cvrInfo.getPrdtInfo().getPrdtNm());
		
		return contMst;
	}

}

