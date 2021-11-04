package com.bluebank.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.bluebank.entities.Conta;

public interface ContaRepository extends JpaRepository<Conta, Long> {

}
