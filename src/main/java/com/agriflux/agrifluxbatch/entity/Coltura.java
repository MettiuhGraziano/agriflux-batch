package com.agriflux.agrifluxbatch.entity;

import java.math.BigDecimal;
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
	
	private BigDecimal prezzoKg;
	
	private Date dataSemina;
	
	private Date dataRaccolto;
	
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
	
	public Date getDataSemina() {
		return dataSemina;
	}
	
	public void setDataSemina(Date dataSemina) {
		this.dataSemina = dataSemina;
	}
	
	public Date getDataRaccolto() {
		return dataRaccolto;
	}
	
	public void setDataRaccolto(Date dataRaccolto) {
		this.dataRaccolto = dataRaccolto;
	}

	public BigDecimal getPrezzoKg() {
		return prezzoKg;
	}

	public void setPrezzoKg(BigDecimal prezzoKg) {
		this.prezzoKg = prezzoKg;
	}
	
}
