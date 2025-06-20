package com.agriflux.agrifluxbatch.repository.projection;

public interface ProduzioneJoinColturaFatturatoProjection {
	
	double getFatturatoRaccolto();
	double getSpeseProduzione();
	long getIdParticella();
	int getAnnoRaccolto();
}
