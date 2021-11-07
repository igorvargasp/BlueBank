package com.bluebank.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.bluebank.entities.Transacao;

public interface TransacaoRepository extends JpaRepository<Transacao, Long> {

	Page<Transacao> findByContaOrigemId(Pageable pageable, Long id);
}
