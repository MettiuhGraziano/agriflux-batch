package com.agriflux.agrifluxbatch.service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.agriflux.agrifluxbatch.entity.Ambiente;
import com.agriflux.agrifluxbatch.entity.Coltura;
import com.agriflux.agrifluxbatch.entity.Morfologia;
import com.agriflux.agrifluxbatch.entity.Produzione;
import com.agriflux.agrifluxbatch.entity.Terreno;
import com.agriflux.agrifluxbatch.repository.DatiAmbientaliRepository;
import com.agriflux.agrifluxbatch.repository.DatiColturaRepository;
import com.agriflux.agrifluxbatch.repository.DatiMorfologiciRepository;
import com.agriflux.agrifluxbatch.repository.DatiProduzioneRepository;
import com.agriflux.agrifluxbatch.repository.DatiTerrenoRepository;
import com.agriflux.agrifluxbatch.repository.projection.ColturaProdottoPrezzoDataProjection;
import com.agriflux.agrifluxbatch.repository.projection.ProduzioneJoinColturaProjection;
import com.agriflux.agrifluxshared.dto.AmbienteDTO;
import com.agriflux.agrifluxshared.dto.ColturaDTO;
import com.agriflux.agrifluxshared.dto.ColturaGroupByProdottoDTO;
import com.agriflux.agrifluxshared.dto.ColturaListPrezzoDataRaccoltoDTO;
import com.agriflux.agrifluxshared.dto.MorfologiaDTO;
import com.agriflux.agrifluxshared.dto.ProduzioneColturaDTO;
import com.agriflux.agrifluxshared.dto.ProduzioneDTO;
import com.agriflux.agrifluxshared.dto.TerrenoDTO;
import com.agriflux.agrifluxshared.service.AgrifluxDataService;

@Service
public class AgrifluxDataServiceImpl implements AgrifluxDataService{
	
	private static final String ID_COLTURA = "idColtura";
	private static final String ID_AMBIENTE = "idAmbiente";
	private static final String ID_MORFOLOGIA = "idMorfologia";
	private static final String ID_RILEVAZIONE_TERRENO = "idRilevazioneTerreno";
	private static final String ID_PRODUZIONE = "idProduzione";
	
	private final DatiColturaRepository datiColturaRepository;
	private final DatiAmbientaliRepository datiAmbientaliRepository;
	private final DatiMorfologiciRepository datiMorfologiciRepository;
	private final DatiTerrenoRepository datiTerrenoRepository;
	private final DatiProduzioneRepository datiProduzioneRepository;
	
	private final ModelMapper mapper;
	
	AgrifluxDataServiceImpl(DatiColturaRepository colturaRepository, DatiAmbientaliRepository datiAmbientaliRepository,
			DatiMorfologiciRepository datiMorfologiciRepository, DatiTerrenoRepository datiTerrenoRepository,
			DatiProduzioneRepository datiProduzioneRepository, ModelMapper mapper) {
		this.datiColturaRepository = colturaRepository;
		this.datiAmbientaliRepository = datiAmbientaliRepository;
		this.datiMorfologiciRepository = datiMorfologiciRepository;
		this.datiTerrenoRepository = datiTerrenoRepository;
		this.datiProduzioneRepository = datiProduzioneRepository;
		
		this.mapper = mapper;
	}

	@Override
	public List<ColturaDTO> findAllColturaSortById() {
		
		List<ColturaDTO> colturaList = new ArrayList<ColturaDTO>();
		
		Iterable<Coltura> colturaIterator = datiColturaRepository.findAll(Sort.by(Sort.Direction.ASC, ID_COLTURA));
		
		for (Coltura coltura : colturaIterator) {
			ColturaDTO colturaDTO = mapper.map(coltura, ColturaDTO.class);
			colturaList.add(colturaDTO);
		}
		
		return colturaList;
	}

	@Override
	public List<AmbienteDTO> findAllAmbienteSortById() {
		
		List<AmbienteDTO> ambienteList = new ArrayList<AmbienteDTO>();
		
		Iterable<Ambiente> ambienteIterator = datiAmbientaliRepository.findAll(Sort.by(Sort.Direction.ASC, ID_AMBIENTE));
		
		for (Ambiente ambiente : ambienteIterator) {
			AmbienteDTO ambienteDTO = mapper.map(ambiente, AmbienteDTO.class);
			ambienteList.add(ambienteDTO);
		}
		
		return ambienteList;
	}

	@Override
	public List<MorfologiaDTO> findAllMorfologiaSortById() {
		
		List<MorfologiaDTO> morfologiaList = new ArrayList<MorfologiaDTO>();
		
		Iterable<Morfologia> morfologiaIterator = datiMorfologiciRepository.findAll(Sort.by(Sort.Direction.ASC, ID_MORFOLOGIA));
		
		for (Morfologia morfologia : morfologiaIterator) {
			MorfologiaDTO morfologiaDTO = mapper.map(morfologia, MorfologiaDTO.class);
			morfologiaList.add(morfologiaDTO);
		}
		
		return morfologiaList;
	}

	@Override
	public List<TerrenoDTO> findAllTerrenoSortById() {
		
		List<TerrenoDTO> terrenoList = new ArrayList<TerrenoDTO>();
		
		Iterable<Terreno> terrenoIterator = datiTerrenoRepository.findAll(Sort.by(Sort.Direction.ASC, ID_RILEVAZIONE_TERRENO));
		
		for (Terreno terreno : terrenoIterator) {
			TerrenoDTO terrenoDTO = mapper.map(terreno, TerrenoDTO.class);
			terrenoList.add(terrenoDTO);
		}
		
		return terrenoList;
	}

	@Override
	public List<ProduzioneDTO> findAllProduzioneSortById() {
		
		List<ProduzioneDTO> produzioneList = new ArrayList<ProduzioneDTO>();
		
		Iterable<Produzione> produzioneIterator = datiProduzioneRepository.findAll(Sort.by(Sort.Direction.ASC, ID_PRODUZIONE));
		
		for (Produzione produzione : produzioneIterator) {
			ProduzioneDTO produzioneDTO = mapper.map(produzione, ProduzioneDTO.class);
			produzioneList.add(produzioneDTO);
		}
		
		return produzioneList;
	}

	@Override
	public List<ColturaGroupByProdottoDTO> countColtureGroupByProdotto() {
		return datiColturaRepository.countColtureGroupByProdotto();
	}

	@Override
	public Map<String, ColturaListPrezzoDataRaccoltoDTO> findPrezziAndDateColtura() {
		
		Map<String, ColturaListPrezzoDataRaccoltoDTO> colturaPrezzoDataMap = new HashMap<String, ColturaListPrezzoDataRaccoltoDTO>();
		
		List<ColturaProdottoPrezzoDataProjection> prezziAndDateColtura = datiColturaRepository.findPrezziAndDateColtura();
		
		for (ColturaProdottoPrezzoDataProjection projection : prezziAndDateColtura) {
			
			if (!colturaPrezzoDataMap.isEmpty()
					&& null != colturaPrezzoDataMap.get(projection.getProdottoColtivato())) {
				
				colturaPrezzoDataMap.get(projection.getProdottoColtivato()).getPrezzoKgList()
						.add(projection.getPrezzoKg());
				colturaPrezzoDataMap.get(projection.getProdottoColtivato()).getDataRaccoltoList()
						.add(projection.getDataRaccolto());
			} else {
				
				ColturaListPrezzoDataRaccoltoDTO dto = new ColturaListPrezzoDataRaccoltoDTO(new ArrayList<BigDecimal>(), new ArrayList<Date>());
				
				dto.getPrezzoKgList().add(projection.getPrezzoKg());
				dto.getDataRaccoltoList().add(projection.getDataRaccolto());
				
				colturaPrezzoDataMap.put(projection.getProdottoColtivato(), dto);
			}
			
		}
		
		return colturaPrezzoDataMap;
	}

	@Override
	public Map<String, Map<String, ProduzioneColturaDTO>> findColtureJoinProduzione() {
		
		Map<String, Map<String, ProduzioneColturaDTO>> response = new HashMap<String, Map<String, ProduzioneColturaDTO>>();
		
		List<ProduzioneJoinColturaProjection> produzioneWithColturaProjection = datiProduzioneRepository.findProduzioneWithColturaProjection();
		
		Calendar calendar = Calendar.getInstance();
		ProduzioneColturaDTO dto = null;
		
		for (ProduzioneJoinColturaProjection projection : produzioneWithColturaProjection) {
			
			calendar.setTime(projection.getDataRaccolto());
			String annoRiferimento = String.valueOf(calendar.get(Calendar.YEAR));
			
			if (!response.isEmpty() && null != response.get(projection.getProdottoColtivato())) {
				
				if (null != response.get(projection.getProdottoColtivato()).get(annoRiferimento)) {
					
					BigDecimal quantitaRaccoltoTotale = response.get(projection.getProdottoColtivato())
							.get(annoRiferimento).getQuantitaRaccolto().add(projection.getQuantitaRaccolto());
					
					BigDecimal quantitaRaccoltoTotaleVenduto = response.get(projection.getProdottoColtivato())
							.get(annoRiferimento).getQuantitaRaccoltoVenduto().add(projection.getQuantitaRaccoltoVenduto());
					
					BigDecimal fatturatoTotale = response.get(projection.getProdottoColtivato()).get(annoRiferimento)
							.getFatturatoColtura().add(projection.getFatturatoColtura());

					response.get(projection.getProdottoColtivato()).get(annoRiferimento).setQuantitaRaccolto(quantitaRaccoltoTotale);
					response.get(projection.getProdottoColtivato()).get(annoRiferimento).setQuantitaRaccoltoVenduto(quantitaRaccoltoTotaleVenduto);
					response.get(projection.getProdottoColtivato()).get(annoRiferimento).setFatturatoColtura(fatturatoTotale);
					
				} else {
					dto = new ProduzioneColturaDTO(projection.getQuantitaRaccolto(), projection.getQuantitaRaccoltoVenduto(), projection.getFatturatoColtura());
					response.get(projection.getProdottoColtivato()).put(annoRiferimento, dto);
				}
			} else {
				dto = new ProduzioneColturaDTO(projection.getQuantitaRaccolto(), projection.getQuantitaRaccoltoVenduto(), projection.getFatturatoColtura());
				Map<String, ProduzioneColturaDTO> mappaAnno = new HashMap<String, ProduzioneColturaDTO>();
				mappaAnno.put(annoRiferimento, dto);
				response.put(projection.getProdottoColtivato(), mappaAnno);
			}
			
		}
		
		return response;
	}

}
