package com.agriflux.agrifluxbatch.service.ambiente;

import java.util.ArrayList;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.agriflux.agrifluxbatch.entity.Ambiente;
import com.agriflux.agrifluxbatch.repository.DatiAmbienteRepository;
import com.agriflux.agrifluxshared.dto.ambiente.AmbienteDTO;
import com.agriflux.agrifluxshared.service.ambiente.DatiAmbienteService;

@Service
public class DatiAmbienteServiceImpl implements DatiAmbienteService {
	
	private static final String ID_AMBIENTE = "idAmbiente";
	
	private final DatiAmbienteRepository datiAmbienteRepository;
	
	private final ModelMapper mapper;
	
	DatiAmbienteServiceImpl(ModelMapper mapper, DatiAmbienteRepository datiAmbienteRepository) {
		this.datiAmbienteRepository = datiAmbienteRepository;
		this.mapper = mapper;
	}
	
	@Override
	public List<AmbienteDTO> findAllAmbienteSortById() {
		List<AmbienteDTO> ambienteList = new ArrayList<AmbienteDTO>();

		Iterable<Ambiente> ambienteIterator = datiAmbienteRepository
				.findAll(Sort.by(Sort.Direction.ASC, ID_AMBIENTE));

		for (Ambiente ambiente : ambienteIterator) {
			AmbienteDTO ambienteDTO = mapper.map(ambiente, AmbienteDTO.class);
			ambienteList.add(ambienteDTO);
		}

		return ambienteList;
	}

}
