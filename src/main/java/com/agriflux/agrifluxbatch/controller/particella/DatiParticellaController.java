package com.agriflux.agrifluxbatch.controller.particella;

import java.util.List;
import java.util.Map;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.agriflux.agrifluxshared.dto.particella.DatiParticellaDTO;
import com.agriflux.agrifluxshared.dto.terreno.ParticellaColturaTerrenoDTO;
import com.agriflux.agrifluxshared.service.particella.DatiParticellaService;

@RestController("api/data/particella")
public class DatiParticellaController implements DatiParticellaService {
	
	private final DatiParticellaService datiParticellaService;
	
	public DatiParticellaController(DatiParticellaService datiParticellaService) {
		this.datiParticellaService = datiParticellaService;
	}
	
	@Override
	@GetMapping("/findAllParticellaSortById")
	public List<DatiParticellaDTO> findAllParticellaSortById() {
		return datiParticellaService.findAllParticellaSortById();
	}

	@Override
	@GetMapping("/findParticellaJoinColturaTerreno")
	public Map<Long, List<ParticellaColturaTerrenoDTO>> findParticellaJoinColturaTerreno() {
		return datiParticellaService.findParticellaJoinColturaTerreno();
	}
	
}
