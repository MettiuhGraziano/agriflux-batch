package com.agriflux.agrifluxbatch.enumeratori;

public enum LitologiaTerrenoEnum {
	
	SABBIOSO(1, "Terreno Sabbioso"),
	ARGILLOSO(2, "Terreno Argilloso"),
	LIMOSO(3, "Terreno Limoso"),
	TORBOSO(4, "Terreno Torboso"),
	SALINO(5, "Terreno Salino"),
	GESSOSO(6, "Terreno Gessoso"),
	ROCCIOSO(7, "Terreno con Roccia"),
	MISTO(8, "Terreno Misto");
	
	private int codiceLitologico;
	private String nomeTerreno;
	
	LitologiaTerrenoEnum(int codiceLitologico, String nomeTerreno) {
		this.codiceLitologico = codiceLitologico;
		this.nomeTerreno = nomeTerreno;
	}

	public int getCodiceLitologico() {
		return codiceLitologico;
	}

	public void setCodiceLitologico(int codiceLitologico) {
		this.codiceLitologico = codiceLitologico;
	}

	public String getNomeTerreno() {
		return nomeTerreno;
	}

	public void setNomeTerreno(String nomeTerreno) {
		this.nomeTerreno = nomeTerreno;
	}
}
