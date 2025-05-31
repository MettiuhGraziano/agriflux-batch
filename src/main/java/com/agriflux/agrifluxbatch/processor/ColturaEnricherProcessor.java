package com.agriflux.agrifluxbatch.processor;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

import org.springframework.batch.item.ItemProcessor;

import com.agriflux.agrifluxbatch.enumeratori.TipoProdottoEnum;
import com.agriflux.agrifluxbatch.model.ColturaMetadata;
import com.agriflux.agrifluxbatch.model.ColturaRecord;

public class ColturaEnricherProcessor extends DatiProcessor implements ItemProcessor<ColturaMetadata, ColturaRecord>{
	
	private final Map<String, BigDecimal> cacheColtura = new HashMap<String, BigDecimal>();
	
	@Override
	public ColturaRecord process(ColturaMetadata item) throws Exception {
		
		int codiceProdotto = generaRandomIntFromRange(1, 7);
		String prodotto = null;
		
		for (TipoProdottoEnum tipoProdotto : TipoProdottoEnum.values()) {
			if (tipoProdotto.getCodiceProdotto() == codiceProdotto) {
				prodotto = tipoProdotto.name();
			}
		}
		
		BigDecimal prezzoKg = null;
		
		if (null != cacheColtura.get(prodotto)) {
			prezzoKg = cacheColtura.get(prodotto);
		} else {
			prezzoKg = generaRandomBigDecimalFromRange(item.prezzoKg());
			cacheColtura.put(prodotto, prezzoKg);
		}
		
		return new ColturaRecord(prodotto, prezzoKg, item.dataSemina(), item.dataRaccolto());
	}

}
