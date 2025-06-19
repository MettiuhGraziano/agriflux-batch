package com.agriflux.agrifluxbatch.service.particella;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.agriflux.agrifluxbatch.repository.DatiParticellaRepository;
import com.agriflux.agrifluxbatch.repository.projection.ParticellaIdAnnoProjection;
import com.agriflux.agrifluxshared.dto.particella.DatiParticellaDTO;
import com.agriflux.agrifluxshared.service.particella.DatiParticellaService;

@Service
public class DatiParticellaServiceImpl implements DatiParticellaService {
	
	private final DatiParticellaRepository datiParticellaRepository;
	
	DatiParticellaServiceImpl(DatiParticellaRepository datiParticellaRepository) {
		this.datiParticellaRepository = datiParticellaRepository;
	}
	
	@Override
	public List<DatiParticellaDTO> findAllParticellaIdAnno() {
		
		List<DatiParticellaDTO> dtoList = new ArrayList<DatiParticellaDTO>();
		
		List<ParticellaIdAnnoProjection> projectionList = datiParticellaRepository.findAllParticellaIdAnnoProjection();
		
		for (ParticellaIdAnnoProjection projection : projectionList) {
			dtoList.add(new DatiParticellaDTO(projection.getIdParticella(), null, null, null,
					projection.getAnnoInstallazione(), null, null, null, null, 0));
		}
		
		return dtoList;
	}

}
