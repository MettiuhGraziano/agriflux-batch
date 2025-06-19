package com.agriflux.agrifluxbatch.service.ortaggio;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.agriflux.agrifluxbatch.entity.Ortaggio;
import com.agriflux.agrifluxbatch.repository.DatiOrtaggioRepository;
import com.agriflux.agrifluxbatch.repository.projection.OrtaggioRangeStagioneProjection;
import com.agriflux.agrifluxshared.dto.ortaggio.OrtaggioDTO;
import com.agriflux.agrifluxshared.dto.ortaggio.OrtaggioRangeStagioneSumDTO;
import com.agriflux.agrifluxshared.service.ortaggio.DatiOrtaggioService;

@Service
public class DatiOrtaggioServiceImpl implements DatiOrtaggioService {
	
	private static final String ID_ORTAGGIO = "idOrtaggio";
	
	private final ModelMapper mapper;
	
	private DatiOrtaggioRepository datiOrtaggiRepository;
	
	public DatiOrtaggioServiceImpl(ModelMapper mapper, DatiOrtaggioRepository datiOrtaggiRepository) {
		this.mapper = mapper;
		this.datiOrtaggiRepository = datiOrtaggiRepository;
	}

	@Override
	public List<OrtaggioDTO> findAllOrtaggioSortById() {
		
		List<OrtaggioDTO> dtoList = new ArrayList<OrtaggioDTO>();
		
		Iterator<Ortaggio> findAll = datiOrtaggiRepository.findAll(Sort.by(Sort.Direction.ASC, ID_ORTAGGIO)).iterator();
		
		while (null != findAll && findAll.hasNext()) {
			OrtaggioDTO dto = mapper.map(findAll.next(), OrtaggioDTO.class);
			dtoList.add(dto);
		}
		
		return dtoList;
	}

	@Override
	public List<OrtaggioRangeStagioneSumDTO> findAllOrtaggioRangeStagione() {
		
		List<OrtaggioRangeStagioneSumDTO> dtoList = new ArrayList<OrtaggioRangeStagioneSumDTO>();
		
		List<OrtaggioRangeStagioneProjection> result = datiOrtaggiRepository.findAllOrtaggioRangeStagioneProjection();
		
		if (null != result && !result.isEmpty()) {
			for (OrtaggioRangeStagioneProjection dto : result) {
				int seminaRaccolto = dto.getGiorniSemina() + dto.getGiorniGerminazione() + dto.getGiorniMaturazione()
						+ dto.getGiorniTrapianto() + dto.getGiorniRaccolta();
				
				dtoList.add(new OrtaggioRangeStagioneSumDTO(dto.getIdOrtaggio(), seminaRaccolto, dto.getPesoMedio(),
						dto.getVolumeMq(), dto.getMeseSeminaMin(), dto.getMeseSeminaMax()));
			}
		}
		
		return dtoList;
	}
}
