package com.agriflux.agrifluxbatch.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "DATI_LITOLOGIA")
public class Litologia {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long idLitologia;
	
	private String tipologia;
	private String descrizione;
	
	public long getIdLitologia() {
		return idLitologia;
	}
	
	public void setIdLitologia(long idLitologia) {
		this.idLitologia = idLitologia;
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
