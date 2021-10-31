package com.bluebank.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.bluebank.model.Cliente;
import com.bluebank.service.ClienteService;

@RestController
@RequestMapping(value="api/v1/clientes")
public class ClienteController {
	
	@Autowired
	private ClienteService clienteService;
	
	
	@RequestMapping(method = RequestMethod.GET)
	public List<Cliente> findAll(){
		List<Cliente> listCliente = clienteService.findAll();
		return listCliente;
	}

}
