package com.agriflux.agrifluxbatch.model.fatturato;

import java.math.BigDecimal;

public record DatiFatturatoRecord(String annoRiferimento, BigDecimal ricaviVendita, BigDecimal speseGenerali, 
		BigDecimal interessiAttivi, BigDecimal interessiPassivi, long idParticella) {

}