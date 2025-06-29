package com.agriflux.agrifluxbatch.controller.azienda;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.agriflux.agrifluxshared.dto.azienda.AziendaDTO;
import com.agriflux.agrifluxshared.service.azienda.DatiAziendaService;

@RestController("api/data/azienda")
public class DatiAziendaController implements DatiAziendaService {

	private final DatiAziendaService datiAziendaService;
	
	DatiAziendaController(DatiAziendaService datiAziendaService) {
		this.datiAziendaService = datiAziendaService;
	}
	
	@Override
	@GetMapping("/findAzienda")
	public AziendaDTO findAzienda() {
		return datiAziendaService.findAzienda();
	}

}
