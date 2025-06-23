package com.agriflux.agrifluxbatch.service;

import java.util.ArrayList;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.agriflux.agrifluxbatch.entity.Ambiente;
import com.agriflux.agrifluxbatch.repository.DatiAmbientaliRepository;
import com.agriflux.agrifluxshared.dto.ambiente.AmbienteDTO;
import com.agriflux.agrifluxshared.service.AgrifluxDataService;

@Service
public class AgrifluxDataServiceImpl implements AgrifluxDataService{
	
	private static final String ID_AMBIENTE = "idAmbiente";
	
	private final DatiAmbientaliRepository datiAmbientaliRepository;
	
	private final ModelMapper mapper;
	
	AgrifluxDataServiceImpl(DatiAmbientaliRepository datiAmbientaliRepository,
			 
			ModelMapper mapper) {
		this.datiAmbientaliRepository = datiAmbientaliRepository;

		this.mapper = mapper;
	}

	@Override
	public List<AmbienteDTO> findAllAmbienteSortById() {
		
		List<AmbienteDTO> ambienteList = new ArrayList<AmbienteDTO>();
		
		Iterable<Ambiente> ambienteIterator = datiAmbientaliRepository.findAll(Sort.by(Sort.Direction.ASC, ID_AMBIENTE));
		
		for (Ambiente ambiente : ambienteIterator) {
			AmbienteDTO ambienteDTO = mapper.map(ambiente, AmbienteDTO.class);
			ambienteList.add(ambienteDTO);
		}
		
		return ambienteList;
	}

}
