package com.agriflux.agrifluxbatch.entity;

import java.math.BigDecimal;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "DATI_PARTICELLA")
public class Particella {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long idParticella;
	
	private String comune;
	private String foglio;
	private String qualita;
	private String annoInstallazione;
	private String esposizione; 
	
	private BigDecimal costo;
	private BigDecimal estensione;
	private BigDecimal pendenza;
	
	private long idLitologia;

	public long getIdParticella() {
		return idParticella;
	}

	public void setIdParticella(long idParticella) {
		this.idParticella = idParticella;
	}

	public String getComune() {
		return comune;
	}

	public void setComune(String comune) {
		this.comune = comune;
	}

	public String getFoglio() {
		return foglio;
	}

	public void setFoglio(String foglio) {
		this.foglio = foglio;
	}

	public String getQualita() {
		return qualita;
	}

	public void setQualita(String qualita) {
		this.qualita = qualita;
	}

	public String getAnnoInstallazione() {
		return annoInstallazione;
	}

	public void setAnnoInstallazione(String annoInstallazione) {
		this.annoInstallazione = annoInstallazione;
	}

	public String getEsposizione() {
		return esposizione;
	}

	public void setEsposizione(String esposizione) {
		this.esposizione = esposizione;
	}

	public BigDecimal getCosto() {
		return costo;
	}

	public void setCosto(BigDecimal costo) {
		this.costo = costo;
	}

	public BigDecimal getEstensione() {
		return estensione;
	}

	public void setEstensione(BigDecimal estensione) {
		this.estensione = estensione;
	}

	public BigDecimal getPendenza() {
		return pendenza;
	}

	public void setPendenza(BigDecimal pendenza) {
		this.pendenza = pendenza;
	}

	public long getIdLitologia() {
		return idLitologia;
	}

	public void setIdLitologia(long idLitologia) {
		this.idLitologia = idLitologia;
	}
	
}
