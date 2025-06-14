package com.agriflux.agrifluxbatch.job;

import org.springframework.batch.item.file.mapping.FieldSetMapper;
import org.springframework.batch.item.file.transform.FieldSet;
import org.springframework.validation.BindException;

import com.agriflux.agrifluxbatch.model.DatiMorfologiciMetadata;

public class DatiMorfologiciFieldSetMapper implements FieldSetMapper<DatiMorfologiciMetadata>{

	private static final String PENDENZA = "pendenza";
	private static final String LITOLOGIA = "litologia";
	private static final String ESPOSIZIONE = "esposizione";
	private static final String ESTENSIONE_TERRENO = "estensioneTerreno";

	@Override
	public DatiMorfologiciMetadata mapFieldSet(FieldSet fieldSet) throws BindException {
		
		String estensioneTerreno = fieldSet.readString(ESTENSIONE_TERRENO);
		String pendenza = fieldSet.readString(PENDENZA);
		String esposizione = fieldSet.readString(ESPOSIZIONE);
		String litologia = fieldSet.readString(LITOLOGIA);
		
		return new DatiMorfologiciMetadata(estensioneTerreno, pendenza, esposizione,
				litologia);
	}

}
