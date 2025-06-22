package com.agriflux.agrifluxbatch.controller;

import java.util.List;
import java.util.Map;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.agriflux.agrifluxbatch.service.AgrifluxDataServiceImpl;
import com.agriflux.agrifluxshared.dto.ambiente.AmbienteDTO;
import com.agriflux.agrifluxshared.dto.produzione.ProduzioneColturaTempiDTO;
import com.agriflux.agrifluxshared.dto.produzione.ProduzioneMorfologiaColturaDTO;
import com.agriflux.agrifluxshared.service.AgrifluxDataService;

@RestController("api/data")
public class AgrifluxDataController implements AgrifluxDataService{
	
	private final AgrifluxDataServiceImpl agrifluxDataServiceImpl;
	
	public AgrifluxDataController(AgrifluxDataServiceImpl agrifluxDataServiceImpl) {
		this.agrifluxDataServiceImpl = agrifluxDataServiceImpl;
	}
	
	@Override
	@GetMapping("/datiAmbientali")
	public List<AmbienteDTO> findAllAmbienteSortById() {
		return agrifluxDataServiceImpl.findAllAmbienteSortById();
	}

	@Override
	@GetMapping("/findProduzioneTempiJoinColtura")
	public Map<String, List<ProduzioneColturaTempiDTO>> findProduzioneTempiJoinColtura() {
		return agrifluxDataServiceImpl.findProduzioneTempiJoinColtura();
	}

	@Override
	@GetMapping("/findProduzioneJoinColturaMorfologia")
	public Map<Long, ProduzioneMorfologiaColturaDTO> findProduzioneJoinColturaMorfologia() {
		return agrifluxDataServiceImpl.findProduzioneJoinColturaMorfologia();
	}

}
