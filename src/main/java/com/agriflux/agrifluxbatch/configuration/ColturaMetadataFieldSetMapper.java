package com.agriflux.agrifluxbatch.configuration;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.batch.item.file.mapping.FieldSetMapper;
import org.springframework.batch.item.file.transform.FieldSet;
import org.springframework.validation.BindException;

import com.agriflux.agrifluxbatch.model.ColturaMetadata;

public class ColturaMetadataFieldSetMapper implements FieldSetMapper<ColturaMetadata> {

	private static final String DATA_RACCOLTO = "dataRaccolto";
	private static final String DATA_SEMINA = "dataSemina";
	
	private final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");

    @Override
    public ColturaMetadata mapFieldSet(FieldSet fieldSet) throws BindException {
    	
    	ColturaMetadata record = null;
        try {
            Date semina = dateFormat.parse(fieldSet.readString(DATA_SEMINA));
            Date raccolto = dateFormat.parse(fieldSet.readString(DATA_RACCOLTO));
            
            record = new ColturaMetadata(semina, raccolto);
            
        } catch (Exception e) {
            throw new BindException(record, "ColturaRecord");
        }
        return record;
    }

}
