package com.agriflux.agrifluxbatch.repository.projection;

import java.io.Serializable;
import java.util.Date;

public interface TerrenoMorfologiaColturaProjection extends Serializable {
	
	Long getIdMorfologia();
	String getProdottoColtivato();
	Long getIdColtura();
	Date getDataRilevazione();
}
