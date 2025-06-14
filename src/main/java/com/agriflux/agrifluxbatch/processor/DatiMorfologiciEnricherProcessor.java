package com.agriflux.agrifluxbatch.processor;

import java.math.BigDecimal;

import org.springframework.batch.item.ItemProcessor;

import com.agriflux.agrifluxbatch.model.DatiMorfologiciMetadata;
import com.agriflux.agrifluxbatch.model.DatiMorfologiciRecord;
import com.agriflux.agrifluxshared.enumeratori.EsposizioneEnum;
import com.agriflux.agrifluxshared.enumeratori.LitologiaTerrenoEnum;

public class DatiMorfologiciEnricherProcessor extends DatiProcessor implements ItemProcessor<DatiMorfologiciMetadata, DatiMorfologiciRecord>{
	
	@Override
	public DatiMorfologiciRecord process(DatiMorfologiciMetadata item) throws Exception {
		
		String estensioneTerrenoRange = item.estensioneTerreno();
		BigDecimal estensioneTerreno = generaRandomBigDecimalFromRange(estensioneTerrenoRange);
		
		BigDecimal pendenza = generaRandomBigDecimalFromRange(item.pendenza());
		
		String esposizione = item.esposizione();
		for (EsposizioneEnum esp : EsposizioneEnum.values()) {
			if (esp.name().equalsIgnoreCase(esposizione)) {
				esposizione = esp.getEsposizione();
			}
		}
		
		String litologia = item.litologia();
		int codiceLitologia = Integer.parseInt(litologia);
		for (LitologiaTerrenoEnum terreno : LitologiaTerrenoEnum.values()) {
			if (terreno.getCodiceLitologico() == codiceLitologia) {
				litologia = terreno.getNomeTerreno();
			}
		}
		
		return new DatiMorfologiciRecord(estensioneTerreno, pendenza, esposizione, litologia);
	}

}
