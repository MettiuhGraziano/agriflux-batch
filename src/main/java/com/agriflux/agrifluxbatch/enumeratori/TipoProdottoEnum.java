package com.agriflux.agrifluxbatch.enumeratori;

public enum TipoProdottoEnum {

	POMODORO(1),
	LATTUGA(2),
	FAGIOLO(3),
	ZUCCHINA(4),
	PORRO(5),
	MELANZANA(6);
	
	private int codiceProdotto;
	
	TipoProdottoEnum(int codiceProdotto) {
		this.codiceProdotto = codiceProdotto;
	}

	public int getCodiceProdotto() {
		return codiceProdotto;
	}

	public void setCodiceProdotto(int codiceProdotto) {
		this.codiceProdotto = codiceProdotto;
	}
	
}
