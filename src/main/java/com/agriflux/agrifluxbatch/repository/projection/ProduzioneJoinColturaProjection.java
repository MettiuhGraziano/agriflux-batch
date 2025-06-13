package com.agriflux.agrifluxbatch.repository.projection;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public interface ProduzioneJoinColturaProjection extends Serializable {
	
	String getProdottoColtivato();
    String getAnnoRaccolto();
    BigDecimal getQuantitaRaccolto();
    BigDecimal getQuantitaRaccoltoVenduto();
    BigDecimal getFatturatoColtura();
}
