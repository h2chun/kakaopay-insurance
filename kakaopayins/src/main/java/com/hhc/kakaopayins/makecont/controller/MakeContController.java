package com.hhc.kakaopayins.makecont.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hhc.kakaopayins.global.entity.ContMst;
import com.hhc.kakaopayins.makecont.service.MakeContService;

@RestController
public class MakeContController {
	
	@Autowired
	private MakeContService service;
	/*
	public MakeContController(MakeContService service) {
		super();
		this.service = service;
	}
	*/
	@PutMapping("/make")
	public ContMst dlngContMst(ContMst contMst) {
		return service.saveContMst(contMst);
	}
}
