package com.bluebank.entities;

import java.io.Serializable;
import java.time.Instant;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import com.bluebank.entities.enums.StatusConta;
import com.bluebank.entities.enums.TipoConta;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "tb_conta")
@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Conta implements Serializable {
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@EqualsAndHashCode.Include
	private Long id;
	private String conta;
	private String agencia;
	private Double saldo;
	private Double limiteCredito;
	private Integer tipo;
	private Integer status;
	
	@CreatedDate
	@Column(columnDefinition = "TIMESTAMP WITHOUT TIME ZONE")
	private Instant criadoEm;
	
	@LastModifiedDate
	@Column(columnDefinition = "TIMESTAMP WITHOUT TIME ZONE")
	private Instant atualizadoEm;

	@ManyToOne
	@JoinColumn(name = "cliente_id")
	private Cliente cliente;
	
	public Instant getAtualizadoEm() {
		if (this.atualizadoEm == null) {
			this.atualizadoEm = this.criadoEm;
		}
		
		return this.atualizadoEm;
	}
	
	public TipoConta getTipo() {
		return TipoConta.toEnum(tipo);
	}

	public void setTipo(TipoConta tipo) {
		if(tipo != null) {
		this.tipo = tipo.getCod();
		}
	}
	
	public StatusConta getStatus() {
		return StatusConta.toEnum(status);
	}
	
	public void setStatus(StatusConta status) {
		if(status != null) {
			this.status = status.getCod();
		}
	}
}
