package com.bluebank.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bluebank.model.Conta;
import com.bluebank.repository.ContaRepository;
import com.bluebank.repository.ContaRepository;

@Service
public class ContaService {
	
	@Autowired
	private ContaRepository contaRepository;
	
	
	
	public List<Conta> findAll() {
		
		return contaRepository.findAll();
	}
	
	
	
	public Optional<Conta> findAll(Integer id) {
		
		return contaRepository.findById(id);
	}
	
	public void deletar(Integer id) {
		
		contaRepository.deleteById(id);
	}
	
	public void save(Conta conta) {
		contaRepository.save(conta);
	}
	
}
