package com.bluebank;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.bluebank.model.Cliente;
import com.bluebank.model.Conta;

import java.util.Date;

@SpringBootApplication
public class BlueBankApplication {


	public static void main(String[] args) {

		SpringApplication.run(BlueBankApplication.class, args);

		Date date = new Date(1997,06,11);
		Cliente cliente = new Cliente(7,"031.202.260-49","Igor Vargas", date,"Fisico","igorvargas","9751248", 5000);
		Conta conta = new Conta(8,1245124,0002,5000,2500,"corrente",cliente);

		StringBuilder dados = new StringBuilder();
		dados.append(conta.getConta()+"\n").append(conta.getTipoConta()+"\n").append(conta.getAgencia()+"\n");


		System.out.println("---Conta---\n" + dados.toString());
	}

}
