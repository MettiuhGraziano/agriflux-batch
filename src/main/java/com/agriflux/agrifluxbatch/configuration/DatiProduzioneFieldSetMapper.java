package com.agriflux.agrifluxbatch.configuration;

import org.springframework.batch.item.file.mapping.FieldSetMapper;
import org.springframework.batch.item.file.transform.FieldSet;
import org.springframework.validation.BindException;

import com.agriflux.agrifluxbatch.model.DatiProduzioneMetadata;

public class DatiProduzioneFieldSetMapper implements FieldSetMapper<DatiProduzioneMetadata>{

	private static final String SPESE_ACCESSORIE = "speseAccessorie";
	private static final String FK_ID_MORFOLOGIA = "fkIdMorfologia";
	private static final String FK_ID_COLTURA = "fkIdColtura";
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
		
		long fkIdColtura = fieldSet.readLong(FK_ID_COLTURA);
		long fkIdMorfologia = fieldSet.readLong(FK_ID_MORFOLOGIA);
		
		return new DatiProduzioneMetadata(numLavoratori, speseAccessorie, tempoSemina, tempoGerminazione,
				tempoTrapianto, tempoMaturazione, tempoRaccolta, fkIdColtura, fkIdMorfologia);
	}
	
}
