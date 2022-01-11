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
import com.hhc.kakaopayins.inqcont.controller.InqContController;

@SpringBootTest
@AutoConfigureMockMvc
public class InqContTest {
	@Autowired
	MockMvc mockMvc;

	@Autowired
	protected ObjectMapper objectMapper;
	
	public void setup() throws Exception {
		mockMvc = MockMvcBuilders.standaloneSetup(InqContController.class).build();
	}
	
	/**
	 * 계약생선
	 * 계약정보 중 어떤 값이 파라미터로 들어와도 모두 조회 가능함.
	 * 단, 다건으로 조회 될 수 있음.
	 * 예) 파라미터 5 -> 계약기간이 5인 건 모두 조회함.
	 * 
	 * @param contInfo	������� �� �Ѱ���
	 * @return ContMst	�������
	 */
	@Test
	void inqContTest() throws Exception{
		
		mockMvc.perform(
				get("/inq")
				.param("contInfo", String.valueOf(5))
		).andDo(print());
	}
	
}
