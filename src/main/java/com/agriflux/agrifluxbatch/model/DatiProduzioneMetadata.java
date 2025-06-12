package com.agriflux.agrifluxbatch.model;

public record DatiProduzioneMetadata(int numLavoratori, String speseAccessorie,
		int tempoSemina, int tempoGerminazione, int tempoTrapianto, int tempoMaturazione,
		int tempoRaccolta, long idColtura, long idMorfologia) {

}
