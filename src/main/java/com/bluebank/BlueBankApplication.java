package com.bluebank;

import java.time.LocalDate;
import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.bluebank.entities.Cliente;
import com.bluebank.entities.Conta;
import com.bluebank.repository.ClienteRepository;
import com.bluebank.repository.ContaRepository;

@SpringBootApplication
public class BlueBankApplication implements CommandLineRunner {

	@Autowired
	private ClienteRepository clienteRepository;

	@Autowired
	private ContaRepository contaRepository;

	public static void main(String[] args) {

		SpringApplication.run(BlueBankApplication.class, args);

	}

	@Override
	public void run(String... args) {

		Conta conta = Conta.builder()
				.id(null)
				.conta(88888L)
				.agencia(777L)
				.saldo(700.00)
				.limiteCredito(1000.00)
				.tipoConta("PF")
				.build();
		
		Conta conta2= Conta.builder()
				.id(null)
				.conta(88888L)
				.agencia(777L)
				.saldo(700.00)
				.limiteCredito(1000.00)
				.tipoConta("PF")
				.build();
		
		Conta conta3 = Conta.builder()
				.id(null)
				.conta(88888L)
				.agencia(777L)
				.saldo(700.00)
				.limiteCredito(1000.00)
				.tipoConta("PF")
				.build();

		Cliente cliente = Cliente.builder()
				.id(null)
				.cpf("12312345678")
				.nomeCompleto("Joao da Silva")
				.dataNascimento(LocalDate.of(2000, 10, 10))
				.tipoCliente("PF")
				.email("joao@gmail.com")
				.telefone("(88)987541236")
				.rendaMensal(3570.00)
				.build();
		
		Cliente cliente2 = Cliente.builder()
				.id(null)
				.cpf("12312345678")
				.nomeCompleto("Marcos Vinicius")
				.dataNascimento(LocalDate.of(1998, 10, 10))
				.tipoCliente("PF")
				.email("marcos@gmail.com")
				.telefone("(88)987541236")
				.rendaMensal(3570.00)
				.build();
		
		conta.setCliente(cliente);
		conta2.setCliente(cliente);
		conta3.setCliente(cliente2);
		cliente.getContas().add(conta);
		cliente.getContas().add(conta2);
		cliente.getContas().add(conta3);
		clienteRepository.saveAll(Arrays.asList(cliente, cliente2));
		contaRepository.saveAll(Arrays.asList(conta, conta2, conta3));
		

		
	}



}
