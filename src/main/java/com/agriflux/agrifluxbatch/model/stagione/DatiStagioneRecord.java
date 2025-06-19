package com.agriflux.agrifluxbatch.model.stagione;

public record DatiStagioneRecord(String nome, String meseGiornoInizio, String meseGiornoFine, String rangeTemperatura,
		String rangeUmidita, String rangePrecipitazioni, String rangeIrraggiamento, String rangeOmbreggiamento) {

}