package com.agriflux.agrifluxbatch.model.coltura;

import java.math.BigDecimal;
import java.util.Date;

public record ColturaRecord(String prodottoColtivato, BigDecimal prezzoKg, Date dataSemina, Date dataRaccolto) {

}
