package com.agriflux.agrifluxbatch.service.produzione;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.agriflux.agrifluxbatch.entity.Produzione;
import com.agriflux.agrifluxbatch.repository.DatiProduzioneRepository;
import com.agriflux.agrifluxbatch.repository.projection.ProduzioneJoinColturaFatturatoProjection;
import com.agriflux.agrifluxbatch.repository.projection.ProduzioneJoinColturaTempiProjection;
import com.agriflux.agrifluxbatch.repository.projection.produzione.ProduzioneQuantitaJoinColturaProjection;
import com.agriflux.agrifluxshared.dto.produzione.ProduzioneColturaDTO;
import com.agriflux.agrifluxshared.dto.produzione.ProduzioneColturaTempiDTO;
import com.agriflux.agrifluxshared.dto.produzione.ProduzioneDTO;
import com.agriflux.agrifluxshared.dto.produzione.ProduzioneJoinColturaFatturatoDTO;
import com.agriflux.agrifluxshared.service.produzione.DatiProduzioneService;

@Service
public class DatiProduzioneServiceImpl implements DatiProduzioneService {
	
	private final DatiProduzioneRepository datiProduzioneRepository;
	
	private final ModelMapper mapper;
	
	private static final String ID_PRODUZIONE = "idProduzione";
	
	DatiProduzioneServiceImpl(DatiProduzioneRepository datiProduzioneRepository, ModelMapper mapper) {
		this.datiProduzioneRepository = datiProduzioneRepository;
		this.mapper = mapper;
	}
	
	@Override
	public List<ProduzioneDTO> findAllProduzioneSortById() {
		List<ProduzioneDTO> produzioneList = new ArrayList<ProduzioneDTO>();

		Iterable<Produzione> produzioneIterator = datiProduzioneRepository
				.findAll(Sort.by(Sort.Direction.ASC, ID_PRODUZIONE));

		for (Produzione produzione : produzioneIterator) {
			ProduzioneDTO produzioneDTO = mapper.map(produzione, ProduzioneDTO.class);
			produzioneList.add(produzioneDTO);
		}

		return produzioneList;
	}

	public Map<Long, List<Map<Integer, ProduzioneJoinColturaFatturatoDTO>>> findProduzioneJoinColturaFatturato() {
		
		Map<Long, List<Map<Integer, ProduzioneJoinColturaFatturatoDTO>>> response = new HashMap<Long, List<Map<Integer, ProduzioneJoinColturaFatturatoDTO>>>();
		
		List<ProduzioneJoinColturaFatturatoProjection> projectionList = datiProduzioneRepository.findProduzioneJoinColturaFatturatoProjection();
		
		//Lista di idParticella distinte
		List<Long> listaIdParticella = projectionList.stream()
				.map(ProduzioneJoinColturaFatturatoProjection::getIdParticella).distinct().collect(Collectors.toList());

		//Lista di anni distinti
		List<Integer> listaAnnoRaccolto = projectionList.stream()
				.map(ProduzioneJoinColturaFatturatoProjection::getAnnoRaccolto).distinct().collect(Collectors.toList());
		
		for (Long idParticella : listaIdParticella) {
			List<Map<Integer, ProduzioneJoinColturaFatturatoDTO>> mapList = new ArrayList<Map<Integer, ProduzioneJoinColturaFatturatoDTO>>();
				for (Integer annoRaccolto : listaAnnoRaccolto) {
					Map<Integer, ProduzioneJoinColturaFatturatoDTO> map = new HashMap<Integer, ProduzioneJoinColturaFatturatoDTO>();
					double fatturatoRaccolto = projectionList.stream()
							.filter(p -> p.getIdParticella() == idParticella && p.getAnnoRaccolto() == annoRaccolto)
							.mapToDouble(ProduzioneJoinColturaFatturatoProjection::getFatturatoRaccolto)
							.sum();
							
					double speseProduzione = projectionList.stream()
							.filter(p -> p.getIdParticella() == idParticella && p.getAnnoRaccolto() == annoRaccolto)
							.mapToDouble(ProduzioneJoinColturaFatturatoProjection::getSpeseProduzione)
							.sum();
					
					if (fatturatoRaccolto == 0 && speseProduzione == 0) {
						continue;
					}
					
					map.put(annoRaccolto, new ProduzioneJoinColturaFatturatoDTO(new BigDecimal(fatturatoRaccolto), new BigDecimal(speseProduzione)));					
					mapList.add(map);
				}
				
				response.put(idParticella, mapList);
			}
			
		return response;
	}

	@Override
	public Map<String, Map<String, ProduzioneColturaDTO>> findProduzioneQuantitaJoinColtura() {
		
		Map<String, Map<String, ProduzioneColturaDTO>> response = new HashMap<String, Map<String, ProduzioneColturaDTO>>();

		List<ProduzioneQuantitaJoinColturaProjection> produzioneWithColturaProjection = datiProduzioneRepository
				.findProduzioneQuantitaJoinColturaProjection();

		ProduzioneColturaDTO dto = null;

		for (ProduzioneQuantitaJoinColturaProjection projection : produzioneWithColturaProjection) {

			String annoRiferimento = projection.getAnnoRaccolto();

			if (!response.isEmpty() && null != response.get(projection.getNomeOrtaggio())) {

				if (null != response.get(projection.getNomeOrtaggio()).get(annoRiferimento)) {

					BigDecimal quantitaRaccoltoTotale = response.get(projection.getNomeOrtaggio()).get(annoRiferimento)
							.getQuantitaRaccolto().add(projection.getQuantitaRaccolto());

					BigDecimal quantitaRaccoltoTotaleVenduto = response.get(projection.getNomeOrtaggio())
							.get(annoRiferimento).getQuantitaRaccoltoVenduto()
							.add(projection.getQuantitaRaccoltoVenduto());

					BigDecimal quantitaRaccoltoTotaleMarcio = response.get(projection.getNomeOrtaggio())
							.get(annoRiferimento).getQuantitaRaccoltoMarcio()
							.add(projection.getQuantitaRaccoltoMarcio());

					BigDecimal quantitaRaccoltoTotaleTerzi = response.get(projection.getNomeOrtaggio())
							.get(annoRiferimento).getQuantitaRaccoltoTerzi()
							.add(projection.getQuantitaRaccoltoTerzi());

					BigDecimal fatturatoTotale = response.get(projection.getNomeOrtaggio()).get(annoRiferimento)
							.getFatturatoRaccolto().add(projection.getFatturatoRaccolto());

					response.get(projection.getNomeOrtaggio()).get(annoRiferimento)
							.setQuantitaRaccolto(quantitaRaccoltoTotale);
					response.get(projection.getNomeOrtaggio()).get(annoRiferimento)
							.setQuantitaRaccoltoVenduto(quantitaRaccoltoTotaleVenduto);
					response.get(projection.getNomeOrtaggio()).get(annoRiferimento)
							.setQuantitaRaccoltoMarcio(quantitaRaccoltoTotaleMarcio);
					response.get(projection.getNomeOrtaggio()).get(annoRiferimento)
							.setQuantitaRaccoltoTerzi(quantitaRaccoltoTotaleTerzi);
					response.get(projection.getNomeOrtaggio()).get(annoRiferimento)
							.setFatturatoRaccolto(fatturatoTotale);

				} else {
					dto = new ProduzioneColturaDTO(projection.getQuantitaRaccolto(),
							projection.getQuantitaRaccoltoVenduto(), projection.getQuantitaRaccoltoMarcio(),
							projection.getQuantitaRaccoltoTerzi(), projection.getFatturatoRaccolto());
					response.get(projection.getNomeOrtaggio()).put(annoRiferimento, dto);
				}
			} else {
				dto = new ProduzioneColturaDTO(projection.getQuantitaRaccolto(),
						projection.getQuantitaRaccoltoVenduto(), projection.getQuantitaRaccoltoMarcio(),
						projection.getQuantitaRaccoltoTerzi(), projection.getFatturatoRaccolto());
				Map<String, ProduzioneColturaDTO> mappaAnno = new HashMap<String, ProduzioneColturaDTO>();
				mappaAnno.put(annoRiferimento, dto);
				response.put(projection.getNomeOrtaggio(), mappaAnno);
			}

		}

		return response;
	}

	@Override
	public Map<String, List<ProduzioneColturaTempiDTO>> findProduzioneJoinColturaTempi() {

		Map<String, List<ProduzioneColturaTempiDTO>> response = new HashMap<String, List<ProduzioneColturaTempiDTO>>();

		List<String> counterAnniRiferimento = new ArrayList<String>();
		List<String> counterProdotti = new ArrayList<String>();

		List<ProduzioneJoinColturaTempiProjection> projectionList = datiProduzioneRepository
				.findProduzioneJoinColturaTempiProjection();

		for (ProduzioneJoinColturaTempiProjection projection : projectionList) {

			String annoRiferimento = projection.getAnnoSemina();

			if (!counterAnniRiferimento.contains(annoRiferimento)) {
				counterAnniRiferimento.add(annoRiferimento);
			}

			String prodotto = projection.getNomeOrtaggio();

			if (!counterProdotti.contains(prodotto)) {
				counterProdotti.add(prodotto);
			}
		}

		long counterNumeroColtureAnno = 0;

		for (String annoRiferimento : counterAnniRiferimento) {

			List<ProduzioneColturaTempiDTO> counterList = new ArrayList<ProduzioneColturaTempiDTO>();

			for (String prodotto : counterProdotti) {

				double sommaTempoSemina = projectionList.stream().filter(
						p -> p.getNomeOrtaggio().equals(prodotto) && p.getAnnoSemina().equals(annoRiferimento))
						.mapToDouble(ProduzioneJoinColturaTempiProjection::getTempoSemina).sum();

				counterNumeroColtureAnno = projectionList.stream().filter(
						p -> p.getNomeOrtaggio().equals(prodotto) && p.getAnnoSemina().equals(annoRiferimento))
						.mapToDouble(ProduzioneJoinColturaTempiProjection::getTempoSemina).count();

				if (counterNumeroColtureAnno == 0L) {
					continue;
				}

				double sommaTempoGerminazione = projectionList.stream().filter(
						p -> p.getNomeOrtaggio().equals(prodotto) && p.getAnnoSemina().equals(annoRiferimento))
						.mapToDouble(ProduzioneJoinColturaTempiProjection::getTempoGerminazione).sum();

				double sommaTempoTrapianto = projectionList.stream().filter(
						p -> p.getNomeOrtaggio().equals(prodotto) && p.getAnnoSemina().equals(annoRiferimento))
						.mapToDouble(ProduzioneJoinColturaTempiProjection::getTempoTrapianto).sum();

				double sommaTempoMaturazione = projectionList.stream().filter(
						p -> p.getNomeOrtaggio().equals(prodotto) && p.getAnnoSemina().equals(annoRiferimento))
						.mapToDouble(ProduzioneJoinColturaTempiProjection::getTempoMaturazione).sum();

				double sommaTempoRaccolta = projectionList.stream().filter(
						p -> p.getNomeOrtaggio().equals(prodotto) && p.getAnnoSemina().equals(annoRiferimento))
						.mapToDouble(ProduzioneJoinColturaTempiProjection::getTempoRaccolta).sum();

				List<Double> listaMedieTempi = Arrays.asList(sommaTempoSemina / counterNumeroColtureAnno,
						sommaTempoGerminazione / counterNumeroColtureAnno,
						sommaTempoTrapianto / counterNumeroColtureAnno,
						sommaTempoMaturazione / counterNumeroColtureAnno,
						sommaTempoRaccolta / counterNumeroColtureAnno);

				ProduzioneColturaTempiDTO dto = new ProduzioneColturaTempiDTO(prodotto, listaMedieTempi);

				counterList.add(dto);
			}

			response.put(annoRiferimento, counterList);
		}

		return response;
	}

}
