package com.agriflux.agrifluxbatch.model;

public class Coltura {
	
	private int id;
	
	private String prodottoColtivato;
	
	private String annoMeseSemina;
	
	private String annoMeseRaccolto;
	
	public int getId() {
		return id;
	}
	
	public void setId(int id) {
		this.id = id;
	}
	
	public String getProdottoColtivato() {
		return prodottoColtivato;
	}
	
	public void setProdottoColtivato(String prodottoColtivato) {
		this.prodottoColtivato = prodottoColtivato;
	}
	
	public String getAnnoMeseSemina() {
		return annoMeseSemina;
	}
	
	public void setAnnoMeseSemina(String annoMeseSemina) {
		this.annoMeseSemina = annoMeseSemina;
	}
	
	public String getAnnoMeseRaccolto() {
		return annoMeseRaccolto;
	}
	
	public void setAnnoMeseRaccolto(String annoMeseRaccolto) {
		this.annoMeseRaccolto = annoMeseRaccolto;
	}
	
}
