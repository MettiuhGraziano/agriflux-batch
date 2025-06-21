package com.agriflux.agrifluxbatch.repository.projection.particella;

import java.util.Date;

public interface TerrenoMorfologiaColturaProjection {
	
	Long getIdMorfologia();
	String getProdottoColtivato();
	Long getIdColtura();
	Date getDataRilevazione();
}
