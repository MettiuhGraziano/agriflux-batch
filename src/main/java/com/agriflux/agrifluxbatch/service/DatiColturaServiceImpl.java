package com.agriflux.agrifluxbatch.service;

import java.util.ArrayList;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.agriflux.agrifluxbatch.entity.Coltura;
import com.agriflux.agrifluxbatch.entity.ColturaDTO;
import com.agriflux.agrifluxbatch.repository.ColturaRepository;

@Service
public class DatiColturaServiceImpl implements DatiColturaService{
	
	private static final String ID_COLTURA = "idColtura";
	
	private final ColturaRepository colturaRepository;
	private final ModelMapper mapper;
	
	DatiColturaServiceImpl(ColturaRepository colturaRepository, ModelMapper mapper) {
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
