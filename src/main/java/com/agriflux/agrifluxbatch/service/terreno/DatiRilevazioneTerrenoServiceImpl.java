package com.agriflux.agrifluxbatch.service.terreno;

import java.util.ArrayList;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.agriflux.agrifluxbatch.entity.Terreno;
import com.agriflux.agrifluxbatch.repository.DatiRilevazioneTerrenoRepository;
import com.agriflux.agrifluxshared.dto.terreno.TerrenoDTO;
import com.agriflux.agrifluxshared.service.terreno.DatiRilevazioneTerrenoService;

@Service
public class DatiRilevazioneTerrenoServiceImpl implements DatiRilevazioneTerrenoService {
	
	private final DatiRilevazioneTerrenoRepository datiRilevazioneTerrenoRepository;
	
	private final ModelMapper mapper;
	
	private static final String ID_RILEVAZIONE_TERRENO = "idRilevazioneTerreno";
	
	DatiRilevazioneTerrenoServiceImpl(ModelMapper mapper, DatiRilevazioneTerrenoRepository datiRilevazioneTerrenoRepository) {
		this.datiRilevazioneTerrenoRepository = datiRilevazioneTerrenoRepository;
		this.mapper = mapper;
	}
	
	@Override
	public List<TerrenoDTO> findAllRilevazioneTerrenoSortById() {
		List<TerrenoDTO> terrenoList = new ArrayList<TerrenoDTO>();

		Iterable<Terreno> terrenoIterator = datiRilevazioneTerrenoRepository
				.findAll(Sort.by(Sort.Direction.ASC, ID_RILEVAZIONE_TERRENO));

		for (Terreno terreno : terrenoIterator) {
			TerrenoDTO terrenoDTO = mapper.map(terreno, TerrenoDTO.class);
			terrenoList.add(terrenoDTO);
		}

		return terrenoList;
	}

}
