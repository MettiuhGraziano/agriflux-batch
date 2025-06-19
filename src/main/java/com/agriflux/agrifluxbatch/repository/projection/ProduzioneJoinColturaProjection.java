package com.agriflux.agrifluxbatch.repository.projection;

import java.math.BigDecimal;

public interface ProduzioneJoinColturaProjection {
	
	String getProdottoColtivato();
    String getAnnoRaccolto();
    BigDecimal getQuantitaRaccolto();
    BigDecimal getQuantitaRaccoltoVenduto();
    BigDecimal getFatturatoColtura();
}
