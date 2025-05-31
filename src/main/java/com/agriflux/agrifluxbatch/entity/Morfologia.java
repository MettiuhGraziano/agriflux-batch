package com.agriflux.agrifluxbatch.entity;

import java.math.BigDecimal;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity(name = "DATI_MORFOLOGICI")
public class Morfologia {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long idMorfologia;
	
	private BigDecimal estensioneTerreno;
	
	private int pendenza;
	
	private String esposizione;
	
	private String litologia;

	public long getIdMorfologia() {
		return idMorfologia;
	}

	public void setIdMorfologia(long idMorfologia) {
		this.idMorfologia = idMorfologia;
	}

	public BigDecimal getEstensioneTerreno() {
		return estensioneTerreno;
	}

	public void setEstensioneTerreno(BigDecimal estensioneTerreno) {
		this.estensioneTerreno = estensioneTerreno;
	}

	public int getPendenza() {
		return pendenza;
	}

	public void setPendenza(int pendenza) {
		this.pendenza = pendenza;
	}

	public String getEsposizione() {
		return esposizione;
	}

	public void setEsposizione(String esposizione) {
		this.esposizione = esposizione;
	}

	public String getLitologia() {
		return litologia;
	}

	public void setLitologia(String litologia) {
		this.litologia = litologia;
	}
	
}
