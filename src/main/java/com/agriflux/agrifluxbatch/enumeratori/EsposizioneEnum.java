package com.agriflux.agrifluxbatch.enumeratori;

public enum EsposizioneEnum {
	
	N("Nord"),
	S("Sud"),
	E("Est"),
	O("Ovest");
	
	private String esposizione;
	
	EsposizioneEnum(String esposizione) {
		this.esposizione = esposizione;
	}

	public String getEsposizione() {
		return esposizione;
	}

	public void setEsposizione(String esposizione) {
		this.esposizione = esposizione;
	}

}
