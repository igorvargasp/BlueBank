package com.bluebank.entities.enums;

public enum TipoTransacao {
	TED(1, "TED"), DOC(2, "DOC"), PIX(3, "PIX"), DEPOSITO(4, "Depósito");

	private int cod;
	private String description;

	private TipoTransacao(int cod, String description) {
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
	
	public static TipoTransacao toEnum(Integer cod) {
		if (cod == null) {
			return null;
		}
		for (TipoTransacao x : TipoTransacao.values()) {
			if (cod.equals(x.getCod())) {
				return x;
			}
		}
		throw new IllegalArgumentException("Invalid Id: " + cod);
	}
}
