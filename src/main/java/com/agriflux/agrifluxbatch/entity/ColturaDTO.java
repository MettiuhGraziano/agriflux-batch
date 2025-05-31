package com.agriflux.agrifluxbatch.entity;

import java.math.BigDecimal;
import java.util.Date;

public class ColturaDTO {
	
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
	
	public BigDecimal getPrezzoKg() {
		return prezzoKg;
	}
	
	public void setPrezzoKg(BigDecimal prezzoKg) {
		this.prezzoKg = prezzoKg;
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
	
}
