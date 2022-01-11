package com.hhc.kakaopayins.makecont.service;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import com.hhc.kakaopayins.global.entity.ContMst;
import com.hhc.kakaopayins.global.entity.CvrInfo;
import com.hhc.kakaopayins.global.exception.ErrCode;
import com.hhc.kakaopayins.global.exception.KakaoException;
import com.hhc.kakaopayins.global.util.CalculationUtil;
import com.hhc.kakaopayins.makecont.repository.ContNoInfoRepository;
import com.hhc.kakaopayins.makecont.repository.CvrInfoRepository;
import com.hhc.kakaopayins.makecont.repository.MakeContRepository;

import lombok.extern.slf4j.Slf4j;

@Service
@Transactional
@Slf4j
public class MakeContServiceImpl implements MakeContService{
	
	private MakeContRepository repository;
	private ContNoInfoRepository contNoInfoRepo;
	private CvrInfoRepository cvrInfoRepository;
	
	public MakeContServiceImpl(MakeContRepository repository, ContNoInfoRepository contNoInfoRepo, CvrInfoRepository cvrInfoRepository) {
		super();
		this.repository = repository;
		this.contNoInfoRepo = contNoInfoRepo;
		this.cvrInfoRepository = cvrInfoRepository;
	}

	@Override
	public ContMst saveContMst(ContMst contMst){
		/*
		int idx = contNoInfoRepo.findMaxValue()+1;
		String idxStr = String.format("%05d", idx);
		
		contNoInfoRepo.updateValue(idx);
		*/
		
		int idx = contNoInfoRepo.findNextSeq();
		String idxStr = String.format("%05d", idx);
		
		//현재일자
		SimpleDateFormat format = new SimpleDateFormat ( "yyyyMMdd");
		String dateFormat = format.format(new Date());
		
		//계약시작일 -> 현재일자 + 1일
		Calendar cal = Calendar.getInstance();
		cal.setTime(new Date());
		cal.add(Calendar.DATE, 1);
		String insStdt = format.format(cal.getTime());
		
		
		//계약종료일
		cal.add(Calendar.MONTH, contMst.getContPrd());
		cal.add(Calendar.DATE, -1);
		String insEndDt	= format.format(cal.getTime());
		
		
		format = new SimpleDateFormat ( "yyyyMMddHHmmss");
		
		
		List<CvrInfo> cvrInfoList = cvrInfoRepository.findPrdtInfo(contMst.getPrdtInfo());
		if(cvrInfoList == null) {
			throw new KakaoException(ErrCode.E1000.getErrMsg(), ErrCode.E1000);
		}
		
		
		//계약기간 체크
		int minContPrd = cvrInfoList.get(0).getPrdtInfo().getMinContPrd();
		int maxContPrd = cvrInfoList.get(0).getPrdtInfo().getMaxContPrd();
		if(minContPrd > contMst.getContPrd() || contMst.getContPrd() > maxContPrd) {
			throw new KakaoException(ErrCode.E1001.getErrMsg(), ErrCode.E1001);	//해당 상품에 계약기간을 확인바랍니다.
		}
		
		//리턴 값에는 코드가 아닌 명칭으로 변경
		String[] cvrArr = contMst.getInsCvr().split(",");
		StringBuffer sb = new StringBuffer();
		for(String cvr : cvrArr) {
			for(CvrInfo cvrInfo : cvrInfoList) {
				if(cvr.equals(cvrInfo.getCvrCd())) {
					sb.append(cvrInfo.getCvrNm() + ",");
				}
			}
		}
		
		//총보험료계산
		BigDecimal totInsPrem = CalculationUtil.calTotInsPrem(contMst.getPrdtInfo(), contMst.getInsCvr(), contMst.getContPrd(), cvrInfoRepository);
		
		contMst.setContNo("P"+dateFormat+idxStr);				//계약번호	-> 현재날짜 + 시퀀스
		contMst.setPrdtInfo(cvrInfoList.get(0).getPrdtInfo().getPrdtNm());		//상품정보	-> 입력값 
		contMst.setInsCvr(sb.toString().substring(0, sb.toString().length()-1));			//가입담보 -> 입력값
		contMst.setContPrd(contMst.getContPrd());			//계약기간 -> 입력값
		contMst.setTotInsPrem(totInsPrem);	//총보험료 -> 담보에 따른 값 셋팅
		contMst.setContStat("정상계약");		//계역상태
		
		try {
			
			contMst.setInsStdt(format.parse(insStdt + "000000"));	//보험시작일 -> 계약일+1
			contMst.setInsEnddt(format.parse(insEndDt + "235959"));	//보험종료일 -> 보험시작일 + (ContPrd + Month)
			
		}catch (Exception e) {
			
			throw new KakaoException("예상치 못한 오류 발생.", ErrCode.E0002);
		}
		
		log.info("저장값 확인 >>>>> "+contMst);
		
		//계약생성
		repository.save(contMst);
		
		return contMst;

	}

}
