package com.agriflux.agrifluxbatch.model;

import java.io.Serializable;

public class StaticMetadata implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	String temperatura; 
	String umidita; 
	String quantita_raccolto; 
	String costo_raccolto;
	
	public String getTemperatura() {
		return temperatura;
	}
	
	public void setTemperatura(String temperatura) {
		this.temperatura = temperatura;
	}
	
	public String getUmidita() {
		return umidita;
	}
	
	public void setUmidita(String umidita) {
		this.umidita = umidita;
	}
	
	public String getQuantita_raccolto() {
		return quantita_raccolto;
	}
	
	public void setQuantita_raccolto(String quantita_raccolto) {
		this.quantita_raccolto = quantita_raccolto;
	}
	
	public String getCosto_raccolto() {
		return costo_raccolto;
	}
	
	public void setCosto_raccolto(String costo_raccolto) {
		this.costo_raccolto = costo_raccolto;
	}

	@Override
	public String toString() {
		return "StaticMetadata [temperatura=" + temperatura + ", umidita=" + umidita + ", quantita_raccolto="
				+ quantita_raccolto + ", costo_raccolto=" + costo_raccolto + "]";
	}
	
}
