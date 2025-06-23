package com.agriflux.agrifluxbatch.repository.projection.produzione;

public interface ProduzioneJoinColturaFatturatoProjection {
	
	double getFatturatoRaccolto();
	double getSpeseProduzione();
	long getIdParticella();
	int getAnnoRaccolto();
}
