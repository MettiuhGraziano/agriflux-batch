package com.agriflux.agrifluxbatch.repository.projection.particella;

import java.time.LocalDate;

public interface ParticellaColturaRilevazioneProjection {
	
	long getIdParticella();
	long getIdColtura();
	String getNomeProdotto();
	LocalDate getDataRilevazioneTerreno();
}
