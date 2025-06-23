package com.agriflux.agrifluxbatch.controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.agriflux.agrifluxbatch.service.AgrifluxDataServiceImpl;
import com.agriflux.agrifluxshared.dto.ambiente.AmbienteDTO;
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

}
