package com.agriflux.agrifluxbatch.processor;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.agriflux.agrifluxshared.dto.ortaggio.OrtaggioRangeStagioneSumDTO;
import com.agriflux.agrifluxshared.dto.particella.DatiParticellaDTO;
import com.agriflux.agrifluxshared.dto.produzione.ProduzioneJoinColturaFatturatoDTO;
import com.agriflux.agrifluxshared.service.ortaggio.DatiOrtaggioService;
import com.agriflux.agrifluxshared.service.particella.DatiParticellaService;
import com.agriflux.agrifluxshared.service.produzione.DatiProduzioneService;

@Component
public class DatiProcessor {
	
	@Autowired
	protected DatiOrtaggioService ortaggioService;
	
	@Autowired
	protected DatiParticellaService particellaService;
	
	@Autowired
	protected DatiProduzioneService produzioneService;
	
	private static final String DELIMITER = "|";
	
	private static final Random random = new Random();
	
	protected static final Map<Long, OrtaggioRangeStagioneSumDTO> cacheOrtaggio = new HashMap<Long, OrtaggioRangeStagioneSumDTO>();
	protected static final Map<Long, DatiParticellaDTO> cacheParticella = new HashMap<Long, DatiParticellaDTO>();
	protected static Map<Long, List<Map<Integer, ProduzioneJoinColturaFatturatoDTO>>> cacheParticellaProduzione = new HashMap<Long, List<Map<Integer, ProduzioneJoinColturaFatturatoDTO>>>();
	
//	@BeforeStep
//	public void init() {
//
//		if (cacheOrtaggio.isEmpty()) {
//			List<OrtaggioRangeStagioneSumDTO> ortaggioDtoList = ortaggioService.findAllOrtaggioRangeStagione();
//
//			if (null != ortaggioDtoList && !ortaggioDtoList.isEmpty()) {
//				for (OrtaggioRangeStagioneSumDTO ortaggioDTO : ortaggioDtoList) {
//					cacheOrtaggio.put(ortaggioDTO.getIdOrtaggio(), ortaggioDTO);
//				}
//			}
//		}
//		
//		if (cacheParticella.isEmpty()) {
//			List<DatiParticellaDTO> particellaDtoList = particellaService.findAllParticellaIdAnno();
//			
//			if (null != particellaDtoList && !particellaDtoList.isEmpty()) {
//				for (DatiParticellaDTO datiParticellaDTO : particellaDtoList) {
//					cacheParticella.put(datiParticellaDTO.getIdParticella(), datiParticellaDTO);
//				}
//			}
//		}
//		
//		if (cacheParticellaProduzione.isEmpty()) {
//			Map<Long, Map<Integer, ProduzioneJoinColturaFatturatoDTO>> particellaMap = produzioneService.findProduzioneJoinColturaFatturato();
//		
//			if (null != particellaMap && !particellaMap.isEmpty()) {
//				cacheParticellaProduzione = particellaMap;
//			}
//		}
//		
//	}
	
//	public Map<Long, OrtaggioRangeStagioneSumDTO> getCacheOrtaggio() {
//		return cacheOrtaggio;
//	}
//	
//	public Map<Long, DatiParticellaDTO> getCacheParticella() {
//		return cacheParticella;
//	}
//	
//	public Map<Long, Map<Integer, ProduzioneJoinColturaFatturatoDTO>> getCacheParticellaProduzione() {
//		return cacheParticellaProduzione;
//	}
	
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
	
	protected LocalDateTime generaDataRandomFromRange(LocalDateTime dataMin, LocalDateTime dataMax) {
		long giorniInclusi = ChronoUnit.DAYS.between(dataMin, dataMax);
        long giornateRandom = random.nextInt((int) Math.abs(giorniInclusi) + 1);
        
        return dataMin.plusDays(giornateRandom);
	}
	
}
