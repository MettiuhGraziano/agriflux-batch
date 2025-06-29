package com.agriflux.agrifluxbatch.service.azienda;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import com.agriflux.agrifluxbatch.entity.Azienda;
import com.agriflux.agrifluxbatch.repository.DatiAziendaRepository;
import com.agriflux.agrifluxshared.dto.azienda.AziendaDTO;
import com.agriflux.agrifluxshared.service.azienda.DatiAziendaService;

@Service
public class DatiAziendaServiceImpl implements DatiAziendaService {
	
	private final DatiAziendaRepository datiAziendaRepository;
	
	private final ModelMapper mapper;
	
	public DatiAziendaServiceImpl(DatiAziendaRepository datiAziendaRepository, ModelMapper mapper) {
		this.datiAziendaRepository = datiAziendaRepository;
		this.mapper = mapper;
	}
	
	@Override
	public AziendaDTO findAzienda() {
		
		AziendaDTO aziendaDto = null;
		
		Azienda azienda = datiAziendaRepository.findAziendaByIdAzienda(1L);
		
		if (null != azienda) {
			aziendaDto = mapper.map(azienda, AziendaDTO.class);
		}
		
		return aziendaDto;
	}

}
