package com.agriflux.agrifluxbatch.job;

import org.springframework.batch.item.file.mapping.FieldSetMapper;
import org.springframework.batch.item.file.transform.FieldSet;
import org.springframework.validation.BindException;

import com.agriflux.agrifluxbatch.model.DatiProduzioneMetadata;

public class DatiProduzioneFieldSetMapper implements FieldSetMapper<DatiProduzioneMetadata>{

	private static final String SPESE_ACCESSORIE = "speseAccessorie";
	private static final String ID_MORFOLOGIA = "idMorfologia";
	private static final String ID_COLTURA = "idColtura";
	private static final String TEMPO_RACCOLTA = "tempoRaccolta";
	private static final String TEMPO_MATURAZIONE = "tempoMaturazione";
	private static final String TEMPO_TRAPIANTO = "tempoTrapianto";
	private static final String TEMPO_GERMINAZIONE = "tempoGerminazione";
	private static final String TEMPO_SEMINA = "tempoSemina";
	private static final String NUM_LAVORATORI = "numLavoratori";

	@Override
	public DatiProduzioneMetadata mapFieldSet(FieldSet fieldSet) throws BindException {
		
		int numLavoratori = fieldSet.readInt(NUM_LAVORATORI);
		int tempoSemina = fieldSet.readInt(TEMPO_SEMINA);
		int tempoGerminazione = fieldSet.readInt(TEMPO_GERMINAZIONE);
		int tempoTrapianto = fieldSet.readInt(TEMPO_TRAPIANTO);
		int tempoMaturazione = fieldSet.readInt(TEMPO_MATURAZIONE);
		int tempoRaccolta = fieldSet.readInt(TEMPO_RACCOLTA);
		
		String speseAccessorie = fieldSet.readString(SPESE_ACCESSORIE);
		
		long idColtura = fieldSet.readLong(ID_COLTURA);
		long idMorfologia = fieldSet.readLong(ID_MORFOLOGIA);
		
		return new DatiProduzioneMetadata(numLavoratori, speseAccessorie, tempoSemina, tempoGerminazione,
				tempoTrapianto, tempoMaturazione, tempoRaccolta, idColtura, idMorfologia);
	}
	
}
