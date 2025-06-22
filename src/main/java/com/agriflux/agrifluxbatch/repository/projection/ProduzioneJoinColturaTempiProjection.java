package com.agriflux.agrifluxbatch.repository.projection;

public interface ProduzioneJoinColturaTempiProjection {

	String getNomeOrtaggio();
	String getAnnoSemina();
	int getTempoSemina();
	int getTempoGerminazione();
	int getTempoTrapianto();
	int getTempoMaturazione();
	int getTempoRaccolta();
}
