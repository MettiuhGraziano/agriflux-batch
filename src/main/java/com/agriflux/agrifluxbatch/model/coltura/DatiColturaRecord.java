package com.agriflux.agrifluxbatch.model.coltura;

import java.math.BigDecimal;
import java.time.LocalDate;

public record DatiColturaRecord(BigDecimal prezzoKg, LocalDate dataSemina, LocalDate dataRaccolto,
		long idParticella, long idOrtaggio) {

}