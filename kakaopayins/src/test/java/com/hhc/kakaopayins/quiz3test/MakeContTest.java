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
	 * ������
	 * @param prdtInfo 	��ǰ����(��ǰ�ڵ�)
	 * @param insCvr	�㺸����(�㺸�ڵ�)
	 * @param contPrd	���Ⱓ
	 * @return �������
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
