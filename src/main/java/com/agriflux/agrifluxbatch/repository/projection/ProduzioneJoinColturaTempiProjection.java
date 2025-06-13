package com.agriflux.agrifluxbatch.repository.projection;

import java.io.Serializable;

public interface ProduzioneJoinColturaTempiProjection extends Serializable {

	String getProdottoColtivato();
	String getAnnoSemina();
	int getTempoSemina();
	int getTempoGerminazione();
	int getTempoTrapianto();
	int getTempoMaturazione();
	int getTempoRaccolta();
}
