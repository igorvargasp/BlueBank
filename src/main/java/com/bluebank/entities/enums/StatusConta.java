package com.bluebank.entities.enums;

public enum StatusConta {
	ATIVA(1, "Ativa"), BLOQUEADA(2, "Bloqueada"), DESATIVADA(3, "Desativada");

	private int cod;
	private String description;

	private StatusConta(int cod, String description) {
		this.cod = cod;
	}

	public int getCod() {
		return cod;
	}

	public void setCod(int cod) {
		this.cod = cod;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
	
	public static StatusConta toEnum(Integer cod) {
		if (cod == null) {
			return null;
		}
		for (StatusConta x : StatusConta.values()) {
			if (cod.equals(x.getCod())) {
				return x;
			}
		}
		throw new IllegalArgumentException("Invalid Id: " + cod);
	}
}
