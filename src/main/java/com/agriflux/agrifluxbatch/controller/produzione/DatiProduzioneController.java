package com.agriflux.agrifluxbatch.controller.produzione;

import java.util.List;
import java.util.Map;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.agriflux.agrifluxshared.dto.produzione.ProduzioneColturaDTO;
import com.agriflux.agrifluxshared.dto.produzione.ProduzioneColturaTempiDTO;
import com.agriflux.agrifluxshared.dto.produzione.ProduzioneDTO;
import com.agriflux.agrifluxshared.service.produzione.DatiProduzioneService;

@RestController("api/data/produzione")
public class DatiProduzioneController implements DatiProduzioneService {
	
	private final DatiProduzioneService datiProduzioneService;
	
	public DatiProduzioneController(DatiProduzioneService datiProduzioneService) {
		this.datiProduzioneService = datiProduzioneService;
	}
	
	@Override
	@GetMapping("/findAllProduzioneSortById")
	public List<ProduzioneDTO> findAllProduzioneSortById() {
		return datiProduzioneService.findAllProduzioneSortById();
	}

	@Override
	@GetMapping("/findProduzioneQuantitaJoinColtura")
	public Map<String, Map<String, ProduzioneColturaDTO>> findProduzioneQuantitaJoinColtura() {
		return datiProduzioneService.findProduzioneQuantitaJoinColtura();
	}

	@Override
	@GetMapping("/findProduzioneJoinColturaTempi")
	public Map<String, List<ProduzioneColturaTempiDTO>> findProduzioneJoinColturaTempi() {
		return datiProduzioneService.findProduzioneJoinColturaTempi();
	}

}
