package com.agriflux.agrifluxbatch.repository.projection.produzione;

import java.math.BigDecimal;

public interface ProduzioneQuantitaJoinColturaProjection {
	
    BigDecimal getQuantitaRaccolto();
    BigDecimal getQuantitaRaccoltoVenduto();
    BigDecimal getQuantitaRaccoltoMarcio();
    BigDecimal getQuantitaRaccoltoTerzi();
    BigDecimal getFatturatoRaccolto();
    String getAnnoRaccolto();
    String getNomeOrtaggio();
}
