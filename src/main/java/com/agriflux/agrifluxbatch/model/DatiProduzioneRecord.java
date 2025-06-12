package com.agriflux.agrifluxbatch.model;

import java.math.BigDecimal;

public record DatiProduzioneRecord(BigDecimal quantitaRaccolto, BigDecimal quantitaRaccoltoVenduto,
		BigDecimal fatturatoColtura, int numLavoratori, BigDecimal speseAccessorie, int tempoSemina, 
		int tempoGerminazione, int tempoTrapianto, int tempoMaturazione, int tempoRaccolta, 
		long idColtura, long idMorfologia) {

}
