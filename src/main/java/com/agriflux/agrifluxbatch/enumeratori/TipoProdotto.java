package com.agriflux.agrifluxbatch.enumeratori;

public enum TipoProdotto {

	POMODORO(1),
	LATTUGA(2),
	FAGIOLO(3),
	ZUCCHINA(4),
	PORRO(5),
	MELANZANA(6);
	
	private int codiceProdotto;
	
	private TipoProdotto(int codiceProdotto) {
		this.codiceProdotto = codiceProdotto;
	}

	public int getCodiceProdotto() {
		return codiceProdotto;
	}

	public void setCodiceProdotto(int codiceProdotto) {
		this.codiceProdotto = codiceProdotto;
	}
	
}
