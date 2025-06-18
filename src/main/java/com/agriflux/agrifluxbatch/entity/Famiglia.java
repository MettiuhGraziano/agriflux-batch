package com.agriflux.agrifluxbatch.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "DATI_FAMIGLIA")
public class Famiglia {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long idFamiglia;
	
	private String tipologia;
	private String descrizione;
	
	public long getIdFamiglia() {
		return idFamiglia;
	}
	
	public void setIdFamiglia(long idFamiglia) {
		this.idFamiglia = idFamiglia;
	}
	
	public String getTipologia() {
		return tipologia;
	}
	
	public void setTipologia(String tipologia) {
		this.tipologia = tipologia;
	}
	
	public String getDescrizione() {
		return descrizione;
	}
	
	public void setDescrizione(String descrizione) {
		this.descrizione = descrizione;
	}
	
}
