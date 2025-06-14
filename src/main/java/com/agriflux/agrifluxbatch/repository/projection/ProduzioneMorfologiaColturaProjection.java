package com.agriflux.agrifluxbatch.repository.projection;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public interface ProduzioneMorfologiaColturaProjection extends Serializable {

	Long getIdProduzione();
	String getProdottoColtivato();
	Date getDataRaccolto();
	Long getIdMorfologia();
	BigDecimal getEstensioneTerreno();
	BigDecimal getPendenza();
	String getEsposizione();
	String getLitologia();
}
