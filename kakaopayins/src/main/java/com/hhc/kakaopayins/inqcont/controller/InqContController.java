package com.hhc.kakaopayins.inqcont.controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hhc.kakaopayins.global.entity.ContMst;
import com.hhc.kakaopayins.inqcont.service.InqContService;

@RestController
public class InqContController {
	
	private InqContService service;

	public InqContController(InqContService service) {
		super();
		this.service = service;
	}
	
	@GetMapping("/inq")
	public List<ContMst> getContMst(String contInfo){
		
		return service.getContMst(contInfo);
	}
	
}
