package com.agriflux.agrifluxbatch.model;

import java.io.Serializable;

public class StaticRandomMetadata implements Serializable{

	private static final long serialVersionUID = 1L;

	int temperatura; 
	int umidita; 
	int quantita_raccolto; 
	int costo_raccolto;
	
	public int getTemperatura() {
		return temperatura;
	}
	
	public void setTemperatura(int temperatura) {
		this.temperatura = temperatura;
	}
	
	public int getUmidita() {
		return umidita;
	}
	
	public void setUmidita(int umidita) {
		this.umidita = umidita;
	}
	
	public int getQuantita_raccolto() {
		return quantita_raccolto;
	}
	
	public void setQuantita_raccolto(int quantita_raccolto) {
		this.quantita_raccolto = quantita_raccolto;
	}
	
	public int getCosto_raccolto() {
		return costo_raccolto;
	}
	
	public void setCosto_raccolto(int costo_raccolto) {
		this.costo_raccolto = costo_raccolto;
	}

	@Override
	public String toString() {
		return "StaticRandomMetadata [temperatura=" + temperatura + ", umidita=" + umidita + ", quantita_raccolto="
				+ quantita_raccolto + ", costo_raccolto=" + costo_raccolto + "]";
	}
	
}
