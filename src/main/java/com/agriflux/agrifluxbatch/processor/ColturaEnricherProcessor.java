package com.agriflux.agrifluxbatch.processor;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

import org.springframework.batch.item.ItemProcessor;

import com.agriflux.agrifluxbatch.model.coltura.ColturaMetadata;
import com.agriflux.agrifluxbatch.model.coltura.ColturaRecord;
import com.agriflux.agrifluxshared.enumeratori.TipoProdottoEnum;

public class ColturaEnricherProcessor extends DatiProcessor implements ItemProcessor<ColturaMetadata, ColturaRecord>{
	
	private final Map<String, BigDecimal> cachePrezziColture = new HashMap<String, BigDecimal>();
	
	@Override
	public ColturaRecord process(ColturaMetadata item) throws Exception {
		
		int codiceProdotto = generaRandomIntFromRange(1, TipoProdottoEnum.values().length + 1);
		String prodotto = null;
		
		for (TipoProdottoEnum tipoProdotto : TipoProdottoEnum.values()) {
			if (tipoProdotto.getCodiceProdotto() == codiceProdotto) {
				prodotto = tipoProdotto.name();
			}
		}
		
		BigDecimal prezzoKg = null;
		
		if (null != cachePrezziColture) {
			if (null != cachePrezziColture.get(prodotto)) {
				prezzoKg = applicaVariazioneDistribuzione(cachePrezziColture.get(prodotto));
			} else {
				prezzoKg = generaRandomBigDecimalFromRange(item.prezzoKg());
			}
		}
		
		cachePrezziColture.put(prodotto, prezzoKg);
		
		return new ColturaRecord(prodotto, prezzoKg, item.dataSemina(), item.dataRaccolto());
	}

}
