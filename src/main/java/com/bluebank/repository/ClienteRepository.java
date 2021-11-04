package com.bluebank.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.bluebank.entities.Cliente;

public interface ClienteRepository extends JpaRepository<Cliente, Long> {

}
