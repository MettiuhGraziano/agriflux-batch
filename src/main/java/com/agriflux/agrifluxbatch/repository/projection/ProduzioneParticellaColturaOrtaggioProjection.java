package com.agriflux.agrifluxbatch.repository.projection;

import java.math.BigDecimal;
import java.time.LocalDate;

public interface ProduzioneParticellaColturaOrtaggioProjection {

	Long getIdProduzione();
	String getNomeOrtaggio();
	LocalDate getDataRaccolto();
	Long getIdParticella();
	BigDecimal getEstensione();
	BigDecimal getPendenza();
	String getEsposizione();
	String getTipologia();
}
