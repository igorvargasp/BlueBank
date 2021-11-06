package com.bluebank.dto;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TransacaoDTO implements Serializable {
	private static final long serialVersionUID = 1L;

	@EqualsAndHashCode.Include
	private Long id;
	private Double montante;
	private String tipoTransacao;
	private String status;
	private Long origem_id;
	private Long destino_id;
}
