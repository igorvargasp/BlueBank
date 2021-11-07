package com.bluebank.entities;

import java.io.Serializable;
import java.time.Instant;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "tb_transacao")
@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Transacao implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@EqualsAndHashCode.Include
	private Long id;
	private Double montante;
	private String tipoTransacao;
	private String status;
	
	@ManyToOne(cascade = CascadeType.PERSIST)
	private Conta contaOrigem;
	
	@ManyToOne(cascade = CascadeType.PERSIST)
	private Conta contaDestino;
	
	@CreatedDate
	@Column(columnDefinition = "TIMESTAMP WITHOUT TIME ZONE")
	private Instant criadoEm;
	
	@LastModifiedDate
	@Column(columnDefinition = "TIMESTAMP WITHOUT TIME ZONE")
	private Instant atualizadoEm;
	
	public Instant getAtualizadoEm() {
		if (this.atualizadoEm == null) {
			return this.criadoEm;
		}
		
		return this.atualizadoEm;
	}
}
