package com.agriflux.agrifluxbatch.repository.projection.terreno;

import java.util.Date;

public interface TerrenoMorfologiaColturaProjection {
	
	Long getIdMorfologia();
	String getProdottoColtivato();
	Long getIdColtura();
	Date getDataRilevazione();
}
