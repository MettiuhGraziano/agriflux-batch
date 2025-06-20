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
@Table(name = "DATI_ECONOMICI_NAZIONALI")
public class DatoEconomico {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long idEconomia;
	
	private String annoRiferimento;
	
	private BigDecimal produzioneMedia;
	private BigDecimal prezzoVenditaMedio;
	
	@ManyToOne
    @JoinColumn(name = "ID_ORTAGGIO")
	private Ortaggio ortaggio;

	public long getIdEconomia() {
		return idEconomia;
	}

	public void setIdEconomia(long idEconomia) {
		this.idEconomia = idEconomia;
	}

	public String getAnnoRiferimento() {
		return annoRiferimento;
	}

	public void setAnnoRiferimento(String annoRiferimento) {
		this.annoRiferimento = annoRiferimento;
	}

	public BigDecimal getProduzioneMedia() {
		return produzioneMedia;
	}

	public void setProduzioneMedia(BigDecimal produzioneMedia) {
		this.produzioneMedia = produzioneMedia;
	}

	public BigDecimal getPrezzoVenditaMedio() {
		return prezzoVenditaMedio;
	}

	public void setPrezzoVenditaMedio(BigDecimal prezzoVenditaMedio) {
		this.prezzoVenditaMedio = prezzoVenditaMedio;
	}

	public Ortaggio getOrtaggio() {
		return ortaggio;
	}

	public void setOrtaggio(Ortaggio ortaggio) {
		this.ortaggio = ortaggio;
	}
	
}