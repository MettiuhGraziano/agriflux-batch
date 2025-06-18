package com.agriflux.agrifluxbatch.model.coltura;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record DatiColturaRecord(BigDecimal prezzoKg, LocalDateTime dataSemina, LocalDateTime dataRaccolto,
		long idParticella, long idOrtaggio) {

}