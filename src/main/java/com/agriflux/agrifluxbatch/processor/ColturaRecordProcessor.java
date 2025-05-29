package com.agriflux.agrifluxbatch.processor;

import java.util.Random;

import org.springframework.batch.item.ItemProcessor;

import com.agriflux.agrifluxbatch.model.ColturaMetadata;
import com.agriflux.agrifluxbatch.model.ColturaRecord;
import com.agriflux.agrifluxbatch.model.TipoProdotto;

public class ColturaRecordProcessor implements ItemProcessor<ColturaMetadata, ColturaRecord>{

	@Override
	public ColturaRecord process(ColturaMetadata item) throws Exception {
		
		Random random = new Random();
		int codiceProdottoRandom = random.nextInt(1, 7);
		String prodotto = null;
		
		for (TipoProdotto tipoProdotto : TipoProdotto.values()) {
			if (tipoProdotto.getCodiceProdotto() == codiceProdottoRandom) {
				prodotto = tipoProdotto.name();
			}
		}
		
		ColturaRecord coltura = new ColturaRecord(prodotto, item.annoMeseSemina(), item.annoMeseRaccolto());
		
		return coltura;
	}

}
