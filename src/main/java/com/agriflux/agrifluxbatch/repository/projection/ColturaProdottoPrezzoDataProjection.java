package com.agriflux.agrifluxbatch.repository.projection;

import java.math.BigDecimal;
import java.util.Date;

public interface ColturaProdottoPrezzoDataProjection {
	
	String getProdottoColtivato();
	BigDecimal getPrezzoKg();
	Date getDataRaccolto();
}
