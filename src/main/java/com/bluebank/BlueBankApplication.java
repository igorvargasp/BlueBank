package com.bluebank;

import java.time.LocalDate;

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

		LocalDate date1 = LocalDate.of(2000, 10, 10);

		Cliente cliente = Cliente.builder()
				.id(null)
				.cpf("12312345678")
				.nomeCompleto("Joao da Silva")
				.dataNascimento(date1)
				.tipoCliente("PF")
				.email("joao@gmail.com")
				.telefone("(88)987541236")
				.rendaMensal(3570.00)
				.build();
		
		conta.setCliente(cliente);
		cliente.getContas().add(conta);
		clienteRepository.save(cliente);
		contaRepository.save(conta);
		

		
	}
=======
		@Override
		public void run(String... args) {
			

		}


}
