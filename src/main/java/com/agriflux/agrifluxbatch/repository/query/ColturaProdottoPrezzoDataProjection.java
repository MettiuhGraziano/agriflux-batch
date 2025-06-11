package com.agriflux.agrifluxbatch.repository.query;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public interface ColturaProdottoPrezzoDataProjection extends Serializable{
	
	String getProdottoColtivato();
	BigDecimal getPrezzoKg();
	Date getDataRaccolto();
}
