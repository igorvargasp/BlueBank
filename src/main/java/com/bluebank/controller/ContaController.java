package com.bluebank.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.bluebank.model.Conta;
import com.bluebank.service.ContaService;

@RestController
@RequestMapping(value="api/v1/Contas")
public class ContaController {
	
	@Autowired
	private ContaService contaService;
	
	
	@RequestMapping(method = RequestMethod.GET)
	public List<Conta> findAll(){
		List<Conta> listConta = contaService.findAll();
		return listConta;
	}
	
	
}
