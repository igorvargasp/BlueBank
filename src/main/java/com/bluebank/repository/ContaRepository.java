package com.bluebank.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.bluebank.entities.Conta;

public interface ContaRepository extends JpaRepository<Conta, Long> {

	Page<Conta> findAllByClienteId(Pageable pageable, Long id);
}
