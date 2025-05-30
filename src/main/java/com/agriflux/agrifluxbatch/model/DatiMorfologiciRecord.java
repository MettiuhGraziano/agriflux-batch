package com.agriflux.agrifluxbatch.model;

import java.math.BigDecimal;

public record DatiMorfologiciRecord(BigDecimal estensioneTerreno, BigDecimal pendenza, String esposizione,
		String litologia, long fkIdColtura) {

}
