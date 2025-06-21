package com.agriflux.agrifluxbatch.model.terreno;

import java.math.BigDecimal;
import java.time.LocalDate;

public record DatiRilevazioneTerrenoRecord(BigDecimal phSuolo, BigDecimal umidita, BigDecimal capacitaAssorbente,
		BigDecimal porosita, BigDecimal temperatura, LocalDate dataRilevazione, long idParticella) {

}
