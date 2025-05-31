package com.agriflux.agrifluxbatch.processor;

import java.math.BigDecimal;

import org.springframework.batch.item.ItemProcessor;

import com.agriflux.agrifluxbatch.model.DatiTerrenoMetadata;
import com.agriflux.agrifluxbatch.model.DatiTerrenoRecord;

public class DatiTerrenoEnricherProcessor extends DatiProcessor implements ItemProcessor<DatiTerrenoMetadata, DatiTerrenoRecord>{

	@Override
	public DatiTerrenoRecord process(DatiTerrenoMetadata item) throws Exception {
		
		BigDecimal phSuolo = generaRandomBigDecimalFromRange(item.phSuolo());
		BigDecimal umidita = generaRandomBigDecimalFromRange(item.umidita());
		BigDecimal capacitaAssorbente = generaRandomBigDecimalFromRange(item.capacitaAssorbente());
		BigDecimal porosita = generaRandomBigDecimalFromRange(item.porosita());
		BigDecimal temperatura = generaRandomBigDecimalFromRange(item.temperatura());
		
		return new DatiTerrenoRecord(phSuolo, umidita, capacitaAssorbente, porosita,
				temperatura, item.disponibilitaIrrigua(), item.dataRilevazione(),
				item.fkIdColtura(), item.fkIdMorfologia());
	}

}
