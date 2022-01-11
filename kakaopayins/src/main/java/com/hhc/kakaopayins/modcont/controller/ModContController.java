package com.hhc.kakaopayins.modcont.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hhc.kakaopayins.global.entity.ContMst;
import com.hhc.kakaopayins.modcont.service.ModContService;

@RestController
public class ModContController {
	
	private ModContService service;
	
	public ModContController(ModContService service) {
		super();
		this.service = service;
	}

	@PostMapping("/mod/cvr")
	public ContMst postInsCvr(String contNo, String insCvr) {
		return service.modInsCvr(contNo, insCvr);
	}
	
	@PostMapping("/mod/prd")
	public ContMst postContPrd(String contNo, String contPrd) throws Exception {
		return service.modContPrd(contNo, contPrd);
	}
	
	@PostMapping("/mod/stat")
	public ContMst postContStat(String contNo, String contStat) {
		return service.modContStat(contNo, contStat);
	}
}
