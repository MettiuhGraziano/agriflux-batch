package com.agriflux.agrifluxbatch.model.terreno;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record DatiRilevazioneTerrenoRecord(BigDecimal phSuolo, BigDecimal umidita, BigDecimal capacitaAssorbente,
		BigDecimal porosita, BigDecimal temperatura, LocalDateTime dataRilevazione, long idParticella) {

}
