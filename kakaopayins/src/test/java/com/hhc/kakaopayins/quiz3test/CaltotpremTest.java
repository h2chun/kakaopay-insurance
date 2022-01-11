package com.hhc.kakaopayins.quiz3test;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hhc.kakaopayins.caltotprem.controller.CalTotPremController;

@SpringBootTest
@AutoConfigureMockMvc
public class CaltotpremTest {
	@Autowired
	MockMvc mockMvc;

	@Autowired
	protected ObjectMapper objectMapper;
	
	public void setup() throws Exception {
		mockMvc = MockMvcBuilders.standaloneSetup(CalTotPremController.class).build();
	}
	
	/**
	 * 예산총보험금 조회
	 * 
	 * @param prdtInfo	상품정보
	 * @param insCvr	계약담보정보
	 * @param contPrd	계약기간
	 * @return ContMst	�������
	 */
	@Test
	void dlngContMstTest() throws Exception{
		
		mockMvc.perform(
				get("/cal")
				.param("prdtInfo", "PRDT02")
				.param("insCvr", "P02001")
				.param("contPrd", String.valueOf(5))
		).andDo(print());
	}
}
