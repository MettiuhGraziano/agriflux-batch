package com.agriflux.agrifluxbatch.model.ambiente;

import java.math.BigDecimal;
import java.time.LocalDate;

public record DatiAmbienteRecord(BigDecimal temperaturaMedia, BigDecimal umiditaMedia, BigDecimal precipitazioni,
		BigDecimal irraggiamentoMedio, BigDecimal ombreggiamentoMedio, LocalDate dataRilevazione) {

}
