package com.agriflux.agrifluxbatch.entity;

import java.math.BigDecimal;
import java.time.LocalDate;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "DATI_AMBIENTE")
public class Ambiente {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long idAmbiente;
	
	private BigDecimal temperaturaMedia;
	private BigDecimal umiditaMedia;
	private BigDecimal precipitazioni;
	private BigDecimal irraggiamentoMedio;
	private BigDecimal ombreggiamentoMedio;
	
	private LocalDate dataRilevazione;
	
	public long getIdAmbiente() {
		return idAmbiente;
	}
	
	public void setIdAmbiente(long idAmbiente) {
		this.idAmbiente = idAmbiente;
	}
	
	public BigDecimal getTemperaturaMedia() {
		return temperaturaMedia;
	}
	
	public void setTemperaturaMedia(BigDecimal temperaturaMedia) {
		this.temperaturaMedia = temperaturaMedia;
	}
	
	public BigDecimal getUmiditaMedia() {
		return umiditaMedia;
	}
	
	public void setUmiditaMedia(BigDecimal umiditaMedia) {
		this.umiditaMedia = umiditaMedia;
	}
	
	public BigDecimal getPrecipitazioni() {
		return precipitazioni;
	}
	
	public void setPrecipitazioni(BigDecimal precipitazioni) {
		this.precipitazioni = precipitazioni;
	}
	
	public BigDecimal getIrraggiamentoMedio() {
		return irraggiamentoMedio;
	}
	
	public void setIrraggiamentoMedio(BigDecimal irraggiamentoMedio) {
		this.irraggiamentoMedio = irraggiamentoMedio;
	}
	
	public BigDecimal getOmbreggiamentoMedio() {
		return ombreggiamentoMedio;
	}
	
	public void setOmbreggiamentoMedio(BigDecimal ombreggiamentoMedio) {
		this.ombreggiamentoMedio = ombreggiamentoMedio;
	}
	
	public LocalDate getDataRilevazione() {
		return dataRilevazione;
	}
	
	public void setDataRilevazione(LocalDate dataRilevazione) {
		this.dataRilevazione = dataRilevazione;
	}
	
}
