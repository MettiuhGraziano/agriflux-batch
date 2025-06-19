package com.agriflux.agrifluxbatch.repository.projection;

import java.math.BigDecimal;
import java.util.Date;

public interface ProduzioneMorfologiaColturaProjection {

	Long getIdProduzione();
	String getProdottoColtivato();
	Date getDataRaccolto();
	Long getIdMorfologia();
	BigDecimal getEstensioneTerreno();
	BigDecimal getPendenza();
	String getEsposizione();
	String getLitologia();
}
