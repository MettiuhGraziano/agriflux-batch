package com.agriflux.agrifluxbatch.model.terreno;

import java.math.BigDecimal;
import java.util.Date;

public record DatiTerrenoRecord(BigDecimal phSuolo, BigDecimal umidita, BigDecimal capacitaAssorbente,
		BigDecimal porosita, BigDecimal temperatura, int disponibilitaIrrigua,
		Date dataRilevazione, long idColtura, long idMorfologia) {

}
