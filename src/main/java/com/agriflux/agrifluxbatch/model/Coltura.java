package com.agriflux.agrifluxbatch.model;

import java.util.Date;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity(name = "DATI_COLTURA")
public class Coltura {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long idColtura;
	
	private String prodottoColtivato;
	
	private Date annoMeseSemina;
	
	private Date annoMeseRaccolto;
	
	public long getIdColtura() {
		return idColtura;
	}
	
	public void setIdColtura(long idColtura) {
		this.idColtura = idColtura;
	}
	
	public String getProdottoColtivato() {
		return prodottoColtivato;
	}
	
	public void setProdottoColtivato(String prodottoColtivato) {
		this.prodottoColtivato = prodottoColtivato;
	}
	
	public Date getAnnoMeseSemina() {
		return annoMeseSemina;
	}
	
	public void setAnnoMeseSemina(Date annoMeseSemina) {
		this.annoMeseSemina = annoMeseSemina;
	}
	
	public Date getAnnoMeseRaccolto() {
		return annoMeseRaccolto;
	}
	
	public void setAnnoMeseRaccolto(Date annoMeseRaccolto) {
		this.annoMeseRaccolto = annoMeseRaccolto;
	}
	
}
