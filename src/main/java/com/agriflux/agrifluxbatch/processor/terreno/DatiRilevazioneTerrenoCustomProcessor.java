package com.agriflux.agrifluxbatch.processor.terreno;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.springframework.batch.core.annotation.BeforeProcess;
import org.springframework.batch.item.ItemProcessor;

import com.agriflux.agrifluxbatch.model.terreno.DatiRilevazioneTerrenoMetadata;
import com.agriflux.agrifluxbatch.model.terreno.DatiRilevazioneTerrenoRecord;
import com.agriflux.agrifluxbatch.processor.DatiProcessor;
import com.agriflux.agrifluxshared.dto.particella.DatiParticellaDTO;

public class DatiRilevazioneTerrenoCustomProcessor extends DatiProcessor implements ItemProcessor<DatiRilevazioneTerrenoMetadata, List<DatiRilevazioneTerrenoRecord>>{
	
	@BeforeProcess
	public void init() {
		if (cacheParticella.isEmpty()) {
			List<DatiParticellaDTO> particellaDtoList = particellaService.findAllParticellaIdAnno();
			
			if (null != particellaDtoList && !particellaDtoList.isEmpty()) {
				for (DatiParticellaDTO datiParticellaDTO : particellaDtoList) {
					cacheParticella.put(datiParticellaDTO.getIdParticella(), datiParticellaDTO);
				}
			}
		}
	}
	
	@Override
	public List<DatiRilevazioneTerrenoRecord> process(DatiRilevazioneTerrenoMetadata item) throws Exception {

		List<DatiRilevazioneTerrenoRecord> response = new ArrayList<DatiRilevazioneTerrenoRecord>();
		
		LocalDate dataOdierna = LocalDate.now();
		
		if (null != cacheParticella && !cacheParticella.isEmpty()) {
			for (Long idParticella : cacheParticella.keySet()) {
				
				int annoInstallazione = Integer.parseInt(cacheParticella.get(idParticella).getAnnoInstallazione());
				int annoAttuale = dataOdierna.getYear();
				
				while (annoInstallazione <= annoAttuale) {
					
					LocalDate dataRange1 = LocalDate.of(annoInstallazione, 1, 1);
					LocalDate dataRange2 = LocalDate.of(annoInstallazione, 3, 1);
					LocalDate dataRange3 = LocalDate.of(annoInstallazione, 6, 1);
					LocalDate dataRange4 = LocalDate.of(annoInstallazione, 9, 1);
					LocalDate dataRange5 = LocalDate.of(annoInstallazione, 12, 31);
					
					int meseAttuale = dataOdierna.getMonth().getValue();
					
					for (int x = 0; x < 4; x++) {
						if (x == 0 && (annoInstallazione != annoAttuale || meseAttuale >= 3)) {
							generaRilevazioneRecord(item, response, idParticella, dataRange1, dataRange2);
						} else if (x == 1 && (annoInstallazione != annoAttuale || meseAttuale >= 6)) {
							generaRilevazioneRecord(item, response, idParticella, dataRange2, dataRange3);
						} else if (x == 2 && (annoInstallazione != annoAttuale || meseAttuale >= 9)) {
							generaRilevazioneRecord(item, response, idParticella, dataRange3, dataRange4);
						} else if (x == 3 && (annoInstallazione != annoAttuale || meseAttuale >= 11)) {
							generaRilevazioneRecord(item, response, idParticella, dataRange4, dataRange5);
						}
					}
					
					annoInstallazione++;
				}
				
			}
		}
		
		return response;
	}

	private void generaRilevazioneRecord(DatiRilevazioneTerrenoMetadata item,
			List<DatiRilevazioneTerrenoRecord> response, Long idParticella, LocalDate dataRange1,
			LocalDate dataRange2) {
		
		BigDecimal phSuolo = generaRandomBigDecimalFromRange(item.phSuolo());
		BigDecimal umidita = generaRandomBigDecimalFromRange(item.umidita());
		BigDecimal capacitaAssorbente = generaRandomBigDecimalFromRange(item.capacitaAssorbente());
		BigDecimal porosita = generaRandomBigDecimalFromRange(item.porosita());
		BigDecimal temperatura = generaRandomBigDecimalFromRange(item.temperatura());

		LocalDate dataRilevazione = generaDataRandomFromRange(dataRange1, dataRange2);

		response.add(new DatiRilevazioneTerrenoRecord(phSuolo, umidita, capacitaAssorbente, porosita, temperatura,
				dataRilevazione, idParticella));
	}

}