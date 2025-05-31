package com.agriflux.agrifluxbatch.controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.agriflux.agrifluxbatch.entity.ColturaDTO;
import com.agriflux.agrifluxbatch.service.DatiColturaServiceImpl;

@RestController("api/services")
public class AgrifluxDataController {
	
	private final DatiColturaServiceImpl datiColturaServiceImpl;
	
	public AgrifluxDataController(DatiColturaServiceImpl datiColturaServiceImpl) {
		this.datiColturaServiceImpl = datiColturaServiceImpl;
	}
	
	@GetMapping("/colture")
	public List<ColturaDTO> getAllColtureSortById(){
		return datiColturaServiceImpl.findAllColturaSortById();
	}
}
