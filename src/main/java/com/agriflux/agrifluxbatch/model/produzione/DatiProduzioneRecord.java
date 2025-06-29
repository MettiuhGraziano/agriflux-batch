package com.agriflux.agrifluxbatch.model.produzione;

import java.math.BigDecimal;

public record DatiProduzioneRecord(BigDecimal quantitaRaccolto, BigDecimal quantitaRaccoltoVenduto,
		BigDecimal quantitaRaccoltoMarcio, BigDecimal quantitaRaccoltoTerzi, BigDecimal fatturatoRaccolto,
		BigDecimal consumoIdrico, int numLavoratori, BigDecimal speseProduzione, long idColtura) {

}
