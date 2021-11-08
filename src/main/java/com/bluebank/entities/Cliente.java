package com.bluebank.entities;

import java.io.Serializable;
import java.time.Instant;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import com.bluebank.entities.enums.TipoCliente;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "tb_cliente")
@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Cliente implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@EqualsAndHashCode.Include
	private Long id;
	private String cpf;
	private String nomeCompleto;
	private LocalDate dataNascimento;
	private Integer tipo;
	private String email;
	private String telefone;
	private Double rendaMensal;
	
	@CreatedDate
	@Column(columnDefinition = "TIMESTAMP WITHOUT TIME ZONE")
	private Instant criadoEm;
	
	@LastModifiedDate
	@Column(columnDefinition = "TIMESTAMP WITHOUT TIME ZONE")
	private Instant atualizadoEm;
	
	@OneToMany(mappedBy = "cliente", fetch = FetchType.LAZY)
	private Set<Conta> contas;

	public Set<Conta> getContas() {
		if (contas == null) {
			return new HashSet<>();
		}
		
		return contas;
	}
	
	public Instant getAtualizadoEm() {
		if (this.atualizadoEm == null) {
			this.atualizadoEm = this.criadoEm;
		}
		
		return this.atualizadoEm;
	}
	
	public TipoCliente getTipo() {
		return TipoCliente.toEnum(tipo);
	}

	public void setTipo(TipoCliente tipo) {
		if(tipo != null) {
		this.tipo = tipo.getCod();
		}
	}
}
