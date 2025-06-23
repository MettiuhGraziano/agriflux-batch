package com.agriflux.agrifluxbatch.processor;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.agriflux.agrifluxbatch.service.ortaggio.DatiOrtaggioServiceImpl;
import com.agriflux.agrifluxbatch.service.particella.DatiParticellaServiceImpl;
import com.agriflux.agrifluxbatch.service.produzione.DatiProduzioneServiceImpl;
import com.agriflux.agrifluxshared.dto.ortaggio.OrtaggioRangeStagioneSumDTO;
import com.agriflux.agrifluxshared.dto.particella.DatiParticellaDTO;
import com.agriflux.agrifluxshared.dto.produzione.ProduzioneJoinColturaFatturatoDTO;
import com.agriflux.agrifluxshared.service.datoEconomico.DatoEconomicoService;

@Component
public class DatiProcessor {
	
	@Autowired
	protected DatiOrtaggioServiceImpl ortaggioService;
	
	@Autowired
	protected DatiParticellaServiceImpl particellaService;
	
	@Autowired
	protected DatiProduzioneServiceImpl produzioneService;
	
	@Autowired
	protected DatoEconomicoService datoEconomicoService;
	
	private static final String DELIMITER = "|";
	
	private static final Random random = new Random();
	
	protected static final Map<Long, OrtaggioRangeStagioneSumDTO> cacheOrtaggio = new HashMap<Long, OrtaggioRangeStagioneSumDTO>();
	protected static final Map<Long, DatiParticellaDTO> cacheParticella = new HashMap<Long, DatiParticellaDTO>();
	protected static Map<Long, List<Map<Integer, ProduzioneJoinColturaFatturatoDTO>>> cacheParticellaProduzione = new HashMap<Long, List<Map<Integer, ProduzioneJoinColturaFatturatoDTO>>>();
	protected static Map<Long, List<Map<Integer, BigDecimal>>> cacheDatiEconomiciOrtaggio = new HashMap<Long, List<Map<Integer, BigDecimal>>>();
	
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
	
	protected LocalDate generaDataRandomFromRange(LocalDate dataMin, LocalDate dataMax) {
		long giorniInclusi = ChronoUnit.DAYS.between(dataMin, dataMax);
        long giornateRandom = random.nextInt((int) Math.abs(giorniInclusi) + 1);
        
        return dataMin.plusDays(giornateRandom);
	}
	
}
