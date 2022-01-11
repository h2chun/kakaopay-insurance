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
	 * �㺸 �߰�/���� �� �����ϱ�
	 * @param contNo 	����ȣ
	 * @param insCvr	�㺸����(�㺸�ڵ�)
	 * @return �������
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
	 * ���Ⱓ �����ϱ�
	 * ���Ⱓ�� ����ʿ� ���� �����������, �Ѻ���ᵵ �����
	 * @param contNo 	����ȣ
	 * @param contPrd	���Ⱓ
	 * @return �������
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
	 * �����º���
	 * ���Ⱓ�� ����ʿ� ���� �����������, �Ѻ���ᵵ �����
	 * @param contNo 	����ȣ
	 * @param contStat	������
	 * @return �������
	 */
	@Test
	void postContStatTest() throws Exception{
		
		mockMvc.perform(
				post("/mod/stat")
				.param("contNo", "P2022010900002")
				.param("contStat", "û��öȸ")
		).andDo(print());
	}
	
}
