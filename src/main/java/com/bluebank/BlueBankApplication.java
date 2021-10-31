package com.bluebank;

import java.util.Arrays;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.bluebank.model.Cliente;
import com.bluebank.model.Conta;
import com.bluebank.repository.ClienteRepository;
import com.bluebank.service.ClienteService;

@SpringBootApplication
public class BlueBankApplication implements CommandLineRunner {

	@Autowired
	private ClienteService clienteService;
	
	
	
	public static void main(String[] args) {

		SpringApplication.run(BlueBankApplication.class, args);
		
	}
		
		@Override
		public void run(String... args) {
			

			Date date1 = new Date(1997,06,11);
			Date date2 = new Date(1997,06,11);
			Cliente cliente = new Cliente(7,"031.202.260-49","Igor Vargas", date1,"Fisico","igorvargas","9751248", 5000);
			Cliente cliente2 = new Cliente(8,"01203844589","Abmael de Lima Ferreira", date2,"Juridico", "abmael", "9999999", 60000);
			
		
	
			clienteService.save( cliente);
			clienteService.save( cliente2);
		    
			
			Conta conta = new Conta(8,1245124,0002,5000,2500,"corrente",cliente);
			
		

			StringBuilder dados = new StringBuilder();
			dados.append(conta.getConta()+"\n").append(conta.getTipoConta()+"\n").append(conta.getAgencia()+"\n");


			System.out.println("---Conta---\n" + dados.toString());
		
		}

}
