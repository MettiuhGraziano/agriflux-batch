package com.agriflux.agrifluxbatch.processor;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import org.springframework.batch.core.annotation.BeforeStep;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.agriflux.agrifluxbatch.entity.Coltura;
import com.agriflux.agrifluxbatch.repository.DatiColturaRepository;
import com.agriflux.agrifluxshared.dto.ortaggio.OrtaggioRangeStagioneSumDTO;
import com.agriflux.agrifluxshared.service.DatiOrtaggioService;

@Component
public class DatiProcessor {
	
	@Autowired
	private DatiColturaRepository colturaRepository;
	
	@Autowired
	private DatiOrtaggioService ortaggioService;
	
	private static final String ID_COLTURA = "idColtura";
	private static final String DELIMITER = "|";
	
	private static Random random = new Random();
	
	private static final Map<Long, Coltura> cacheColtura = new HashMap<Long, Coltura>();
	private static final Map<Long, OrtaggioRangeStagioneSumDTO> cacheOrtaggio = new HashMap<Long, OrtaggioRangeStagioneSumDTO>();
	
	@BeforeStep
	public void init() {
		
//		if (cacheColtura.isEmpty()) {
//			Iterable<Coltura> colture = colturaRepository.findAll(Sort.by(Sort.Direction.ASC, ID_COLTURA));
//
//			if (null != colture && colture.iterator().hasNext()) {
//				for (Coltura coltura : colture) {
//					cacheColtura.put(coltura.getIdColtura(), coltura);
//				}
//			}
//		}
//		
		if (cacheOrtaggio.isEmpty()) {
			List<OrtaggioRangeStagioneSumDTO> ortaggioDtoList = ortaggioService.findAllOrtaggioRangeStagione();
			
			if (null != ortaggioDtoList && !ortaggioDtoList.isEmpty()) {
				for (OrtaggioRangeStagioneSumDTO ortaggioDTO : ortaggioDtoList) {
					cacheOrtaggio.put(ortaggioDTO.getIdOrtaggio(), ortaggioDTO);
				}
			}
		}
		
	}
	
	public Map<Long, Coltura> getCacheColtura() {
		return cacheColtura;
	}
	
	public Map<Long, OrtaggioRangeStagioneSumDTO> getCacheOrtaggio() {
		return cacheOrtaggio;
	}
	
	protected static BigDecimal generaRandomBigDecimalFromRange(String range) {

		int delimiterIndex = range.indexOf(DELIMITER);
		String rangeMin = range.substring(0, delimiterIndex);
		String rangeMax = range.substring(delimiterIndex + 1);
		
		double min = 0;
		double max = 0;
		
		if (null != rangeMin && null != rangeMax) {
			min = Double.parseDouble(rangeMin);
			max = Double.parseDouble(rangeMax);
		}
		
		double randomValue = random.nextDouble(min, max);
		
		return BigDecimal.valueOf(randomValue);
	}
	
	protected static BigDecimal generaRandomBigDecimalFromRange(double min, double max) {

		double randomValue = random.nextDouble(min, max);
		
		return BigDecimal.valueOf(randomValue);
	}
	
	protected static int generaRandomIntFromRange(int min, int max) {
		return random.nextInt(min, max);
	}
	
	protected BigDecimal applicaVariazioneDistribuzione(BigDecimal valoreIniziale) {
		
		BigDecimal limiteVariazioneSuperiore = valoreIniziale.divide(new BigDecimal(4));
		BigDecimal limiteVariazioneinferiore = limiteVariazioneSuperiore.negate();
		
		double variazione = random.nextDouble(limiteVariazioneinferiore.doubleValue(), limiteVariazioneSuperiore.doubleValue());
		
		return valoreIniziale.add(new BigDecimal(variazione));
	}
}
