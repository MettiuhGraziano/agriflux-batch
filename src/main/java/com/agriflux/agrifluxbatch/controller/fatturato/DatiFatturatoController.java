package com.agriflux.agrifluxbatch.controller.fatturato;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.agriflux.agrifluxshared.dto.fatturato.FatturatoDTO;
import com.agriflux.agrifluxshared.service.fatturato.DatiFatturatoService;

@RestController("api/data/fatturato")
public class DatiFatturatoController implements DatiFatturatoService {
	
	private final DatiFatturatoService datiFatturatoService;
	
	public DatiFatturatoController(DatiFatturatoService datiFatturatoService) {
		this.datiFatturatoService = datiFatturatoService;
	}
	
	@Override
	@GetMapping("/findAllFatturatoSortById")
	public List<FatturatoDTO> findAllFatturatoSortById() {
		return datiFatturatoService.findAllFatturatoSortById();
	}

}
