package com.agriflux.agrifluxbatch.controller.terreno;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.agriflux.agrifluxshared.dto.terreno.TerrenoDTO;
import com.agriflux.agrifluxshared.service.terreno.DatiRilevazioneTerrenoService;

@RestController("api/data/terreno")
public class DatiRilevazioneTerrenoController implements DatiRilevazioneTerrenoService {
	
	private final DatiRilevazioneTerrenoService datiRilevazioneTerrenoService;
	
	DatiRilevazioneTerrenoController(DatiRilevazioneTerrenoService datiRilevazioneTerrenoService) {
		this.datiRilevazioneTerrenoService = datiRilevazioneTerrenoService;
	}
	
	@Override
	@GetMapping("/findAllRilevazioneTerrenoSortById")
	public List<TerrenoDTO> findAllRilevazioneTerrenoSortById() {
		return datiRilevazioneTerrenoService.findAllRilevazioneTerrenoSortById();
	}

}
