package com.agriflux.agrifluxbatch.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "DATI_AZIENDA")
public class Azienda {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long idAzienda;
	
	private String ragioneSociale;
	private String annoFondazione;
	private String tipologia;
	private String ateco;
	
	private long numDipendenti;

	public long getIdAzienda() {
		return idAzienda;
	}

	public void setIdAzienda(long idAzienda) {
		this.idAzienda = idAzienda;
	}

	public String getRagioneSociale() {
		return ragioneSociale;
	}

	public void setRagioneSociale(String ragioneSociale) {
		this.ragioneSociale = ragioneSociale;
	}

	public String getAnnoFondazione() {
		return annoFondazione;
	}

	public void setAnnoFondazione(String annoFondazione) {
		this.annoFondazione = annoFondazione;
	}

	public String getTipologia() {
		return tipologia;
	}

	public void setTipologia(String tipologia) {
		this.tipologia = tipologia;
	}

	public String getAteco() {
		return ateco;
	}

	public void setAteco(String ateco) {
		this.ateco = ateco;
	}

	public long getNumDipendenti() {
		return numDipendenti;
	}

	public void setNumDipendenti(long numDipendenti) {
		this.numDipendenti = numDipendenti;
	}

}
