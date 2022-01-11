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
		
		//��������
		SimpleDateFormat format = new SimpleDateFormat ( "yyyyMMdd");
		String dateFormat = format.format(new Date());
		
		//�������� -> �������� + 1��
		Calendar cal = Calendar.getInstance();
		cal.setTime(new Date());
		cal.add(Calendar.DATE, 1);
		String insStdt = format.format(cal.getTime());
		
		
		//���������
		cal.add(Calendar.MONTH, contMst.getContPrd());
		cal.add(Calendar.DATE, -1);
		String insEndDt	= format.format(cal.getTime());
		
		
		format = new SimpleDateFormat ( "yyyyMMddHHmmss");
		
		
		List<CvrInfo> cvrInfoList = cvrInfoRepository.findPrdtInfo(contMst.getPrdtInfo());
		if(cvrInfoList == null) {
			throw new KakaoException(ErrCode.E1000.getErrMsg(), ErrCode.E1000);
		}
		
		
		//���Ⱓ üũ
		int minContPrd = cvrInfoList.get(0).getPrdtInfo().getMinContPrd();
		int maxContPrd = cvrInfoList.get(0).getPrdtInfo().getMaxContPrd();
		if(minContPrd > contMst.getContPrd() || contMst.getContPrd() > maxContPrd) {
			throw new KakaoException(ErrCode.E1001.getErrMsg(), ErrCode.E1001);	//�ش� ��ǰ�� ���Ⱓ�� Ȯ�ιٶ��ϴ�.
		}
		
		//���� ������ �ڵ尡 �ƴ� ��Ī���� ����
		String[] cvrArr = contMst.getInsCvr().split(",");
		StringBuffer sb = new StringBuffer();
		for(String cvr : cvrArr) {
			for(CvrInfo cvrInfo : cvrInfoList) {
				if(cvr.equals(cvrInfo.getCvrCd())) {
					sb.append(cvrInfo.getCvrNm() + ",");
				}
			}
		}
		
		//�Ѻ������
		BigDecimal totInsPrem = CalculationUtil.calTotInsPrem(contMst.getPrdtInfo(), contMst.getInsCvr(), contMst.getContPrd(), cvrInfoRepository);
		
		contMst.setContNo("P"+dateFormat+idxStr);				//����ȣ	-> ���糯¥ + ������
		contMst.setPrdtInfo(cvrInfoList.get(0).getPrdtInfo().getPrdtNm());		//��ǰ����	-> �Է°� 
		contMst.setInsCvr(sb.toString().substring(0, sb.toString().length()-1));			//���Դ㺸 -> �Է°�
		contMst.setContPrd(contMst.getContPrd());			//���Ⱓ -> �Է°�
		contMst.setTotInsPrem(totInsPrem);	//�Ѻ���� -> �㺸�� ���� �� ����
		contMst.setContStat("������");		//�迪����
		
		try {
			
			contMst.setInsStdt(format.parse(insStdt + "000000"));	//��������� -> �����+1
			contMst.setInsEnddt(format.parse(insEndDt + "235959"));	//���������� -> ��������� + (ContPrd + Month)
			
		}catch (Exception e) {
			
			throw new KakaoException("����ġ ���� ���� �߻�.", ErrCode.E0002);
		}
		
		log.info("���尪 Ȯ�� >>>>> "+contMst);
		
		//������
		repository.save(contMst);
		
		return contMst;

	}

}
