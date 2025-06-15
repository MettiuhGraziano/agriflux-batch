package com.agriflux.agrifluxbatch.controller;

import java.util.List;
import java.util.Map;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.agriflux.agrifluxbatch.service.AgrifluxDataServiceImpl;
import com.agriflux.agrifluxshared.dto.AmbienteDTO;
import com.agriflux.agrifluxshared.dto.ColturaDTO;
import com.agriflux.agrifluxshared.dto.ColturaGroupByProdottoDTO;
import com.agriflux.agrifluxshared.dto.ColturaListPrezzoDataRaccoltoDTO;
import com.agriflux.agrifluxshared.dto.MorfologiaDTO;
import com.agriflux.agrifluxshared.dto.ProduzioneColturaDTO;
import com.agriflux.agrifluxshared.dto.ProduzioneColturaTempiDTO;
import com.agriflux.agrifluxshared.dto.ProduzioneDTO;
import com.agriflux.agrifluxshared.dto.ProduzioneMorfologiaColturaDTO;
import com.agriflux.agrifluxshared.dto.TerrenoDTO;
import com.agriflux.agrifluxshared.dto.TerrenoMorfologiaColturaDTO;
import com.agriflux.agrifluxshared.service.AgrifluxDataService;

@RestController("api/data")
public class AgrifluxDataController implements AgrifluxDataService{
	
	private final AgrifluxDataServiceImpl agrifluxDataServiceImpl;
	
	public AgrifluxDataController(AgrifluxDataServiceImpl agrifluxDataServiceImpl) {
		this.agrifluxDataServiceImpl = agrifluxDataServiceImpl;
	}
	
	@Override
	@GetMapping("/datiColture")
	public List<ColturaDTO> findAllColturaSortById(){
		return agrifluxDataServiceImpl.findAllColturaSortById();
	}

	@Override
	@GetMapping("/datiAmbientali")
	public List<AmbienteDTO> findAllAmbienteSortById() {
		return agrifluxDataServiceImpl.findAllAmbienteSortById();
	}

	@Override
	@GetMapping("/datiMorfologici")
	public List<MorfologiaDTO> findAllMorfologiaSortById() {
		return agrifluxDataServiceImpl.findAllMorfologiaSortById();
	}

	@Override
	@GetMapping("/datiTerreni")
	public List<TerrenoDTO> findAllTerrenoSortById() {
		return agrifluxDataServiceImpl.findAllTerrenoSortById();
	}

	@Override
	@GetMapping("/datiProduzione")
	public List<ProduzioneDTO> findAllProduzioneSortById() {
		return agrifluxDataServiceImpl.findAllProduzioneSortById();
	}

	@Override
	@GetMapping("/listColtureGroupByProdotto")
	public List<ColturaGroupByProdottoDTO> countColtureGroupByProdotto() {
		return agrifluxDataServiceImpl.countColtureGroupByProdotto();
	}

	@Override
	@GetMapping("/findPrezziAndDateRaccoltoColtura")
	public Map<String, ColturaListPrezzoDataRaccoltoDTO> findPrezziAndDateColtura() {
		return agrifluxDataServiceImpl.findPrezziAndDateColtura();
	}

	@Override
	@GetMapping("/findColtureJoinProduzione")
	public Map<String, Map<String, ProduzioneColturaDTO>> findColtureJoinProduzione() {
		return agrifluxDataServiceImpl.findColtureJoinProduzione();
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

	@Override
	@GetMapping("/findTerrenoJoinColturaMorfologia")
	public Map<Long, List<TerrenoMorfologiaColturaDTO>> findTerrenoJoinColturaMorfologia() {
		return agrifluxDataServiceImpl.findTerrenoJoinColturaMorfologia();
	}
}
