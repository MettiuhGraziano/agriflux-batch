package com.agriflux.agrifluxbatch.job.terreno;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.batch.item.file.mapping.FieldSetMapper;
import org.springframework.batch.item.file.transform.FieldSet;
import org.springframework.validation.BindException;

import com.agriflux.agrifluxbatch.model.terreno.DatiTerrenoMetadata;

public class DatiTerrenoFieldSetMapper implements FieldSetMapper<DatiTerrenoMetadata>{

	private static final String ID_MORFOLOGIA = "idMorfologia";
	private static final String ID_COLTURA = "idColtura";
	private static final String DATA_RILEVAZIONE = "dataRilevazione";
	private static final String DISPONIBILITA_IRRIGUA = "disponibilitaIrrigua";
	private static final String TEMPERATURA = "temperatura";
	private static final String POROSITA = "porosita";
	private static final String CAPACITA_ASSORBENTE = "capacitaAssorbente";
	private static final String UMIDITA = "umidita";
	private static final String PH_SUOLO = "phSuolo";
	
	private final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");

	@Override
	public DatiTerrenoMetadata mapFieldSet(FieldSet fieldSet) throws BindException {
		
		String phSuolo = fieldSet.readString(PH_SUOLO);
		String umidita = fieldSet.readString(UMIDITA);
		String capacitaAssorbente = fieldSet.readString(CAPACITA_ASSORBENTE);
		String porosita = fieldSet.readString(POROSITA);
		String temperatura = fieldSet.readString(TEMPERATURA);
		
		int disponibilitaIrrigua = fieldSet.readInt(DISPONIBILITA_IRRIGUA);
		
		Date dataRilevazione = new Date();
		try {
			dataRilevazione = dateFormat.parse(fieldSet.readString(DATA_RILEVAZIONE));
		} catch (ParseException e) {
			e.printStackTrace();
		}
		
		long idColtura = fieldSet.readLong(ID_COLTURA);
		long idMorfologia = fieldSet.readLong(ID_MORFOLOGIA);
		
		
		return new DatiTerrenoMetadata(phSuolo, umidita, capacitaAssorbente, porosita, temperatura, disponibilitaIrrigua, dataRilevazione, idColtura, idMorfologia);
	}

}
