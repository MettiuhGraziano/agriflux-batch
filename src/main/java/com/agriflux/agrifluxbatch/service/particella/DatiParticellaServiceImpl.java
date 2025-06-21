package com.agriflux.agrifluxbatch.service.particella;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.agriflux.agrifluxbatch.entity.Particella;
import com.agriflux.agrifluxbatch.repository.DatiParticellaRepository;
import com.agriflux.agrifluxbatch.repository.projection.particella.ParticellaColturaRilevazioneProjection;
import com.agriflux.agrifluxbatch.repository.projection.particella.ParticellaIdAnnoProjection;
import com.agriflux.agrifluxshared.dto.particella.DatiParticellaDTO;
import com.agriflux.agrifluxshared.dto.terreno.ParticellaColturaTerrenoDTO;
import com.agriflux.agrifluxshared.service.particella.DatiParticellaService;

@Service
public class DatiParticellaServiceImpl implements DatiParticellaService {
	
	private final DatiParticellaRepository datiParticellaRepository;
	
	private final ModelMapper mapper;
	
	private static final String ID_PARTICELLA = "idParticella";
	
	DatiParticellaServiceImpl(DatiParticellaRepository datiParticellaRepository, ModelMapper mapper) {
		this.datiParticellaRepository = datiParticellaRepository;
		this.mapper = mapper;
	}
	
	public List<DatiParticellaDTO> findAllParticellaIdAnno() {
		
		List<DatiParticellaDTO> dtoList = new ArrayList<DatiParticellaDTO>();
		
		List<ParticellaIdAnnoProjection> projectionList = datiParticellaRepository.findAllParticellaIdAnnoProjection();
		
		for (ParticellaIdAnnoProjection projection : projectionList) {
			dtoList.add(new DatiParticellaDTO(projection.getIdParticella(), null, null, null,
					projection.getAnnoInstallazione(), null, null, null, null, null));
		}
		
		return dtoList;
	}

	@Override
	public List<DatiParticellaDTO> findAllParticellaSortById() {
		List<DatiParticellaDTO> response = new ArrayList<DatiParticellaDTO>();

		Iterable<Particella> particellaIterator = datiParticellaRepository.findAll(Sort.by(Sort.Direction.ASC, ID_PARTICELLA));

		for (Particella particella : particellaIterator) {
			DatiParticellaDTO particellaDto = mapper.map(particella, DatiParticellaDTO.class);
			response.add(particellaDto);
		}

		return response;
	}

	@Override
	public Map<Long, List<ParticellaColturaTerrenoDTO>> findParticellaJoinColturaTerreno() {
		Map<Long, List<ParticellaColturaTerrenoDTO>> response = new HashMap<Long, List<ParticellaColturaTerrenoDTO>>();

		List<ParticellaColturaRilevazioneProjection> projectionList = datiParticellaRepository.findAllParticellaColturaRilevazioneProjection();
		
		Map<String, List<LocalDate>> projectionGroup = projectionList.stream().collect(Collectors.groupingBy(
				p -> p.getIdParticella() + "-" + p.getIdColtura() + "-" + p.getNomeProdotto(),
				Collectors.mapping(ParticellaColturaRilevazioneProjection::getDataRilevazioneTerreno, Collectors.toList())));

		for (String key : projectionGroup.keySet()) {

			String[] split = key.split("-");
			ParticellaColturaTerrenoDTO dto = new ParticellaColturaTerrenoDTO(split[2], projectionGroup.get(key),
					Long.parseLong(split[1]));

			if (null != response.get(Long.parseLong(split[0]))) {
				response.get(Long.parseLong(split[0])).add(dto);
			} else {
				List<ParticellaColturaTerrenoDTO> dtoList = new ArrayList<ParticellaColturaTerrenoDTO>();
				dtoList.add(dto);
				response.put(Long.parseLong(split[0]), dtoList);
			}
		}

		return response;
	}
	
}
