package com.agriflux.agrifluxbatch.entity;

import java.math.BigDecimal;
import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "DATI_ORTAGGIO")
public class Ortaggio {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long idOrtaggio;
	
	private String nome;
	private String descrizione;
	private int giorniSemina;
	private int giorniGerminazione;
	private int giorniTrapianto;
	private int giorniMaturazione;
	private int giorniRaccolta;
	
	private BigDecimal pesoMedio;
	private BigDecimal volumeMq;
	
	@OneToOne
    @JoinColumn(name = "ID_FAMIGLIA")
	private Famiglia famiglia;
	
	@OneToOne
    @JoinColumn(name = "ID_RANGE_STAGIONE_SEMINA")
	private RangeStagioneSemina rangeStagioneSemina;
	
	@OneToMany
	private List<Coltura> colture;

	public List<Coltura> getColture() {
		return colture;
	}

	public void setColture(List<Coltura> colture) {
		this.colture = colture;
	}

	public long getIdOrtaggio() {
		return idOrtaggio;
	}

	public void setIdOrtaggio(long idOrtaggio) {
		this.idOrtaggio = idOrtaggio;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getDescrizione() {
		return descrizione;
	}

	public void setDescrizione(String descrizione) {
		this.descrizione = descrizione;
	}

	public int getGiorniSemina() {
		return giorniSemina;
	}

	public void setGiorniSemina(int giorniSemina) {
		this.giorniSemina = giorniSemina;
	}

	public int getGiorniGerminazione() {
		return giorniGerminazione;
	}

	public void setGiorniGerminazione(int giorniGerminazione) {
		this.giorniGerminazione = giorniGerminazione;
	}

	public int getGiorniTrapianto() {
		return giorniTrapianto;
	}

	public void setGiorniTrapianto(int giorniTrapianto) {
		this.giorniTrapianto = giorniTrapianto;
	}

	public int getGiorniMaturazione() {
		return giorniMaturazione;
	}

	public void setGiorniMaturazione(int giorniMaturazione) {
		this.giorniMaturazione = giorniMaturazione;
	}

	public int getGiorniRaccolta() {
		return giorniRaccolta;
	}

	public void setGiorniRaccolta(int giorniRaccolta) {
		this.giorniRaccolta = giorniRaccolta;
	}

	public BigDecimal getPesoMedio() {
		return pesoMedio;
	}

	public void setPesoMedio(BigDecimal pesoMedio) {
		this.pesoMedio = pesoMedio;
	}

	public BigDecimal getVolumeMq() {
		return volumeMq;
	}

	public void setVolumeMq(BigDecimal volumeMq) {
		this.volumeMq = volumeMq;
	}

	public Famiglia getFamiglia() {
		return famiglia;
	}

	public void setFamiglia(Famiglia famiglia) {
		this.famiglia = famiglia;
	}

	public RangeStagioneSemina getRangeStagioneSemina() {
		return rangeStagioneSemina;
	}

	public void setRangeStagioneSemina(RangeStagioneSemina rangeStagioneSemina) {
		this.rangeStagioneSemina = rangeStagioneSemina;
	}
	
}