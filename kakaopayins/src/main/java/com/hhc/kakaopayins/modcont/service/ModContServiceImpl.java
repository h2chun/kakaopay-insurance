package com.hhc.kakaopayins.modcont.service;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import com.hhc.kakaopayins.global.entity.ContMst;
import com.hhc.kakaopayins.global.entity.CvrInfo;
import com.hhc.kakaopayins.global.exception.ErrCode;
import com.hhc.kakaopayins.global.exception.KakaoException;
import com.hhc.kakaopayins.global.util.CalculationUtil;
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
	 * �㺸���� -> �㺸���� �� �Ѻ���� �ݾ׵� ������.
	 * @param contNo ����ȣ
	 * @param insCvr ����㺸
	 * @return ContMst
	 */
	@Override
	@Transactional
	public ContMst modInsCvr(String contNo, String insCvr) {
		ContMst cm = modContRepository.findById(contNo).orElseThrow(null);
		cm.setInsCvr(insCvr);
		cm.setTotInsPrem(CalculationUtil.calTotInsPrem(cm.getPrdtInfo(), insCvr, cm.getContPrd(), cvrInfoRepository));
		return setHnglNm(modContRepository.save(cm));
	}
	
	/**
	 * ���Ⱓ ���� -> ���Ⱓ ���� �� ������������, �Ѻ���ᵵ ������.
	 * @param contNo ����ȣ
	 * @param contPrd ���Ⱓ
	 * @return ContMst
	 */
	@Override
	@Transactional
	public ContMst modContPrd(String contNo, String contPrd) throws Exception {
		ContMst cm = modContRepository.findById(contNo).orElseThrow(null);
		cm.setContPrd(Integer.parseInt(contPrd));
		
		//�������� -> �������� + 1��
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
	 * ������ ���� 
	 * @param contNo ����ȣ
	 * @param contPrd ������
	 * @return ContMst
	 */
	@Override
	@Transactional
	public ContMst modContStat(String contNo, String contStat) {
		ContMst cm = modContRepository.findById(contNo).orElseThrow(null);
		if("�Ⱓ����".equals(cm.getContStat())){
			throw new KakaoException(ErrCode.E1003.getErrMsg(), ErrCode.E1003);	//�̹� ���Ⱓ�� ����� ����Դϴ�.
		}
		
	    int compare = new Date().compareTo(cm.getInsEnddt());
	    System.out.println("compare>>>>>"+compare);
		if(compare >= 0) {
			//�����������ڰ� �����ٸ� �Ⱓ����� ����. 
			throw new KakaoException("�̹� ����� ����� ���Դϴ�.", ErrCode.E1003);	
		}
		
		cm.setContStat(contStat);
		return setHnglNm(modContRepository.save(cm));
	}
	
	
	/**
	 * ���ϵǴ� ������ �ѱ� ��ǰ��/�㺸�� ����
	 * @param ContMst
	 * @return ContMst
	 */
	@Transactional
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

