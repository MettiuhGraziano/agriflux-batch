package com.agriflux.agrifluxbatch.configuration;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.batch.item.file.mapping.FieldSetMapper;
import org.springframework.batch.item.file.transform.FieldSet;
import org.springframework.validation.BindException;

import com.agriflux.agrifluxbatch.model.ColturaMetadata;

public class ColturaMetadataFieldSetMapper implements FieldSetMapper<ColturaMetadata> {

	private final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");

    @Override
    public ColturaMetadata mapFieldSet(FieldSet fieldSet) throws BindException {
    	
    	ColturaMetadata record = null;
        try {
            Date semina = dateFormat.parse(fieldSet.readString("annoMeseSemina"));
            Date raccolto = dateFormat.parse(fieldSet.readString("annoMeseRaccolto"));
            
            record = new ColturaMetadata(semina, raccolto);
            
        } catch (Exception e) {
            throw new BindException(record, "ColturaRecord");
        }
        return record;
    }

}
