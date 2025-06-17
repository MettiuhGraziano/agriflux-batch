package com.agriflux.agrifluxbatch.job;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.batch.item.file.mapping.FieldSetMapper;
import org.springframework.batch.item.file.transform.FieldSet;
import org.springframework.validation.BindException;

import com.agriflux.agrifluxbatch.model.coltura.ColturaMetadata;

public class ColturaMetadataFieldSetMapper implements FieldSetMapper<ColturaMetadata> {

	private static final String PREZZO_KG = "prezzoKg";
	private static final String DATA_RACCOLTO = "dataRaccolto";
	private static final String DATA_SEMINA = "dataSemina";
	
	private final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");

    @Override
    public ColturaMetadata mapFieldSet(FieldSet fieldSet) throws BindException {
    	
    	String prezzoKgRange = fieldSet.readString(PREZZO_KG);
    	
    	Date semina;
    	Date raccolto;
    	
        try {
            semina = dateFormat.parse(fieldSet.readString(DATA_SEMINA));
            raccolto = dateFormat.parse(fieldSet.readString(DATA_RACCOLTO));
        } catch (Exception e) {
            throw new BindException(e, "ColturaRecord");
        }
		return new ColturaMetadata(prezzoKgRange, semina, raccolto);
    }

}
