package com.bluebank;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.bluebank.model.Cliente;
import com.bluebank.model.Conta;
import com.bluebank.repository.ClienteRepository;
import com.bluebank.service.ClienteService;
import com.bluebank.service.ContaService;

@SpringBootApplication
public class BlueBankApplication implements CommandLineRunner {

	@Autowired
	private ClienteService clienteService;
	
	@Autowired
	private ContaService contaService;
	
	public static void main(String[] args) {

		SpringApplication.run(BlueBankApplication.class, args);
		
	}
		
		@Override
		public void run(String... args) {
			

		}

}
