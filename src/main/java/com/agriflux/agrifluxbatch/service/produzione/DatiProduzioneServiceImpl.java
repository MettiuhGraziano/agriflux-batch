package com.agriflux.agrifluxbatch.service.produzione;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.agriflux.agrifluxbatch.repository.DatiProduzioneRepository;
import com.agriflux.agrifluxbatch.repository.projection.ProduzioneJoinColturaFatturatoProjection;
import com.agriflux.agrifluxshared.dto.produzione.ProduzioneJoinColturaFatturatoDTO;
import com.agriflux.agrifluxshared.service.produzione.DatiProduzioneService;

@Service
public class DatiProduzioneServiceImpl implements DatiProduzioneService {
	
	private final DatiProduzioneRepository datiProduzioneRepository;
	
	DatiProduzioneServiceImpl(DatiProduzioneRepository datiProduzioneRepository) {
		this.datiProduzioneRepository = datiProduzioneRepository;
	}

	@Override
	public Map<Long, List<Map<Integer, ProduzioneJoinColturaFatturatoDTO>>> findProduzioneJoinColturaFatturato() {
		
		Map<Long, List<Map<Integer, ProduzioneJoinColturaFatturatoDTO>>> response = new HashMap<Long, List<Map<Integer, ProduzioneJoinColturaFatturatoDTO>>>();
		
		List<ProduzioneJoinColturaFatturatoProjection> projectionList = datiProduzioneRepository.findProduzioneJoinColturaFatturatoProjection();
		
		//Lista di idParticella distinte
		List<Long> listaIdParticella = projectionList.stream()
				.map(ProduzioneJoinColturaFatturatoProjection::getIdParticella).distinct().collect(Collectors.toList());

		//Lista di anni distinti
		List<Integer> listaAnnoRaccolto = projectionList.stream()
				.map(ProduzioneJoinColturaFatturatoProjection::getAnnoRaccolto).distinct().collect(Collectors.toList());
		
		for (Long idParticella : listaIdParticella) {
			List<Map<Integer, ProduzioneJoinColturaFatturatoDTO>> mapList = new ArrayList<Map<Integer, ProduzioneJoinColturaFatturatoDTO>>();
				for (Integer annoRaccolto : listaAnnoRaccolto) {
					Map<Integer, ProduzioneJoinColturaFatturatoDTO> map = new HashMap<Integer, ProduzioneJoinColturaFatturatoDTO>();
					double fatturatoRaccolto = projectionList.stream()
							.filter(p -> p.getIdParticella() == idParticella && p.getAnnoRaccolto() == annoRaccolto)
							.mapToDouble(ProduzioneJoinColturaFatturatoProjection::getFatturatoRaccolto)
							.sum();
							
					double speseProduzione = projectionList.stream()
							.filter(p -> p.getIdParticella() == idParticella && p.getAnnoRaccolto() == annoRaccolto)
							.mapToDouble(ProduzioneJoinColturaFatturatoProjection::getSpeseProduzione)
							.sum();
					
					if (fatturatoRaccolto == 0 && speseProduzione == 0) {
						continue;
					}
					
					map.put(annoRaccolto, new ProduzioneJoinColturaFatturatoDTO(new BigDecimal(fatturatoRaccolto), new BigDecimal(speseProduzione)));					
					mapList.add(map);
				}
				
				response.put(idParticella, mapList);
			}
			
		return response;
	}

}
