package com.agriflux.agrifluxbatch.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "DATI_RANGE_STAGIONE")
public class RangeStagione {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long idRangeStagione;
	
	private String meseSeminaMin;
	private String meseSeminaMax;
	private String stagione;
	
	public long getIdRangeStagione() {
		return idRangeStagione;
	}
	
	public void setIdRangeStagione(long idRangeStagione) {
		this.idRangeStagione = idRangeStagione;
	}
	
	public String getMeseSeminaMin() {
		return meseSeminaMin;
	}
	
	public void setMeseSeminaMin(String meseSeminaMin) {
		this.meseSeminaMin = meseSeminaMin;
	}
	
	public String getMeseSeminaMax() {
		return meseSeminaMax;
	}
	
	public void setMeseSeminaMax(String meseSeminaMax) {
		this.meseSeminaMax = meseSeminaMax;
	}
	
	public String getStagione() {
		return stagione;
	}
	
	public void setStagione(String stagione) {
		this.stagione = stagione;
	}
	
}