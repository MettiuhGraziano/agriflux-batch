package com.agriflux.agrifluxbatch.service;

import java.util.ArrayList;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.agriflux.agrifluxbatch.entity.Coltura;
import com.agriflux.agrifluxbatch.repository.ColturaRepository;
import com.agriflux.agrifluxshared.dto.ColturaDTO;
import com.agriflux.agrifluxshared.service.AgrifluxDataService;

@Service
public class AgrifluxDataServiceImpl implements AgrifluxDataService{
	
	private static final String ID_COLTURA = "idColtura";
	
	private final ColturaRepository colturaRepository;
	private final ModelMapper mapper;
	
	AgrifluxDataServiceImpl(ColturaRepository colturaRepository, ModelMapper mapper) {
		this.colturaRepository = colturaRepository;
		this.mapper = mapper;
	}

	@Override
	public List<ColturaDTO> findAllColturaSortById() {
		
		List<ColturaDTO> colturaList = new ArrayList<ColturaDTO>();
		
		Iterable<Coltura> colturaIterator = colturaRepository.findAll(Sort.by(Sort.Direction.ASC, ID_COLTURA));
		
		for (Coltura coltura : colturaIterator) {
			ColturaDTO colturaDTO = mapper.map(coltura, ColturaDTO.class);
			colturaList.add(colturaDTO);
		}
		
		return colturaList;
	}

}
