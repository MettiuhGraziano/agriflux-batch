package com.agriflux.agrifluxbatch.entity;

import java.math.BigDecimal;
import java.util.Date;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "DATI_TERRENO")
public class Terreno {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long idRilevazioneTerreno;
	
	private BigDecimal phSuolo;
	private BigDecimal umidita;
	private BigDecimal capacitaAssorbente;
	private BigDecimal porosita;
	private BigDecimal temperatura;
	
	private int disponibilitaIrrigua;
	
	private Date dataRilevazione;
	
	private long fkIdColtura;
	private long fkIdMorfologia;
	
	public long getIdRilevazioneTerreno() {
		return idRilevazioneTerreno;
	}
	
	public void setIdRilevazioneTerreno(long idRilevazioneTerreno) {
		this.idRilevazioneTerreno = idRilevazioneTerreno;
	}
	
	public BigDecimal getPhSuolo() {
		return phSuolo;
	}
	
	public void setPhSuolo(BigDecimal phSuolo) {
		this.phSuolo = phSuolo;
	}
	
	public BigDecimal getUmidita() {
		return umidita;
	}
	
	public void setUmidita(BigDecimal umidita) {
		this.umidita = umidita;
	}
	
	public BigDecimal getCapacitaAssorbente() {
		return capacitaAssorbente;
	}
	
	public void setCapacitaAssorbente(BigDecimal capacitaAssorbente) {
		this.capacitaAssorbente = capacitaAssorbente;
	}
	
	public BigDecimal getPorosita() {
		return porosita;
	}
	
	public void setPorosita(BigDecimal porosita) {
		this.porosita = porosita;
	}
	
	public BigDecimal getTemperatura() {
		return temperatura;
	}
	
	public void setTemperatura(BigDecimal temperatura) {
		this.temperatura = temperatura;
	}
	
	public int getDisponibilitaIrrigua() {
		return disponibilitaIrrigua;
	}
	
	public void setDisponibilitaIrrigua(int disponibilitaIrrigua) {
		this.disponibilitaIrrigua = disponibilitaIrrigua;
	}
	
	public Date getDataRilevazione() {
		return dataRilevazione;
	}
	
	public void setDataRilevazione(Date dataRilevazione) {
		this.dataRilevazione = dataRilevazione;
	}
	
	public long getFkIdColtura() {
		return fkIdColtura;
	}
	
	public void setFkIdColtura(long fkIdColtura) {
		this.fkIdColtura = fkIdColtura;
	}
	
	public long getFkIdMorfologia() {
		return fkIdMorfologia;
	}
	
	public void setFkIdMorfologia(long fkIdMorfologia) {
		this.fkIdMorfologia = fkIdMorfologia;
	}
	
}
