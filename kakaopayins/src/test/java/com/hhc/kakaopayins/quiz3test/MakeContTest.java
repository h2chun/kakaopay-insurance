package com.hhc.kakaopayins.quiz3test;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hhc.kakaopayins.makecont.controller.MakeContController;

@SpringBootTest
@AutoConfigureMockMvc
public class MakeContTest {
	@Autowired
	MockMvc mockMvc;

	@Autowired
	protected ObjectMapper objectMapper;
	
	public void setup() throws Exception {
		mockMvc = MockMvcBuilders.standaloneSetup(MakeContController.class).build();
	}
	
	/**
	 * 계약생성
	 * @param prdtInfo 	상품정보(상품코드)
	 * @param insCvr	담보정보(담보코드)
	 * @param contPrd	계약기간
	 * @return 계약정보
	 */
	@Test
	void makeContTest() throws Exception{
		
		mockMvc.perform(
				put("/make")
				.param("prdtInfo", "PRDT01")
				.param("insCvr", "P01001,P01002")
				.param("contPrd", String.valueOf(3))
		).andDo(print());
	}
	
	
	
}
