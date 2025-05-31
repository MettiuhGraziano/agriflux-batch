package com.agriflux.agrifluxbatch.service;

import java.util.List;

import com.agriflux.agrifluxbatch.entity.ColturaDTO;

public interface DatiColturaService {
	
	List<ColturaDTO> findAllColturaSortById();
}
