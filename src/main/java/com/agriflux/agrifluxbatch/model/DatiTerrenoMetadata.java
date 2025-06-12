package com.agriflux.agrifluxbatch.model;

import java.util.Date;

public record DatiTerrenoMetadata(String phSuolo, String umidita, String capacitaAssorbente,
		String porosita, String temperatura, int disponibilitaIrrigua,
		Date dataRilevazione, long idColtura, long idMorfologia) {

}
