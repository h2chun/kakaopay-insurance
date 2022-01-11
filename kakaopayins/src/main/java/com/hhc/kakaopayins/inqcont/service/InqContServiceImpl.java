package com.hhc.kakaopayins.inqcont.service;

import java.math.BigDecimal;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import com.hhc.kakaopayins.global.entity.ContMst;
import com.hhc.kakaopayins.global.entity.CvrInfo;
import com.hhc.kakaopayins.global.exception.ErrCode;
import com.hhc.kakaopayins.global.exception.KakaoException;
import com.hhc.kakaopayins.global.util.ValidationUtil;
import com.hhc.kakaopayins.inqcont.repository.ContMstRepository;
import com.hhc.kakaopayins.makecont.repository.CvrInfoRepository;

@Service
@Transactional
public class InqContServiceImpl implements InqContService {
	
	private ContMstRepository contMstRepository;
	private CvrInfoRepository cvrInfoRepository;
	
	public InqContServiceImpl(ContMstRepository contMstRepository, CvrInfoRepository cvrInfoRepository) {
		super();
		this.contMstRepository = contMstRepository;
		this.cvrInfoRepository = cvrInfoRepository;
	}

	@Override
	public List<ContMst> getContMst(String contInfo) {

		List<ContMst> rtn = null;
		
		String dtForm = ValidationUtil.chkDateFormat(contInfo);
		
		if(dtForm != null) {
			rtn = contMstRepository.findDateType(contInfo, dtForm);
		}
		
		if(ValidationUtil.chkNumberType(contInfo)) {
			List<ContMst> numeric = contMstRepository.findNumberType(new BigDecimal(contInfo));
			
			if(rtn != null) {
				for(ContMst add : numeric) {
					rtn.add(add);
				}
			}else {
				rtn = numeric;
			}
			
		}
		
		List<ContMst> inq = contMstRepository.findVarCharType(contInfo);
		if(rtn != null) {
			for(ContMst add : inq) {
				rtn.add(add);
			}
		}else {
			rtn = inq;
		}
		
		
		if(rtn.size() == 0) {
			throw new KakaoException(ErrCode.E1002.getErrMsg(), ErrCode.E1002);
		}
		
		int len = rtn.size();
		for(int i = 0; i < len; i++) {
			
			CvrInfo cvrInfo = null;
			String[] tStr = rtn.get(i).getInsCvr().split(",");
			StringBuffer cvrNm = new StringBuffer();
			for(String cvr : tStr) {
				cvrInfo = cvrInfoRepository.findCvrInfo(cvr, rtn.get(i).getPrdtInfo());
				cvrNm.append(cvrInfo.getCvrNm()+",");
			}
			
			rtn.get(i).setPrdtInfo(cvrInfo.getPrdtInfo().getPrdtNm());
			rtn.get(i).setInsCvr(cvrNm.toString().substring(0, cvrNm.toString().length()-1));
		}
		
		
		
		return rtn;
	}

}
