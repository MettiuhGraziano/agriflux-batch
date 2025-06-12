package com.agriflux.agrifluxbatch.processor;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.batch.item.ItemProcessor;

import com.agriflux.agrifluxbatch.model.DatiAmbientaliMetadata;
import com.agriflux.agrifluxbatch.model.DatiAmbientaliRecord;

public class DatiAmbientaliEnricherProcessor extends DatiProcessor implements ItemProcessor<DatiAmbientaliMetadata, DatiAmbientaliRecord>{
	
	private final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");
	
	@Override
	public DatiAmbientaliRecord process(DatiAmbientaliMetadata datiAmbientaliMetadata) throws Exception {
			
		BigDecimal temperaturaRandom = null;
		BigDecimal umiditaRandom = null;
		BigDecimal precipitazioniRandom = null;
		BigDecimal irraggiamentoRandom = null;
		BigDecimal ombreggiamentoRandom = null;

		if (null != datiAmbientaliMetadata.temperaturaMedia()) {
			temperaturaRandom = generaRandomBigDecimalFromRange(datiAmbientaliMetadata.temperaturaMedia());
		}

		if (null != datiAmbientaliMetadata.umiditaMedia()) {
			umiditaRandom = generaRandomBigDecimalFromRange(datiAmbientaliMetadata.umiditaMedia());
		}

		if (null != datiAmbientaliMetadata.temperaturaMedia()) {
			precipitazioniRandom = generaRandomBigDecimalFromRange(datiAmbientaliMetadata.precipitazioni());
		}

		if (null != datiAmbientaliMetadata.irraggiamentoMedio()) {
			irraggiamentoRandom = generaRandomBigDecimalFromRange(datiAmbientaliMetadata.irraggiamentoMedio());
		}

		if (null != datiAmbientaliMetadata.ombreggiamentoMedio()) {
			ombreggiamentoRandom = generaRandomBigDecimalFromRange(datiAmbientaliMetadata.ombreggiamentoMedio());
		}
		
		Date dataRilevazione = dateFormat.parse(datiAmbientaliMetadata.dataRilevazione());
		
		long idColtura = Long.parseLong(datiAmbientaliMetadata.idColtura());
		
		return new DatiAmbientaliRecord(temperaturaRandom, umiditaRandom, precipitazioniRandom, irraggiamentoRandom,
				ombreggiamentoRandom, dataRilevazione, idColtura);
	}

}
