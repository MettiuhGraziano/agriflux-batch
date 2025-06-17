package com.agriflux.agrifluxbatch.job.particella;

import java.math.BigDecimal;

import org.springframework.batch.item.file.mapping.FieldSetMapper;
import org.springframework.batch.item.file.transform.FieldSet;
import org.springframework.validation.BindException;

import com.agriflux.agrifluxbatch.model.particella.DatiParticellaMetadata;

public class DatiParticellaFieldSetMapper implements FieldSetMapper<DatiParticellaMetadata>{

	private static final String COSTO = "costo";
	private static final String ANNO_INSTALLAZIONE = "annoInstallazione";
	private static final String QUALITA = "qualita";
	private static final String FOGLIO = "foglio";
	private static final String COMUNE = "comune";
	private static final String PENDENZA = "pendenza";
	private static final String ESPOSIZIONE = "esposizione";
	private static final String ESTENSIONE = "estensione";

	@Override
	public DatiParticellaMetadata mapFieldSet(FieldSet fieldSet) throws BindException {
		
		String comune = fieldSet.readString(COMUNE);
		String foglio = fieldSet.readString(FOGLIO);
		String qualita = fieldSet.readString(QUALITA);
		String annoInstallazione = fieldSet.readString(ANNO_INSTALLAZIONE);
		BigDecimal costo = fieldSet.readBigDecimal(COSTO);
		BigDecimal estensione = fieldSet.readBigDecimal(ESTENSIONE);
		String pendenza = fieldSet.readString(PENDENZA);
		String esposizione = fieldSet.readString(ESPOSIZIONE);
		
		return new DatiParticellaMetadata(comune, foglio, qualita,
				annoInstallazione, costo, estensione, pendenza, esposizione);
	}
	
}
