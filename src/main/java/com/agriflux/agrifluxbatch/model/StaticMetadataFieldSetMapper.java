package com.agriflux.agrifluxbatch.model;

import org.springframework.batch.item.file.mapping.FieldSetMapper;
import org.springframework.batch.item.file.transform.FieldSet;
import org.springframework.validation.BindException;

public class StaticMetadataFieldSetMapper implements FieldSetMapper<StaticMetadata>{

	@Override
	public StaticMetadata mapFieldSet(FieldSet fieldSet) throws BindException {
		
		StaticMetadata metadata = new StaticMetadata();
		
		metadata.setTemperatura(fieldSet.readString(0));
		metadata.setUmidita(fieldSet.readString(1));
		metadata.setQuantita_raccolto(fieldSet.readString(2));
		metadata.setCosto_raccolto(fieldSet.readString(3));
		
		return metadata;
	}

}
