package com.bluebank.controller;

import java.net.URI;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.bluebank.dto.TransacaoDTO;
import com.bluebank.service.TransacaoService;

@RestController
@RequestMapping(path = "/transacoes")
public class TransacaoController {

	@Autowired
	private TransacaoService transacaoService;

	@GetMapping
	public ResponseEntity<Page<TransacaoDTO>> findAll(Pageable pageable) {
		
		return ResponseEntity.ok(transacaoService.findAll(pageable));
	}

	@GetMapping(value = "/{id}")
	public ResponseEntity<TransacaoDTO> findByTransacaoId(@PathVariable Long id) {
		
		return ResponseEntity.ok(transacaoService.findByTransacaoId(id));
	}
	
	@GetMapping(value = "/cliente/{id}")
	public ResponseEntity<Page<TransacaoDTO>> findByContaOrigemId(Pageable pageable, @PathVariable Long id) {
		
		return ResponseEntity.ok(transacaoService.findByContaOrigemId(pageable, id));
	}

	@PatchMapping(path = "/{id}")
	public ResponseEntity<TransacaoDTO> updateStatus(@PathVariable Long id, @RequestBody TransacaoDTO dto) {
		
		return ResponseEntity.ok(transacaoService.updateStatus(id, dto));
	}
	
	@PostMapping(path = "/{origemId}/{destinoId}")
	public ResponseEntity<TransacaoDTO> transferFunds(
			@PathVariable Long origemId,
			@PathVariable Long destinoId,
			@RequestBody TransacaoDTO data
			) 
	{
		TransacaoDTO dto = transacaoService.transferFunds(origemId, destinoId, data.getMontante(), data.getTipoTransacao());
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(dto.getId()).toUri();
		
		return ResponseEntity.created(uri).body(dto);
	}

}
