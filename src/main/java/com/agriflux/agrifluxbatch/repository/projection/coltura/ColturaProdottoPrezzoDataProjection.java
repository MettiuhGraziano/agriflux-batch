package com.agriflux.agrifluxbatch.repository.projection.coltura;

import java.math.BigDecimal;
import java.time.LocalDate;

public interface ColturaProdottoPrezzoDataProjection {
	
	String getProdottoColtivato();
	BigDecimal getPrezzoKg();
	LocalDate getDataRaccolto();
}
