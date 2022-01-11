package com.hhc.kakaopayins.quiz3test;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
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
public class ModContTest {
	@Autowired
	MockMvc mockMvc;

	@Autowired
	protected ObjectMapper objectMapper;
	
	public void setup() throws Exception {
		mockMvc = MockMvcBuilders.standaloneSetup(MakeContController.class).build();
	}
	
	/**
	 * 담보 추가/삭제 등 변경하기
	 * @param contNo 	계약번호
	 * @param insCvr	담보정보(담보코드)
	 * @return 계약정보
	 */
	@Test
	void postInsCvrTest() throws Exception{
		
		mockMvc.perform(
				post("/mod/cvr")
				.param("contNo", "P2022010900002")
				.param("insCvr", "P02002,P02001")
		).andDo(print());
	}
	
	
	/**
	 * 계약기간 변경하기
	 * 계약기간이 변경됨에 따라 계약종료일자, 총보험료도 변경됨
	 * @param contNo 	계약번호
	 * @param contPrd	계약기간
	 * @return 계약정보
	 */
	@Test
	void postContPrdTest() throws Exception{
		
		mockMvc.perform(
				post("/mod/prd")
				.param("contNo", "P2022010900001")
				.param("contPrd", String.valueOf(1))
		).andDo(print());
	}
	
	/**
	 * 계약상태변경
	 * 계약기간이 변경됨에 따라 계약종료일자, 총보험료도 변경됨
	 * @param contNo 	계약번호
	 * @param contStat	계약상태
	 * @return 계약정보
	 */
	@Test
	void postContStatTest() throws Exception{
		
		mockMvc.perform(
				post("/mod/stat")
				.param("contNo", "P2022010900002")
				.param("contStat", "청약철회")
		).andDo(print());
	}
	
}
