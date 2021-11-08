package com.bluebank.entities.enums;

public enum TipoConta {
	CORRENTE(1, "Corrente"), POUPANCA(2, "Poupança"), SALARIO(3, "Salário");

	private int cod;
	private String description;

	private TipoConta(int cod, String description) {
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
	
	public static TipoConta toEnum(Integer cod) {
		if (cod == null) {
			return null;
		}
		for (TipoConta x : TipoConta.values()) {
			if (cod.equals(x.getCod())) {
				return x;
			}
		}
		throw new IllegalArgumentException("Invalid Id: " + cod);
	}
}
