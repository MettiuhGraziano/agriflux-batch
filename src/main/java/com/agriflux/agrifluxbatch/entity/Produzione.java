package com.agriflux.agrifluxbatch.entity;

import java.math.BigDecimal;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "DATI_PRODUZIONE")
public class Produzione {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long idProduzione;
	
	private BigDecimal quantitaRaccolto;
	private BigDecimal quantitaRaccoltoVenduto;
	private BigDecimal quantitaRaccoltoMarcio;
	private BigDecimal quantitaRaccoltoTerzi;
	private BigDecimal fatturatoRaccolto;
	private BigDecimal consumoIdrico;
	
	private int numLavoratori;
	
	private BigDecimal speseProduzione;
	
	@OneToOne
    @JoinColumn(name = "ID_COLTURA")
    private Coltura coltura;

	public long getIdProduzione() {
		return idProduzione;
	}

	public void setIdProduzione(long idProduzione) {
		this.idProduzione = idProduzione;
	}

	public BigDecimal getQuantitaRaccolto() {
		return quantitaRaccolto;
	}

	public void setQuantitaRaccolto(BigDecimal quantitaRaccolto) {
		this.quantitaRaccolto = quantitaRaccolto;
	}

	public BigDecimal getQuantitaRaccoltoVenduto() {
		return quantitaRaccoltoVenduto;
	}

	public void setQuantitaRaccoltoVenduto(BigDecimal quantitaRaccoltoVenduto) {
		this.quantitaRaccoltoVenduto = quantitaRaccoltoVenduto;
	}

	public BigDecimal getQuantitaRaccoltoMarcio() {
		return quantitaRaccoltoMarcio;
	}

	public void setQuantitaRaccoltoMarcio(BigDecimal quantitaRaccoltoMarcio) {
		this.quantitaRaccoltoMarcio = quantitaRaccoltoMarcio;
	}

	public BigDecimal getQuantitaRaccoltoTerzi() {
		return quantitaRaccoltoTerzi;
	}

	public void setQuantitaRaccoltoTerzi(BigDecimal quantitaRaccoltoTerzi) {
		this.quantitaRaccoltoTerzi = quantitaRaccoltoTerzi;
	}

	public BigDecimal getFatturatoRaccolto() {
		return fatturatoRaccolto;
	}

	public void setFatturatoRaccolto(BigDecimal fatturatoRaccolto) {
		this.fatturatoRaccolto = fatturatoRaccolto;
	}

	public int getNumLavoratori() {
		return numLavoratori;
	}

	public void setNumLavoratori(int numLavoratori) {
		this.numLavoratori = numLavoratori;
	}

	public BigDecimal getSpeseProduzione() {
		return speseProduzione;
	}

	public void setSpeseProduzione(BigDecimal speseProduzione) {
		this.speseProduzione = speseProduzione;
	}

	public Coltura getColtura() {
		return coltura;
	}

	public void setColtura(Coltura coltura) {
		this.coltura = coltura;
	}

	public BigDecimal getConsumoIdrico() {
		return consumoIdrico;
	}

	public void setConsumoIdrico(BigDecimal consumoIdrico) {
		this.consumoIdrico = consumoIdrico;
	}
	
}
