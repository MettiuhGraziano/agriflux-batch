package com.agriflux.agrifluxbatch.processor.terreno;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

import org.springframework.batch.item.ItemProcessor;

import com.agriflux.agrifluxbatch.model.terreno.DatiTerrenoMetadata;
import com.agriflux.agrifluxbatch.model.terreno.DatiTerrenoRecord;
import com.agriflux.agrifluxbatch.processor.DatiProcessor;

public class DatiTerrenoEnricherProcessor extends DatiProcessor implements ItemProcessor<DatiTerrenoMetadata, DatiTerrenoRecord>{
	
	private static final String PH_SUOLO = "PH_SUOLO";
	private static final String UMIDITA = "UMIDITA";
	private static final String CAP_ASSORBENTE = "CAP_ASSORBENTE";
	private static final String POROSITA = "POROSITA";
	private static final String TEMPERATURA = "TEMPERATURA";
	
	private final Map<String, BigDecimal> cacheVariazioneRilevazioniTerreno = new HashMap<String, BigDecimal>();
	
	@Override
	public DatiTerrenoRecord process(DatiTerrenoMetadata item) throws Exception {
		
		BigDecimal phSuolo = null;
		if (null != cacheVariazioneRilevazioniTerreno.get(PH_SUOLO.concat(String.valueOf(item.idColtura())))) {
			phSuolo = applicaVariazioneDistribuzione(cacheVariazioneRilevazioniTerreno.get(PH_SUOLO.concat(String.valueOf(item.idColtura()))));
		} else {
			phSuolo = generaRandomBigDecimalFromRange(item.phSuolo());
		}
		cacheVariazioneRilevazioniTerreno.put(PH_SUOLO.concat(String.valueOf(item.idColtura())), phSuolo);
		
		BigDecimal umidita = null;
		if (null != cacheVariazioneRilevazioniTerreno.get(UMIDITA.concat(String.valueOf(item.idColtura())))) {
			umidita = applicaVariazioneDistribuzione(cacheVariazioneRilevazioniTerreno.get(UMIDITA.concat(String.valueOf(item.idColtura()))));
		} else {
			umidita = generaRandomBigDecimalFromRange(item.umidita());
		}
		cacheVariazioneRilevazioniTerreno.put(UMIDITA.concat(String.valueOf(item.idColtura())), umidita);
		
		BigDecimal capacitaAssorbente = null;
		if (null != cacheVariazioneRilevazioniTerreno.get(CAP_ASSORBENTE.concat(String.valueOf(item.idColtura())))) {
			capacitaAssorbente = applicaVariazioneDistribuzione(cacheVariazioneRilevazioniTerreno.get(CAP_ASSORBENTE.concat(String.valueOf(item.idColtura()))));
		} else {
			capacitaAssorbente = generaRandomBigDecimalFromRange(item.capacitaAssorbente());
		}
		cacheVariazioneRilevazioniTerreno.put(CAP_ASSORBENTE.concat(String.valueOf(item.idColtura())), capacitaAssorbente);

		BigDecimal porosita = null;
		if (null != cacheVariazioneRilevazioniTerreno.get(POROSITA.concat(String.valueOf(item.idColtura())))) {
			porosita = applicaVariazioneDistribuzione(cacheVariazioneRilevazioniTerreno.get(POROSITA.concat(String.valueOf(item.idColtura()))));
		} else {
			porosita = generaRandomBigDecimalFromRange(item.porosita());
		}
		cacheVariazioneRilevazioniTerreno.put(POROSITA.concat(String.valueOf(item.idColtura())), porosita);
		
		BigDecimal temperatura = null;
		if (null != cacheVariazioneRilevazioniTerreno.get(TEMPERATURA.concat(String.valueOf(item.idColtura())))) {
			temperatura = applicaVariazioneDistribuzione(cacheVariazioneRilevazioniTerreno.get(TEMPERATURA.concat(String.valueOf(item.idColtura()))));
		} else {
			temperatura = generaRandomBigDecimalFromRange(item.temperatura());
		}
		cacheVariazioneRilevazioniTerreno.put(TEMPERATURA.concat(String.valueOf(item.idColtura())), temperatura);
		
		return new DatiTerrenoRecord(phSuolo, umidita, capacitaAssorbente, porosita,
				temperatura, item.disponibilitaIrrigua(), item.dataRilevazione(),
				item.idColtura(), item.idMorfologia());
	}

}
