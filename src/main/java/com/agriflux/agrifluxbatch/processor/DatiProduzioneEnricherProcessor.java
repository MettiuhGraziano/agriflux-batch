package com.agriflux.agrifluxbatch.processor;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

import org.springframework.batch.core.annotation.BeforeProcess;
import org.springframework.batch.item.ItemProcessor;

import com.agriflux.agrifluxbatch.model.DatiProduzioneMetadata;
import com.agriflux.agrifluxbatch.model.DatiProduzioneRecord;
import com.agriflux.agrifluxshared.enumeratori.TipoProdottoEnum;

public class DatiProduzioneEnricherProcessor extends DatiProcessor implements ItemProcessor<DatiProduzioneMetadata, DatiProduzioneRecord>{
	
	private final Map<String, Double> cacheVolumeProdotto = new HashMap<String, Double>();
	private final Map<String, BigDecimal> cachePesoMedioProdotto = new HashMap<String, BigDecimal>();
	
	@BeforeProcess
	public void beforeProcess() {
		
		if (cacheVolumeProdotto.isEmpty()) {
			for (TipoProdottoEnum prodotto : TipoProdottoEnum.values()) {
				cacheVolumeProdotto.put(prodotto.name(), prodotto.getVolumeXMetroQuadro());
			}
		}
		
		if (cachePesoMedioProdotto.isEmpty()) {
			for (TipoProdottoEnum prodotto : TipoProdottoEnum.values()) {
				cachePesoMedioProdotto.put(prodotto.name(), new BigDecimal(prodotto.getPesoMedio()));
			}
		}
	}
	
	@Override
	public DatiProduzioneRecord process(DatiProduzioneMetadata item) throws Exception {
		
		int numLavoratori = item.numLavoratori();
		int tempoSemina = item.tempoSemina();
		int tempoGerminazione = item.tempoGerminazione();
		int tempoTrapianto = item.tempoTrapianto();
		int tempoMaturazione = item.tempoMaturazione();
		int tempoRaccolta = item.tempoRaccolta();
		
		BigDecimal speseAccessorie = generaRandomBigDecimalFromRange(item.speseAccessorie());
		
		long idColtura = item.idColtura();
		long idMorfologia = item.idMorfologia();
		
//		BigDecimal quantitaRaccolto = calcolaQuantitaRaccolto(idColtura, idMorfologia);
//		BigDecimal quantitaRaccoltoVenduto = calcoloQuantitaRaccoltoVenduto(quantitaRaccolto);
//		
//		BigDecimal fatturatoColtura = calcoloFatturatoColtura(quantitaRaccoltoVenduto, idColtura);
//		
//		return new DatiProduzioneRecord(quantitaRaccolto, quantitaRaccoltoVenduto, fatturatoColtura,
//				numLavoratori, speseAccessorie, tempoSemina, tempoGerminazione, tempoTrapianto, 
//				tempoMaturazione, tempoRaccolta, idColtura, idMorfologia);
		return null;
	}

//	private BigDecimal calcoloFatturatoColtura(BigDecimal quantitaRaccoltoVenduto, long fkIdColtura) {
//		
//		BigDecimal fatturatoColtura = null;
//		
//		if (null != getCacheColtura() && !getCacheColtura().isEmpty()) {
//			Coltura coltura = getCacheColtura().get(fkIdColtura);
//			if (null != coltura) {
//				if (null != cachePesoMedioProdotto && !cachePesoMedioProdotto.isEmpty()) {
//					BigDecimal pesoProdotto = cachePesoMedioProdotto.get(coltura.getProdottoColtivato());
//					
//					fatturatoColtura = quantitaRaccoltoVenduto.multiply(pesoProdotto);
//				}
//			}
//		}
//		
//		return fatturatoColtura;
//	}

	private BigDecimal calcoloQuantitaRaccoltoVenduto(BigDecimal quantitaRaccolto) {
		
		double scarto = quantitaRaccolto.doubleValue() / 2.0;
		
		BigDecimal quantitaRaccoltoNonVenduto = generaRandomBigDecimalFromRange(0, scarto);
		
		return quantitaRaccolto.subtract(quantitaRaccoltoNonVenduto);
	}

//	private BigDecimal calcolaQuantitaRaccolto(long fkIdColtura, long fkIdMorfologia) {
//		
//		BigDecimal quantitaRaccolto = null;
//		
//		if (null != getCacheMorfologia() && !getCacheMorfologia().isEmpty()) {
//			Morfologia morfologia = getCacheMorfologia().get(fkIdMorfologia);
//			if (null != morfologia) {
//				BigDecimal estensioneTerreno = morfologia.getEstensioneTerreno();
//				if (null != getCacheColtura() && !getCacheColtura().isEmpty()) {
//					Coltura coltura = getCacheColtura().get(fkIdColtura);
//					if (null != coltura) {
//						String prodottoColtivato = coltura.getProdottoColtivato();
//						if (null != cacheVolumeProdotto && !cacheVolumeProdotto.isEmpty()) {
//							double volumeProdotto = cacheVolumeProdotto.get(prodottoColtivato);
//							
//							quantitaRaccolto = new BigDecimal(estensioneTerreno.doubleValue()*volumeProdotto); 
//						}
//					}
//				}
//			}
//		}
//		
//		return quantitaRaccolto;
//	}
	
}
