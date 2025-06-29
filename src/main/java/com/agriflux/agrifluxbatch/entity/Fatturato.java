package com.agriflux.agrifluxbatch.entity;

import java.math.BigDecimal;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "DATI_FATTURATO")
public class Fatturato {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long idFatturato;
	
	private String annoRiferimento;
	
	private BigDecimal ricaviVendita;
	private BigDecimal speseGenerali;
	private BigDecimal interessiAttivi;
	private BigDecimal interessiPassivi;
	
	@ManyToOne
    @JoinColumn(name = "ID_PARTICELLA")
	private Particella particella;

	public long getIdFatturato() {
		return idFatturato;
	}

	public void setIdFatturato(long idFatturato) {
		this.idFatturato = idFatturato;
	}

	public String getAnnoRiferimento() {
		return annoRiferimento;
	}

	public void setAnnoRiferimento(String annoRiferimento) {
		this.annoRiferimento = annoRiferimento;
	}

	public BigDecimal getRicaviVendita() {
		return ricaviVendita;
	}

	public void setRicaviVendita(BigDecimal ricaviVendita) {
		this.ricaviVendita = ricaviVendita;
	}

	public BigDecimal getSpeseGenerali() {
		return speseGenerali;
	}

	public void setSpeseGenerali(BigDecimal speseGenerali) {
		this.speseGenerali = speseGenerali;
	}

	public BigDecimal getInteressiAttivi() {
		return interessiAttivi;
	}

	public void setInteressiAttivi(BigDecimal interessiAttivi) {
		this.interessiAttivi = interessiAttivi;
	}

	public BigDecimal getInteressiPassivi() {
		return interessiPassivi;
	}

	public void setInteressiPassivi(BigDecimal interessiPassivi) {
		this.interessiPassivi = interessiPassivi;
	}

	public Particella getParticella() {
		return particella;
	}

	public void setParticella(Particella particella) {
		this.particella = particella;
	}
	
}
