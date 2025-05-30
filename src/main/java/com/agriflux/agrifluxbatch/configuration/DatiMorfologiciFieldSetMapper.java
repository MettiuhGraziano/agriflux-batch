package com.agriflux.agrifluxbatch.configuration;

import org.springframework.batch.item.file.mapping.FieldSetMapper;
import org.springframework.batch.item.file.transform.FieldSet;
import org.springframework.validation.BindException;

import com.agriflux.agrifluxbatch.model.DatiMorfologiciMetadata;

public class DatiMorfologiciFieldSetMapper implements FieldSetMapper<DatiMorfologiciMetadata>{

	private static final String FK_ID_COLTURA = "fkIdColtura";
	private static final String LITOLOGIA = "litologia";
	private static final String ESPOSIZIONE = "esposizione";
	private static final String ESTENSIONE_TERRENO = "estensioneTerreno";

	@Override
	public DatiMorfologiciMetadata mapFieldSet(FieldSet fieldSet) throws BindException {
		
		String estensioneTerreno = fieldSet.readString(ESTENSIONE_TERRENO);
		String esposizione = fieldSet.readString(ESPOSIZIONE);
		String litologia = fieldSet.readString(LITOLOGIA);
		String fkIdColtura = fieldSet.readString(FK_ID_COLTURA);
		
		return new DatiMorfologiciMetadata(estensioneTerreno, esposizione,
				litologia, fkIdColtura);
	}

}
