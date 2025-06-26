package com.agriflux.agrifluxbatch.processor.ambiente;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.springframework.batch.item.ItemProcessor;

import com.agriflux.agrifluxbatch.model.ambiente.DatiAmbienteRecord;
import com.agriflux.agrifluxbatch.model.stagione.DatiStagioneRecord;
import com.agriflux.agrifluxbatch.processor.DatiProcessor;

public class DatiAmbienteCustomProcessor extends DatiProcessor implements ItemProcessor<DatiStagioneRecord, List<DatiAmbienteRecord>> {
	
	@Override
	public List<DatiAmbienteRecord> process(DatiStagioneRecord datiStagioneRecord) throws Exception {
			
		List<DatiAmbienteRecord> response = new ArrayList<DatiAmbienteRecord>();
		
		int counterAnnoRilevazione = Integer.parseInt(cacheParticella.get(1L).getAnnoInstallazione());
		
		String meseGiornoInizio = datiStagioneRecord.meseGiornoInizio();
		int delimiterIndex = meseGiornoInizio.indexOf("-");
		int meseInizio = Integer.parseInt(meseGiornoInizio.substring(0, delimiterIndex));
		int giornoInizio = Integer.parseInt(meseGiornoInizio.substring(delimiterIndex + 1));
		
		String meseGiornoFine = datiStagioneRecord.meseGiornoFine();
		delimiterIndex = meseGiornoFine.indexOf("-");
		int meseFine = Integer.parseInt(meseGiornoFine.substring(0, delimiterIndex));
		int giornoFine = Integer.parseInt(meseGiornoFine.substring(delimiterIndex + 1));
		
		LocalDate dataOdierna = LocalDate.now();
		
		while (counterAnnoRilevazione <= dataOdierna.getYear()) {

			if (counterAnnoRilevazione != dataOdierna.getYear()) {

				generaRilevazioneAmbiente(datiStagioneRecord, response, counterAnnoRilevazione, meseInizio,
						giornoInizio, meseFine, giornoFine, datiStagioneRecord.nome());
				
			} else if (counterAnnoRilevazione == dataOdierna.getYear() && dataOdierna.getMonthValue() <= meseFine
					&& dataOdierna.getMonthValue() >= meseInizio) {
				
				generaRilevazioneAmbiente(datiStagioneRecord, response, counterAnnoRilevazione, meseInizio,
						giornoInizio, dataOdierna.getMonthValue(), dataOdierna.getDayOfMonth(), datiStagioneRecord.nome());
			}

			counterAnnoRilevazione++;
		}
		
		return response;
	}

	private void generaRilevazioneAmbiente(DatiStagioneRecord datiStagioneRecord, List<DatiAmbienteRecord> response,
			int counterAnnoRilevazione, int meseInizio, int giornoInizio, int meseFine, int giornoFine, String nomeStagione) {
		
		BigDecimal temperatura = generaRandomBigDecimalFromRange(datiStagioneRecord.rangeTemperatura());
		BigDecimal umidita = generaRandomBigDecimalFromRange(datiStagioneRecord.rangeUmidita());
		BigDecimal precipitazioni = generaRandomBigDecimalFromRange(datiStagioneRecord.rangePrecipitazioni());
		BigDecimal irraggiamento = generaRandomBigDecimalFromRange(datiStagioneRecord.rangeIrraggiamento());
		BigDecimal ombreggiamento = generaRandomBigDecimalFromRange(datiStagioneRecord.rangeOmbreggiamento());
		
		LocalDate dataInizioRange = LocalDate.of(counterAnnoRilevazione, meseInizio, giornoInizio);
		LocalDate dataFineRange = LocalDate.of(counterAnnoRilevazione, meseFine, giornoFine);
		if (nomeStagione.equalsIgnoreCase("INVERNO")) {
			dataFineRange = LocalDate.of(counterAnnoRilevazione+1, meseFine, giornoFine);
		}

		LocalDate dataRilevazione = generaDataRandomFromRange(dataInizioRange, dataFineRange);

		response.add(new DatiAmbienteRecord(temperatura, umidita, precipitazioni, irraggiamento, ombreggiamento,
				dataRilevazione));
	}

}
