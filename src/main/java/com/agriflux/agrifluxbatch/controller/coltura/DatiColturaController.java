package com.agriflux.agrifluxbatch.controller.coltura;

import java.util.List;
import java.util.Map;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.agriflux.agrifluxshared.dto.coltura.ColturaDTO;
import com.agriflux.agrifluxshared.dto.coltura.ColturaListPrezzoDataRaccoltoDTO;
import com.agriflux.agrifluxshared.service.coltura.DatiColturaService;

@RestController("api/data/coltura")
public class DatiColturaController implements DatiColturaService {
	
	private final DatiColturaService datiColturaService;
	
	DatiColturaController(DatiColturaService datiColturaService) {
		this.datiColturaService = datiColturaService;
	}
	
	@Override
	@GetMapping("/findAllColturaSortById")
	public List<ColturaDTO> findAllColturaSortById() {
		return datiColturaService.findAllColturaSortById();
	}

	@Override
	@GetMapping("/countOrtaggioColtura")
	public Map<String, Long> countOrtaggioColtura() {
		return datiColturaService.countOrtaggioColtura();
	}

	@Override
	@GetMapping("/countFamigliaOrtaggioColtura")
	public Map<String, Long> countFamigliaOrtaggioColtura() {
		return datiColturaService.countFamigliaOrtaggioColtura();
	}
	
	@Override
	@GetMapping("/findPrezziAndDateRaccoltoColtura")
	public Map<String, ColturaListPrezzoDataRaccoltoDTO> findPrezziAndDateColtura() {
		return datiColturaService.findPrezziAndDateColtura();
	}

}
