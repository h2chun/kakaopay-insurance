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
	 * �����ȸ
	 * ������� �� � ���� �Ķ���ͷ� ���� ��� ��ȸ ������
	 * ��, �ܰ��� �ƴ� �ٰ����� ��ȸ��
	 * ��) �Ķ���� 5 -> ���Ⱓ�� 5�� �� ��� ��ȸ��.
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
