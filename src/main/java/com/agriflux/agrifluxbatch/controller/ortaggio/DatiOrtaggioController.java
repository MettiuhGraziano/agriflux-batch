package com.agriflux.agrifluxbatch.controller.ortaggio;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.agriflux.agrifluxshared.dto.ortaggio.OrtaggioDTO;
import com.agriflux.agrifluxshared.dto.ortaggio.OrtaggioRangeStagioneSumDTO;
import com.agriflux.agrifluxshared.service.ortaggio.DatiOrtaggioService;

@RestController("api/data/ortaggio")
public class DatiOrtaggioController implements DatiOrtaggioService {
	
	private final DatiOrtaggioService datiOrtaggioService;
	
	DatiOrtaggioController(DatiOrtaggioService datiOrtaggioService) {
		this.datiOrtaggioService = datiOrtaggioService;
	}
	
	@Override
	@GetMapping("/findAllOrtaggioSortById")
	public List<OrtaggioDTO> findAllOrtaggioSortById() {
		return datiOrtaggioService.findAllOrtaggioSortById();
	}

	@Override
	@GetMapping("/findAllOrtaggioRangeStagione")
	public List<OrtaggioRangeStagioneSumDTO> findAllOrtaggioRangeStagione() {
		return datiOrtaggioService.findAllOrtaggioRangeStagione();
	}

}
