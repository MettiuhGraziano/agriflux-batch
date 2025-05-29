package com.agriflux.agrifluxbatch.processor;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

import org.springframework.batch.item.ItemProcessor;

import com.agriflux.agrifluxbatch.model.DatiAmbientaliMetadata;
import com.agriflux.agrifluxbatch.model.DatiAmbientaliRecord;

public class DatiAmbientaliEnricherProcessor implements ItemProcessor<DatiAmbientaliMetadata, DatiAmbientaliRecord>{
	
	private static final String DELIMITER = "|";
	
	private final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");
	
	@Override
	public DatiAmbientaliRecord process(DatiAmbientaliMetadata datiAmbientaliMetadata) throws Exception {
			
		BigDecimal temperaturaRandom = null;
		BigDecimal umiditaRandom = null;
		BigDecimal precipitazioniRandom = null;
		BigDecimal irraggiamentoRandom = null;
		BigDecimal ombreggiamentoRandom = null;

		if (null != datiAmbientaliMetadata.temperaturaMedia()) {
			temperaturaRandom = generaRandomDataFromRange(datiAmbientaliMetadata.temperaturaMedia());
		}

		if (null != datiAmbientaliMetadata.umiditaMedia()) {
			umiditaRandom = generaRandomDataFromRange(datiAmbientaliMetadata.umiditaMedia());
		}

		if (null != datiAmbientaliMetadata.temperaturaMedia()) {
			precipitazioniRandom = generaRandomDataFromRange(datiAmbientaliMetadata.precipitazioni());
		}

		if (null != datiAmbientaliMetadata.irraggiamentoMedio()) {
			irraggiamentoRandom = generaRandomDataFromRange(datiAmbientaliMetadata.irraggiamentoMedio());
		}

		if (null != datiAmbientaliMetadata.ombreggiamentoMedio()) {
			ombreggiamentoRandom = generaRandomDataFromRange(datiAmbientaliMetadata.ombreggiamentoMedio());
		}
		
		Date dataRilevazione = dateFormat.parse(datiAmbientaliMetadata.dataRilevazione());
		
		long fkIdColtura = Long.parseLong(datiAmbientaliMetadata.fkIdColtura());
		
		return new DatiAmbientaliRecord(temperaturaRandom, umiditaRandom, precipitazioniRandom, irraggiamentoRandom,
				ombreggiamentoRandom, dataRilevazione,
				fkIdColtura);

	}

	private BigDecimal generaRandomDataFromRange(String range) {

		int delimiterIndex = range.indexOf(DELIMITER);
		String rangeMin = range.substring(0, delimiterIndex);
		String rangeMax = range.substring(delimiterIndex + 1);
		
		double min = 0;
		double max = 0;
		
		if (null != rangeMin && null != rangeMax) {
			min = Double.parseDouble(rangeMin);
			max = Double.parseDouble(rangeMax);
		}
		
		Random random = new Random();
		double randomValue = random.nextDouble(min, max);
		
		return BigDecimal.valueOf(randomValue);
	}

}
