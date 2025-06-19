package com.agriflux.agrifluxbatch.model.ambiente;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record DatiAmbienteRecord(BigDecimal temperaturaMedia, BigDecimal umiditaMedia, BigDecimal precipitazioni,
		BigDecimal irraggiamentoMedio, BigDecimal ombreggiamentoMedio, LocalDateTime dataRilevazione) {

}
