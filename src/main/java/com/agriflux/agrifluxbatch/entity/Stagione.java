package com.agriflux.agrifluxbatch.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "DATI_STAGIONE")
public class Stagione {
	
	@Id
	private String nome;
	
	private String meseGiornoInizio;
	private String meseGiornoFine;
	private String rangeTemperatura;
	private String rangeUmidita;
	private String rangePrecipitazioni;
	private String rangeIrraggiamento;
	private String rangeOmbreggiamento;
	
	public String getNome() {
		return nome;
	}
	
	public void setNome(String nome) {
		this.nome = nome;
	}
	
	public String getRangeTemperatura() {
		return rangeTemperatura;
	}
	
	public void setRangeTemperatura(String rangeTemperatura) {
		this.rangeTemperatura = rangeTemperatura;
	}
	
	public String getRangeUmidita() {
		return rangeUmidita;
	}
	
	public void setRangeUmidita(String rangeUmidita) {
		this.rangeUmidita = rangeUmidita;
	}
	
	public String getRangePrecipitazioni() {
		return rangePrecipitazioni;
	}
	
	public void setRangePrecipitazioni(String rangePrecipitazioni) {
		this.rangePrecipitazioni = rangePrecipitazioni;
	}
	
	public String getRangeIrraggiamento() {
		return rangeIrraggiamento;
	}
	
	public void setRangeIrraggiamento(String rangeIrraggiamento) {
		this.rangeIrraggiamento = rangeIrraggiamento;
	}
	
	public String getRangeOmbreggiamento() {
		return rangeOmbreggiamento;
	}
	
	public void setRangeOmbreggiamento(String rangeOmbreggiamento) {
		this.rangeOmbreggiamento = rangeOmbreggiamento;
	}

	public String getMeseGiornoInizio() {
		return meseGiornoInizio;
	}

	public void setMeseGiornoInizio(String meseGiornoInizio) {
		this.meseGiornoInizio = meseGiornoInizio;
	}

	public String getMeseGiornoFine() {
		return meseGiornoFine;
	}

	public void setMeseGiornoFine(String meseGiornoFine) {
		this.meseGiornoFine = meseGiornoFine;
	}
	
}
