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
			
			Conta conta = new Conta(1,1245124,0002,5000,2500,"corrente", null);
			contaService.save(conta);
			
			Date date1 = new Date(1997,06,11);
			
			
			Cliente cliente = new Cliente(1, "88888888888", conta,"Igor Vargas", date1,"Fisico","igorvargas","9751248", 5000);
			conta.setCliente(cliente);
			
			clienteService.save(cliente);
			conta.setCliente(cliente);
			
			
		    
			
			StringBuilder dados = new StringBuilder();
			dados.append(conta.getConta()+"\n").append(conta.getTipoConta()+"\n").append(conta.getAgencia()+"\n");

			System.out.println("---Conta---\n" + dados.toString());
		
			//Cliente cliente = new Cliente(7,"031.202.260-49","Igor Vargas", date1,"Fisico","igorvargas","9751248", 5000);
		}

}
