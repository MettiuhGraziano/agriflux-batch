package com.agriflux.agrifluxbatch.entity;

import java.math.BigDecimal;
import java.time.LocalDate;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "DATI_COLTURA")
public class Coltura {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long idColtura;
	
	private BigDecimal prezzoKg;
	
	private LocalDate dataSemina;
	private LocalDate dataRaccolto;
	
	@ManyToOne
    @JoinColumn(name = "ID_PARTICELLA")
	private Particella particella;
	
	@ManyToOne
    @JoinColumn(name = "ID_ORTAGGIO")
	private Ortaggio ortaggio;
	
	public long getIdColtura() {
		return idColtura;
	}
	
	public void setIdColtura(long idColtura) {
		this.idColtura = idColtura;
	}
	
	public LocalDate getDataSemina() {
		return dataSemina;
	}
	
	public void setDataSemina(LocalDate dataSemina) {
		this.dataSemina = dataSemina;
	}
	
	public LocalDate getDataRaccolto() {
		return dataRaccolto;
	}
	
	public void setDataRaccolto(LocalDate dataRaccolto) {
		this.dataRaccolto = dataRaccolto;
	}

	public BigDecimal getPrezzoKg() {
		return prezzoKg;
	}

	public void setPrezzoKg(BigDecimal prezzoKg) {
		this.prezzoKg = prezzoKg;
	}

	public Particella getParticella() {
		return particella;
	}

	public void setParticella(Particella particella) {
		this.particella = particella;
	}

	public Ortaggio getOrtaggio() {
		return ortaggio;
	}

	public void setOrtaggio(Ortaggio ortaggio) {
		this.ortaggio = ortaggio;
	}
	
}
