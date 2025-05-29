package com.agriflux.agrifluxbatch.model;

import java.math.BigDecimal;
import java.util.Date;

public record DatiAmbientaliRecord(BigDecimal temperaturaMedia, BigDecimal umiditaMedia, BigDecimal precipitazioni,
		BigDecimal irraggiamentoMedio, BigDecimal ombreggiamentoMedio, Date annoMeseRilevazione, long fkIdColtura) {

}
