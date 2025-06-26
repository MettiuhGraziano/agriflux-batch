package com.agriflux.agrifluxbatch.service.ambiente;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.agriflux.agrifluxbatch.entity.Ambiente;
import com.agriflux.agrifluxbatch.repository.DatiAmbienteRepository;
import com.agriflux.agrifluxshared.dto.ambiente.AmbienteDTO;
import com.agriflux.agrifluxshared.dto.ambiente.VariazioneValoriParametriAmbienteDTO;
import com.agriflux.agrifluxshared.enumeratori.ParametriAmbientaliEnum;
import com.agriflux.agrifluxshared.service.ambiente.DatiAmbienteService;

@Service
public class DatiAmbienteServiceImpl implements DatiAmbienteService {

	private static final String DATA_RILEVAZIONE = "dataRilevazione";
	private static final String ID_AMBIENTE = "idAmbiente";
	
	private final DatiAmbienteRepository datiAmbienteRepository;
	
	private final ModelMapper mapper;
	
	DatiAmbienteServiceImpl(ModelMapper mapper, DatiAmbienteRepository datiAmbienteRepository) {
		this.datiAmbienteRepository = datiAmbienteRepository;
		this.mapper = mapper;
	}
	
	@Override
	public List<AmbienteDTO> findAllAmbienteSortById() {
		List<AmbienteDTO> ambienteList = new ArrayList<AmbienteDTO>();

		Iterable<Ambiente> ambienteIterator = datiAmbienteRepository
				.findAll(Sort.by(Sort.Direction.ASC, ID_AMBIENTE));

		for (Ambiente ambiente : ambienteIterator) {
			AmbienteDTO ambienteDTO = mapper.map(ambiente, AmbienteDTO.class);
			ambienteList.add(ambienteDTO);
		}

		return ambienteList;
	}

	@Override
	public List<String> getListaParametriAmbiente() {
		
		List<String> parametriList = new ArrayList<String>();
		
		for (ParametriAmbientaliEnum parametro : ParametriAmbientaliEnum.values()) {
			parametriList.add(parametro.name());
		}
		
		return parametriList;
	}

	@Override
	public Map<String, List<VariazioneValoriParametriAmbienteDTO>> getVariazioneValoriParametriAmbiente() {
		
		Map<String, List<VariazioneValoriParametriAmbienteDTO>> response = new HashMap<String, List<VariazioneValoriParametriAmbienteDTO>>();
		
		Map<String, BigDecimal> counterParametriInverno = new HashMap<String, BigDecimal>();
		Map<String, BigDecimal> counterParametriPrimavera = new HashMap<String, BigDecimal>();
		Map<String, BigDecimal> counterParametriEstate = new HashMap<String, BigDecimal>();
		Map<String, BigDecimal> counterParametriAutunno = new HashMap<String, BigDecimal>();
		List<String> listaParametriAmbiente = getListaParametriAmbiente();
		
		Iterable<Ambiente> ambienteIterator = datiAmbienteRepository
				.findAll(Sort.by(Sort.Direction.ASC, DATA_RILEVAZIONE));
		
		for (Ambiente ambiente : ambienteIterator) {
			inizializzaMappaResponse(response, listaParametriAmbiente, ambiente, counterParametriInverno,
					counterParametriPrimavera, counterParametriEstate, counterParametriAutunno);
		}
		
		return response;
	}

	private void inizializzaMappaResponse(Map<String, List<VariazioneValoriParametriAmbienteDTO>> response,
			List<String> listaParametriAmbiente, Ambiente ambiente, Map<String, BigDecimal> counterParametriInverno,
			Map<String, BigDecimal> counterParametriPrimavera, Map<String, BigDecimal> counterParametriEstate,
			Map<String, BigDecimal> counterParametriAutunno) {

		for (String parametro : listaParametriAmbiente) {
			List<VariazioneValoriParametriAmbienteDTO> dtoList = new ArrayList<VariazioneValoriParametriAmbienteDTO>();
			BigDecimal valore = null;
			BigDecimal valoreAnnoPrecedente = null;
			BigDecimal percentualeVariazione = new BigDecimal(0.0);

			if (parametro.equalsIgnoreCase("TEMPERATURA")) {
				valore = ambiente.getTemperaturaMedia();
			} else if (parametro.equalsIgnoreCase("UMIDITA")) {
				valore = ambiente.getUmiditaMedia();
			} else if (parametro.equalsIgnoreCase("PRECIPITAZIONI")) {
				valore = ambiente.getPrecipitazioni();
			} else if (parametro.equalsIgnoreCase("IRRAGGIAMENTO")) {
				valore = ambiente.getIrraggiamentoMedio();
			} else if (parametro.equalsIgnoreCase("OMBREGGIAMENTO")) {
				valore = ambiente.getOmbreggiamentoMedio();
			}
			
			if (ambiente.getDataRilevazione().isBefore(LocalDate.of(ambiente.getDataRilevazione().getYear(), 6, 20)) && 
					ambiente.getDataRilevazione().isAfter(LocalDate.of(ambiente.getDataRilevazione().getYear(), 3, 20))) {
				if (!counterParametriPrimavera.isEmpty() && null != counterParametriPrimavera.get(parametro)) {
					valoreAnnoPrecedente = counterParametriPrimavera.get(parametro);
				}
				counterParametriPrimavera.put(parametro, valore);
			} else if (ambiente.getDataRilevazione().isBefore(LocalDate.of(ambiente.getDataRilevazione().getYear(), 9, 22)) && 
					ambiente.getDataRilevazione().isAfter(LocalDate.of(ambiente.getDataRilevazione().getYear(), 6, 21))) {
				if (!counterParametriEstate.isEmpty() && null != counterParametriEstate.get(parametro)) {
					valoreAnnoPrecedente = counterParametriEstate.get(parametro);
				}
				counterParametriEstate.put(parametro, valore);
			} else if (ambiente.getDataRilevazione().isBefore(LocalDate.of(ambiente.getDataRilevazione().getYear(), 12, 20)) && 
					ambiente.getDataRilevazione().isAfter(LocalDate.of(ambiente.getDataRilevazione().getYear(), 9, 23))) {
				if (!counterParametriAutunno.isEmpty() && null != counterParametriAutunno.get(parametro)) {
					valoreAnnoPrecedente = counterParametriAutunno.get(parametro);
				}
				counterParametriAutunno.put(parametro, valore);
			} else {
				if (!counterParametriInverno.isEmpty() && null != counterParametriInverno.get(parametro)) {
					valoreAnnoPrecedente = counterParametriInverno.get(parametro);
				}
				counterParametriInverno.put(parametro, valore);
			}
			
			if (null != valoreAnnoPrecedente) {
				double proporzione = (valore.doubleValue()*100.0)/valoreAnnoPrecedente.doubleValue();
				percentualeVariazione = new BigDecimal(proporzione).subtract(new BigDecimal(100.0));
				response.get(parametro).add(new VariazioneValoriParametriAmbienteDTO(valore, ambiente.getDataRilevazione(), percentualeVariazione));
			} else {
				if (null == response.get(parametro)) {
					dtoList.add(new VariazioneValoriParametriAmbienteDTO(valore, ambiente.getDataRilevazione(), percentualeVariazione));
					response.put(parametro, dtoList);
				} else {
					response.get(parametro).add(new VariazioneValoriParametriAmbienteDTO(valore, ambiente.getDataRilevazione(), percentualeVariazione));
				}
			}
			
		}
	}
	
}
