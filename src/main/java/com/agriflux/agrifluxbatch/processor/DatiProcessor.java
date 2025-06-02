package com.agriflux.agrifluxbatch.processor;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import org.springframework.batch.core.annotation.BeforeStep;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;

import com.agriflux.agrifluxbatch.entity.Coltura;
import com.agriflux.agrifluxbatch.entity.Morfologia;
import com.agriflux.agrifluxbatch.repository.DatiColturaRepository;
import com.agriflux.agrifluxbatch.repository.DatiMorfologiciRepository;

@Component
public class DatiProcessor {
	
	@Autowired
	private DatiColturaRepository colturaRepository;
	
	@Autowired
	private DatiMorfologiciRepository datiMorfologiciRepository;
	
	private static final String ID_COLTURA = "idColtura";
	private static final String ID_MORFOLOGIA = "idMorfologia";
	private static final String DELIMITER = "|";
	private static Random random = new Random();
	
	private static final Map<Long, Coltura> cacheColtura = new HashMap<Long, Coltura>();
	private static final Map<Long, Morfologia> cacheMorfologia = new HashMap<Long, Morfologia>();
	
	@BeforeStep
	public void init() {
		
		if (cacheColtura.isEmpty()) {
			Iterable<Coltura> colture = colturaRepository.findAll(Sort.by(Sort.Direction.ASC, ID_COLTURA));

			if (null != colture && colture.iterator().hasNext()) {
				for (Coltura coltura : colture) {
					cacheColtura.put(coltura.getIdColtura(), coltura);
				}
			}
		}
		
		if (cacheMorfologia.isEmpty()) {
			Iterable<Morfologia> morfologie = datiMorfologiciRepository.findAll(Sort.by(Sort.Direction.ASC, ID_MORFOLOGIA));

			if (null != morfologie && morfologie.iterator().hasNext()) {
				for (Morfologia morfologia : morfologie) {
					cacheMorfologia.put(morfologia.getIdMorfologia(), morfologia);
				}
			}
		}
	}
	
	public Map<Long, Coltura> getCacheColtura() {
		return cacheColtura;
	}
	
	public static Map<Long, Morfologia> getCacheMorfologia() {
		return cacheMorfologia;
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
}
