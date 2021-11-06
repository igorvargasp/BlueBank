package com.bluebank.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.bluebank.entities.Transacao;

public interface TransacaoRepository extends JpaRepository<Transacao, Long> {

}
