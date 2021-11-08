package com.bluebank;

import java.time.Instant;
import java.time.LocalDate;
import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.bluebank.entities.Cliente;
import com.bluebank.entities.Conta;
import com.bluebank.entities.enums.StatusConta;
import com.bluebank.entities.enums.TipoCliente;
import com.bluebank.entities.enums.TipoConta;
import com.bluebank.entities.enums.TipoTransacao;
import com.bluebank.repository.ClienteRepository;
import com.bluebank.repository.ContaRepository;
import com.bluebank.service.ContaService;

@SpringBootApplication
public class BlueBankApplication implements CommandLineRunner {

	@Autowired
	private ClienteRepository clienteRepository;

	@Autowired
	private ContaRepository contaRepository;
	
	@Autowired
	private ContaService contaService;

	public static void main(String[] args) {

		SpringApplication.run(BlueBankApplication.class, args);

	}

	@Override
	public void run(String... args) {

		/*
		Cliente cliente = Cliente.builder()
				.id(null)
				.cpf("12312345678")
				.nomeCompleto("Joao da Silva")
				.dataNascimento(LocalDate.of(2000, 10, 10))
				.tipo(TipoCliente.PF.getCod())
				.email("joao@gmail.com")
				.telefone("(88)987541236")
				.rendaMensal(3570.00)
				.criadoEm(Instant.now())
				.build();
		
		Cliente cliente2 = Cliente.builder()
				.id(null)
				.cpf("12312345678")
				.nomeCompleto("Marcos Vinicius")
				.dataNascimento(LocalDate.of(1998, 10, 10))
				.tipo(TipoCliente.PF.getCod())
				.email("marcos@gmail.com")
				.telefone("(88)987541236")
				.rendaMensal(3570.00)
				.criadoEm(Instant.now())
				.build();
		
		Conta conta = Conta.builder()
				.id(null)
				.conta("100001")
				.agencia("101")
				.saldo(700.00)
				.limiteCredito(1000.00)
				.tipo(TipoConta.CORRENTE.getCod())
				.status(StatusConta.ATIVA.getCod())
				.cliente(cliente)
				.criadoEm(Instant.now())
				.build();
		
		Conta conta2= Conta.builder()
				.id(null)
				.conta("20002")
				.agencia("202")
				.saldo(800.00)
				.limiteCredito(2000.00)
				.tipo(TipoConta.CORRENTE.getCod())
				.status(StatusConta.ATIVA.getCod())
				.cliente(cliente)
				.criadoEm(Instant.now())
				.build();
		
		Conta conta3 = Conta.builder()
				.id(null)
				.conta("30003")
				.agencia("303")
				.saldo(900.00)
				.limiteCredito(3000.00)
				.tipo(TipoConta.CORRENTE.getCod())
				.status(StatusConta.ATIVA.getCod())
				.cliente(cliente2)
				.criadoEm(Instant.now())
				.build();
		
		cliente.getContas().add(conta);
		cliente.getContas().add(conta2);
		cliente.getContas().add(conta3);
		clienteRepository.saveAll(Arrays.asList(cliente, cliente2));
		contaRepository.saveAll(Arrays.asList(conta, conta2, conta3));
		
		contaService.transferFunds(conta2.getId(), conta.getId(), 1000.00, TipoTransacao.PIX.getCod());
		*/
	}



}
