package com.agriflux.agrifluxbatch.enumeratori;

public enum TipoProdottoEnum {

	POMODORO(1, 0.12, 2.5),
	LATTUGA(2, 0.30, 20.5),
	FAGIOLO(3, 0.0005, 25.0),
	ZUCCHINA(4, 0.20, 1.5),
	PORRO(5, 0.15, 12.5),
	MELANZANA(6, 0.30, 1.5),
	CAROTA(7, 0.075, 15.5),
	BARBABIETOLA(8, 0.12, 2.25),
	ZUCCA(9, 1.5, 1.5);
	
	private int codiceProdotto;
	private double pesoMedio;
	private double volumeXMetroQuadro;
	
	TipoProdottoEnum(int codiceProdotto, double pesoMedio, double volumeXMetroQuadro) {
		this.codiceProdotto = codiceProdotto;
		this.pesoMedio = pesoMedio;
		this.volumeXMetroQuadro = volumeXMetroQuadro;
	}

	public int getCodiceProdotto() {
		return codiceProdotto;
	}

	public void setCodiceProdotto(int codiceProdotto) {
		this.codiceProdotto = codiceProdotto;
	}
	
	public double getPesoMedio() {
		return pesoMedio;
	}
	
	public void setPesoMedio(double pesoMedio) {
		this.pesoMedio = pesoMedio;
	}
	
	public double getVolumeXMetroQuadro() {
		return volumeXMetroQuadro;
	}

	public void setVolumeXMetroQuadro(double volumeXMetroQuadro) {
		this.volumeXMetroQuadro = volumeXMetroQuadro;
	}
	
}
