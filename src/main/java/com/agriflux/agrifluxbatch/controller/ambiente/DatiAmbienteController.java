package com.agriflux.agrifluxbatch.controller.ambiente;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.agriflux.agrifluxshared.dto.ambiente.AmbienteDTO;
import com.agriflux.agrifluxshared.service.ambiente.DatiAmbienteService;

@RestController("api/data/ambiente")
public class DatiAmbienteController implements DatiAmbienteService {
	
	private final DatiAmbienteService datiAmbienteService;
	
	DatiAmbienteController(DatiAmbienteService datiAmbienteService) {
		this.datiAmbienteService = datiAmbienteService;
	}
	
	@Override
	@GetMapping("/findAllAmbienteSortById")
	public List<AmbienteDTO> findAllAmbienteSortById() {
		return datiAmbienteService.findAllAmbienteSortById();
	}

}
