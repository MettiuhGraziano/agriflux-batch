package com.agriflux.agrifluxbatch.configuration;

import java.util.Random;

import org.springframework.batch.item.ItemProcessor;

import com.agriflux.agrifluxbatch.model.StaticMetadata;
import com.agriflux.agrifluxbatch.model.StaticRandomMetadata;

/**
 * Processor per la generazione di dati random partendo da uno StaticMetadata
 */
public class GenerateStaticRandomMetadataProcessor implements ItemProcessor<StaticMetadata, StaticRandomMetadata>{
	
	public static final String DELIMITER = "-";

	@Override
	public StaticRandomMetadata process(StaticMetadata item) throws Exception {
		StaticRandomMetadata randomData = new StaticRandomMetadata();
		
		String temperaturaRange = item.getTemperatura();
		randomData.setTemperatura(generateRandomIntegerFromRange(temperaturaRange)); 
		
		String umiditaRange = item.getUmidita();
		randomData.setUmidita(generateRandomIntegerFromRange(umiditaRange));
		
		String quantitaRaccoltoRange = item.getQuantita_raccolto();
		randomData.setQuantita_raccolto(generateRandomIntegerFromRange(quantitaRaccoltoRange));
		
		String costoRaccolto = item.getCosto_raccolto();
		randomData.setCosto_raccolto(generateRandomIntegerFromRange(costoRaccolto));
		
		return randomData;
	}

	private int generateRandomIntegerFromRange(String range) {
		int index = range.lastIndexOf(DELIMITER);
		String rangeMin = range.substring(0, index);
		String rangeMax = range.substring(index + 1);
		
		Random random = new Random();
		return random.nextInt(Integer.parseInt(rangeMin), Integer.parseInt(rangeMax));
	}

}
