package com.agriflux.agrifluxbatch.entity;

import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "DATI_LITOLOGIA")
public class Litologia {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long idLitologia;
	
	private String tipologia;
	private String descrizione;
	
	@OneToMany( mappedBy = "litologia")
	private List<Particella> particelle;
	
	public long getIdLitologia() {
		return idLitologia;
	}
	
	public void setIdLitologia(long idLitologia) {
		this.idLitologia = idLitologia;
	}
	
	public String getTipologia() {
		return tipologia;
	}
	
	public void setTipologia(String tipologia) {
		this.tipologia = tipologia;
	}
	
	public String getDescrizione() {
		return descrizione;
	}
	
	public void setDescrizione(String descrizione) {
		this.descrizione = descrizione;
	}
	
	public List<Particella> getParticelle() {
		return particelle;
	}

	public void setParticelle(List<Particella> particelle) {
		this.particelle = particelle;
	}
	
}
