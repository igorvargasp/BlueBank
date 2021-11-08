package com.bluebank.entities.enums;

public enum StatusTransacao {
	CONCLUÍDA(1, "Concluída"), PENDENTE(2, "Pendente"), CANCELADA(3, "Cancelada");

	private int cod;
	private String description;

	private StatusTransacao(int cod, String description) {
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
	
	public static StatusTransacao toEnum(Integer cod) {
		if (cod == null) {
			return null;
		}
		for (StatusTransacao x : StatusTransacao.values()) {
			if (cod.equals(x.getCod())) {
				return x;
			}
		}
		throw new IllegalArgumentException("Invalid Id: " + cod);
	}
}
