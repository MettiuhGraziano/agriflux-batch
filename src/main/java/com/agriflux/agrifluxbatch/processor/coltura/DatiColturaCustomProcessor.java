package com.agriflux.agrifluxbatch.processor.coltura;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.batch.core.annotation.BeforeProcess;
import org.springframework.batch.item.ItemProcessor;

import com.agriflux.agrifluxbatch.model.coltura.DatiColturaRecord;
import com.agriflux.agrifluxbatch.model.particella.DatiParticellaRecordReduce;
import com.agriflux.agrifluxbatch.processor.DatiProcessor;
import com.agriflux.agrifluxshared.dto.ortaggio.OrtaggioRangeStagioneSumDTO;

public class DatiColturaCustomProcessor extends DatiProcessor implements ItemProcessor<DatiParticellaRecordReduce, List<DatiColturaRecord>> {
	
	private final Map<Long, LocalDateTime> cacheDateParticella = new HashMap<Long, LocalDateTime>();
	
	private boolean counterParticella = false;
	
	@BeforeProcess
	public void init() {
		if (cacheOrtaggio.isEmpty()) {
			List<OrtaggioRangeStagioneSumDTO> ortaggioDtoList = ortaggioService.findAllOrtaggioRangeStagione();

			if (null != ortaggioDtoList && !ortaggioDtoList.isEmpty()) {
				for (OrtaggioRangeStagioneSumDTO ortaggioDTO : ortaggioDtoList) {
					cacheOrtaggio.put(ortaggioDTO.getIdOrtaggio(), ortaggioDTO);
				}
			}
		}
		
		if (cacheDatiEconomiciOrtaggio.isEmpty()) {
			cacheDatiEconomiciOrtaggio = datoEconomicoService.findDatoEconomicoOrtaggio();
		}
	}
	
	@Override
	public List<DatiColturaRecord> process(DatiParticellaRecordReduce item) throws Exception {
		
		List<DatiColturaRecord> response = new ArrayList<DatiColturaRecord>();
		
		if (!cacheOrtaggio.isEmpty()) {
			while (!counterParticella) {

				if (null != cacheDateParticella.get(item.idParticella()) && !cacheDateParticella.isEmpty()) {
					generateColtura(item, response);
				} else {
					LocalDateTime dataPrimaSemina = LocalDateTime.of(Integer.parseInt(item.annoInstallazione()), 2, 10, 0, 0);
					
					cacheDateParticella.put(item.idParticella(), dataPrimaSemina);
					
					generateColtura(item, response);
				}
			}
			counterParticella = false;
		}
			
		return response;
	}

	private void generateColtura(DatiParticellaRecordReduce item, List<DatiColturaRecord> response) {

		if (!cacheOrtaggio.isEmpty()) {
			long idOrtaggio = generaRandomIntFromRange(1, cacheOrtaggio.keySet().size() + 1);
			OrtaggioRangeStagioneSumDTO ortaggio = cacheOrtaggio.get(idOrtaggio);

			int meseSeminaMin = Integer.parseInt(ortaggio.getMeseSeminaMin());
			int meseSeminaMax = Integer.parseInt(ortaggio.getMeseSeminaMax());
			
			LocalDateTime dataUltimoRaccolto = cacheDateParticella.get(item.idParticella());
			
			int mese = dataUltimoRaccolto.getMonthValue();

			while (!(mese >= meseSeminaMin && mese <= meseSeminaMax)) {
				idOrtaggio = generaRandomIntFromRange(1, cacheOrtaggio.keySet().size() + 1);
				ortaggio = cacheOrtaggio.get(idOrtaggio);
				meseSeminaMin = Integer.parseInt(ortaggio.getMeseSeminaMin());
				meseSeminaMax = Integer.parseInt(ortaggio.getMeseSeminaMax());
			}
			
			LocalDateTime dataSeminaColtura = dataUltimoRaccolto;
			LocalDateTime dataRaccoltoColtura = dataSeminaColtura.plusDays(ortaggio.getGiorniSeminaRaccolto());
			
			LocalDateTime dataOdierna = LocalDateTime.now();
			if (dataRaccoltoColtura.isBefore(dataOdierna)) {
				
				BigDecimal prezzoKg = null;
				
				for (Map<Integer, BigDecimal> mapCache : cacheDatiEconomiciOrtaggio.get(idOrtaggio)) {
					if (null != mapCache.get(dataRaccoltoColtura.getYear())) {
						prezzoKg = applicaVariazioneDistribuzione(mapCache.get(dataRaccoltoColtura.getYear()));
						break;
					} else {
						continue;
					}
				}
				
				response.add(new DatiColturaRecord(prezzoKg, dataSeminaColtura,
						dataRaccoltoColtura, item.idParticella(), idOrtaggio));
				
				dataRaccoltoColtura.plusDays(7);
				cacheDateParticella.put(item.idParticella(), dataRaccoltoColtura);

			} else {
				counterParticella = true;
			}
		}
	}

}
