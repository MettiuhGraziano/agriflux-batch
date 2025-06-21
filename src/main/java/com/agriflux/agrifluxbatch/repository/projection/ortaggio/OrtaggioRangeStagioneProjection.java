package com.agriflux.agrifluxbatch.repository.projection.ortaggio;

import java.math.BigDecimal;

public interface OrtaggioRangeStagioneProjection {
	
	long getIdOrtaggio();
	int getGiorniSemina();
	int getGiorniGerminazione();
	int getGiorniTrapianto();
	int getGiorniMaturazione();
	int getGiorniRaccolta();
	
	BigDecimal getPesoMedio();
	BigDecimal getVolumeMq();
	
	String getMeseSeminaMin();
	String getMeseSeminaMax();
}
