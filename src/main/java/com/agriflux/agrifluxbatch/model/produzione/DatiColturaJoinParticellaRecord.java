package com.agriflux.agrifluxbatch.model.produzione;

import java.math.BigDecimal;

public record DatiColturaJoinParticellaRecord(long idColtura, BigDecimal prezzoKg, BigDecimal estensione,
		BigDecimal pesoMedio, BigDecimal volumeMq) {

}
