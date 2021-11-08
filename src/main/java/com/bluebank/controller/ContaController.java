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

import com.bluebank.dto.ContaDTO;
import com.bluebank.dto.TransacaoDTO;
import com.bluebank.service.ContaService;

@RestController
@RequestMapping(path = "contas")
public class ContaController {

	@Autowired
	private ContaService contaService;

	@GetMapping
	public ResponseEntity<Page<ContaDTO>> findAll(Pageable pageable) {
		
		return ResponseEntity.ok(contaService.findAll(pageable));
	}
	
	@GetMapping(path = "/cliente/{id}")
	public ResponseEntity<Page<ContaDTO>> findAllByClienteId(Pageable pageable, @PathVariable Long id) {
		
		return ResponseEntity.ok(contaService.findAllByClienteId(pageable, id));
	}

	@GetMapping(value = "/{id}")
	public ResponseEntity<ContaDTO> findById(@PathVariable Long id) {
		
		return ResponseEntity.ok(contaService.findById(id));
	}

	@PostMapping
	public ResponseEntity<ContaDTO> insert(@RequestBody ContaDTO dto) {
		dto = contaService.insert(dto);
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(dto.getId()).toUri();
		
		return ResponseEntity.created(uri).body(dto);
	}

	@PatchMapping(path = "/{id}/limit")
	public ResponseEntity<ContaDTO> updateLimit(@PathVariable Long id, @RequestBody ContaDTO dto) {
		
		return ResponseEntity.ok(contaService.updateLimit(id, dto));
	}

	@PatchMapping(path = "/{id}")
	public ResponseEntity<ContaDTO> disable(@PathVariable Long id,  @RequestBody ContaDTO dto) {
		
		return ResponseEntity.ok(contaService.updateStatus(id, dto));
	}
	
	@PostMapping(path = "/{contaOrigemId}/transfer/{contaDestinoId}")
	public ResponseEntity<TransacaoDTO> transferFunds(
			@PathVariable Long contaOrigemId,
			@PathVariable Long contaDestinoId,
			@RequestBody TransacaoDTO data
			) 
	{
		TransacaoDTO dto = contaService.transferFunds(contaOrigemId, contaDestinoId, data.getMontante(), data.getTipo().getCod());
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(dto.getId()).toUri();
		
		return ResponseEntity.created(uri).body(dto);
	}
}
