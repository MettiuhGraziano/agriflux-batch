package com.agriflux.agrifluxbatch.processor.produzione;

import java.math.BigDecimal;

import org.springframework.batch.item.ItemProcessor;

import com.agriflux.agrifluxbatch.model.produzione.DatiColturaJoinParticellaRecord;
import com.agriflux.agrifluxbatch.model.produzione.DatiProduzioneRecord;
import com.agriflux.agrifluxbatch.processor.DatiProcessor;

public class DatiProduzioneCustomProcessor extends DatiProcessor implements ItemProcessor<DatiColturaJoinParticellaRecord, DatiProduzioneRecord>{
	
	@Override
	public DatiProduzioneRecord process(DatiColturaJoinParticellaRecord item) throws Exception {
		
		BigDecimal quantitaRaccolto = new BigDecimal(item.estensione().multiply(item.volumeMq()).doubleValue());
		BigDecimal quantitaRaccoltoVenduto = calcoloQuantitaRaccoltoVenduto(quantitaRaccolto);
		
		BigDecimal quantitaRaccoltoNonVenduto = quantitaRaccolto.subtract(quantitaRaccoltoVenduto);
		
		BigDecimal quantitaRaccoltoMarcio = generaRandomBigDecimalFromRange(0, quantitaRaccoltoNonVenduto.doubleValue());
		BigDecimal quantitaRaccoltoTerzi = quantitaRaccoltoNonVenduto.subtract(quantitaRaccoltoMarcio);
		
		BigDecimal fatturatoRaccolto = quantitaRaccoltoVenduto.multiply(item.pesoMedio());
		
		int numLavoratori = generaRandomIntFromRange(1, 11);
		
		BigDecimal speseProduzione = generaRandomBigDecimalFromRange("100.0|1500.0");
		
		return new DatiProduzioneRecord(quantitaRaccolto, quantitaRaccoltoVenduto, quantitaRaccoltoMarcio,
				quantitaRaccoltoTerzi, fatturatoRaccolto, numLavoratori, speseProduzione, item.idColtura());
	}
	
	private BigDecimal calcoloQuantitaRaccoltoVenduto(BigDecimal quantitaRaccolto) {

		double scarto = quantitaRaccolto.doubleValue() / 2.0;

		BigDecimal quantitaRaccoltoNonVenduto = generaRandomBigDecimalFromRange(0, scarto);

		return quantitaRaccolto.subtract(quantitaRaccoltoNonVenduto);
	}
	
}
