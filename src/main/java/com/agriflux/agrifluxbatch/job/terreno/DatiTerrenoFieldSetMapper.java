package com.agriflux.agrifluxbatch.job.terreno;

import org.springframework.batch.item.file.mapping.FieldSetMapper;
import org.springframework.batch.item.file.transform.FieldSet;
import org.springframework.validation.BindException;

import com.agriflux.agrifluxbatch.model.terreno.DatiRilevazioneTerrenoMetadata;

public class DatiTerrenoFieldSetMapper implements FieldSetMapper<DatiRilevazioneTerrenoMetadata>{

	private static final String TEMPERATURA = "temperatura";
	private static final String POROSITA = "porosita";
	private static final String CAPACITA_ASSORBENTE = "capacitaAssorbente";
	private static final String UMIDITA = "umidita";
	private static final String PH_SUOLO = "phSuolo";
	
	@Override
	public DatiRilevazioneTerrenoMetadata mapFieldSet(FieldSet fieldSet) throws BindException {
		
		String phSuolo = fieldSet.readString(PH_SUOLO);
		String umidita = fieldSet.readString(UMIDITA);
		String capacitaAssorbente = fieldSet.readString(CAPACITA_ASSORBENTE);
		String porosita = fieldSet.readString(POROSITA);
		String temperatura = fieldSet.readString(TEMPERATURA);
		
		return new DatiRilevazioneTerrenoMetadata(phSuolo, umidita, capacitaAssorbente, porosita, temperatura);
	}

}
