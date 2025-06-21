package com.agriflux.agrifluxbatch.service.coltura;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.agriflux.agrifluxbatch.entity.Coltura;
import com.agriflux.agrifluxbatch.repository.DatiColturaRepository;
import com.agriflux.agrifluxbatch.repository.projection.coltura.ColturaFamigliaOrtaggioProjection;
import com.agriflux.agrifluxbatch.repository.projection.coltura.ColturaNomeOrtaggioProjection;
import com.agriflux.agrifluxbatch.repository.projection.coltura.ColturaProdottoPrezzoDataProjection;
import com.agriflux.agrifluxshared.dto.coltura.ColturaDTO;
import com.agriflux.agrifluxshared.dto.coltura.ColturaListPrezzoDataRaccoltoDTO;
import com.agriflux.agrifluxshared.service.coltura.DatiColturaService;

@Service
public class DatiColturaServiceImpl implements DatiColturaService {
	
	private final DatiColturaRepository datiColturaRepository;
	
	private final ModelMapper mapper;
	
	private static final String ID_COLTURA = "idColtura";
	
	DatiColturaServiceImpl(DatiColturaRepository datiColturaRepository, ModelMapper mapper) {
		this.datiColturaRepository = datiColturaRepository;
		this.mapper = mapper;
	}
	
	@Override
	public List<ColturaDTO> findAllColturaSortById() {
		List<ColturaDTO> response = new ArrayList<ColturaDTO>();

		Iterable<Coltura> colturaIterator = datiColturaRepository.findAll(Sort.by(Sort.Direction.ASC, ID_COLTURA));

		for (Coltura coltura : colturaIterator) {
			ColturaDTO colturaDTO = mapper.map(coltura, ColturaDTO.class);
			response.add(colturaDTO);
		}

		return response;
	}

	@Override
	public Map<String, Long> countOrtaggioColtura() {
		
		Map<String, Long> response = new HashMap<String, Long>();
		
		List<ColturaNomeOrtaggioProjection> projectionList = datiColturaRepository.findAllNomeOrtaggioProjection();
		
		for (ColturaNomeOrtaggioProjection projection : projectionList) {
			if (null != response.get(projection.getNomeOrtaggio())) {
				response.put(projection.getNomeOrtaggio(), response.get(projection.getNomeOrtaggio()) + 1L);
			} else {
				response.put(projection.getNomeOrtaggio(), 1L);
			}
		}
		
		return response;
	}
	
	@Override
	public Map<String, Long> countFamigliaOrtaggioColtura() {
		
		Map<String, Long> response = new HashMap<String, Long>();
		
		List<ColturaFamigliaOrtaggioProjection> projectionList = datiColturaRepository.findAllFamigliaOrtaggioProjection();
		
		for (ColturaFamigliaOrtaggioProjection projection : projectionList) {
			if (null != response.get(projection.getTipologia())) {
				response.put(projection.getTipologia(), response.get(projection.getTipologia()) + 1L);
			} else {
				response.put(projection.getTipologia(), 1L);
			}
		}
		
		return response;
	}

	@Override
	public Map<String, ColturaListPrezzoDataRaccoltoDTO> findPrezziAndDateColtura() {
		Map<String, ColturaListPrezzoDataRaccoltoDTO> colturaPrezzoDataMap = new HashMap<String, ColturaListPrezzoDataRaccoltoDTO>();

		List<ColturaProdottoPrezzoDataProjection> prezziAndDateColtura = datiColturaRepository
				.findPrezziAndDateColturaProjection();

		for (ColturaProdottoPrezzoDataProjection projection : prezziAndDateColtura) {

			if (!colturaPrezzoDataMap.isEmpty()
					&& null != colturaPrezzoDataMap.get(projection.getProdottoColtivato())) {

				colturaPrezzoDataMap.get(projection.getProdottoColtivato()).getPrezzoKgList()
						.add(projection.getPrezzoKg());
				colturaPrezzoDataMap.get(projection.getProdottoColtivato()).getDataRaccoltoList()
						.add(projection.getDataRaccolto());
			} else {

				ColturaListPrezzoDataRaccoltoDTO dto = new ColturaListPrezzoDataRaccoltoDTO(new ArrayList<BigDecimal>(),
						new ArrayList<LocalDate>());

				dto.getPrezzoKgList().add(projection.getPrezzoKg());
				dto.getDataRaccoltoList().add(projection.getDataRaccolto());

				colturaPrezzoDataMap.put(projection.getProdottoColtivato(), dto);
			}

		}

		return colturaPrezzoDataMap;
	}

}
