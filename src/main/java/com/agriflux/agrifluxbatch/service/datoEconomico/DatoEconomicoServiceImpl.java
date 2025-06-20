package com.agriflux.agrifluxbatch.service.datoEconomico;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.agriflux.agrifluxbatch.repository.DatiEconomiciRepository;
import com.agriflux.agrifluxbatch.repository.projection.DatoEconomicoOrtaggioProjection;
import com.agriflux.agrifluxshared.service.datoEconomico.DatoEconomicoService;

@Service
public class DatoEconomicoServiceImpl implements DatoEconomicoService {
	
	private final DatiEconomiciRepository datiEconomiciRepository;
	
	public DatoEconomicoServiceImpl(DatiEconomiciRepository datiEconomiciRepository) {
		this.datiEconomiciRepository = datiEconomiciRepository;
	}
	
	@Override
	public Map<Long, List<Map<Integer, BigDecimal>>> findDatoEconomicoOrtaggio() {
		
		Map<Long, List<Map<Integer, BigDecimal>>> response = new HashMap<Long, List<Map<Integer, BigDecimal>>>();
		
		List<DatoEconomicoOrtaggioProjection> projectionList = datiEconomiciRepository.findDatoEconomicoOrtaggioProjection();
		
		List<Long> listaIdOrtaggio = projectionList.stream()
				.map(DatoEconomicoOrtaggioProjection::getIdOrtaggio).distinct().collect(Collectors.toList());
		
		List<String> listaAnnoRiferimento = projectionList.stream()
				.map(DatoEconomicoOrtaggioProjection::getAnnoRiferimento).distinct().collect(Collectors.toList());
		
		for (long idOrtaggio : listaIdOrtaggio) {
			Map<Integer, BigDecimal> prezzoMap = new HashMap<Integer, BigDecimal>();
			List<Map<Integer, BigDecimal>> prezzoAnnoList = new ArrayList<Map<Integer, BigDecimal>>();
			
			for (String annoRiferimento : listaAnnoRiferimento) {
				
				double prezzoMedioOrtaggio = projectionList.stream()
						.filter(d -> d.getIdOrtaggio() == idOrtaggio && d.getAnnoRiferimento().equals(annoRiferimento))
						.map(DatoEconomicoOrtaggioProjection::getPrezzoVenditaMedio).findFirst().get();
				
				prezzoMap.put(Integer.parseInt(annoRiferimento), new BigDecimal(prezzoMedioOrtaggio));
			}
			prezzoAnnoList.add(prezzoMap);
			response.put(idOrtaggio, prezzoAnnoList);
		}
		
		return response;
	}

}
