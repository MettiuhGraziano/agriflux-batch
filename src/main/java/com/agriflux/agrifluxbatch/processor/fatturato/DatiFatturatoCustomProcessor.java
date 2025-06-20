package com.agriflux.agrifluxbatch.processor.fatturato;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.batch.core.annotation.BeforeProcess;
import org.springframework.batch.item.ItemProcessor;

import com.agriflux.agrifluxbatch.model.fatturato.DatiFatturatoRecord;
import com.agriflux.agrifluxbatch.model.particella.DatiParticellaFatturatoRecord;
import com.agriflux.agrifluxbatch.processor.DatiProcessor;
import com.agriflux.agrifluxshared.dto.produzione.ProduzioneJoinColturaFatturatoDTO;

public class DatiFatturatoCustomProcessor extends DatiProcessor implements ItemProcessor<DatiParticellaFatturatoRecord, List<DatiFatturatoRecord>> {
	
	@BeforeProcess
	public void init() {
		if (cacheParticellaProduzione.isEmpty()) {
			Map<Long, List<Map<Integer, ProduzioneJoinColturaFatturatoDTO>>> particellaMap = produzioneService
					.findProduzioneJoinColturaFatturato();

			if (null != particellaMap && !particellaMap.isEmpty()) {
				cacheParticellaProduzione = particellaMap;
			}
		}
	}
	
	@Override
	public List<DatiFatturatoRecord> process(DatiParticellaFatturatoRecord item) throws Exception {
		
		List<DatiFatturatoRecord> response = new ArrayList<DatiFatturatoRecord>();
		
		if (!cacheParticellaProduzione.isEmpty()) {
			List<Map<Integer, ProduzioneJoinColturaFatturatoDTO>> listAnnoDtoMap = cacheParticellaProduzione
					.get(item.idParticella());

			for (Map<Integer, ProduzioneJoinColturaFatturatoDTO> annoDtoMap : listAnnoDtoMap) {

				for (Integer annoRiferimento : annoDtoMap.keySet()) {

					BigDecimal ricaviVendita = annoDtoMap.get(annoRiferimento).getFatturatoRaccolto();
					BigDecimal speseGenerali = annoDtoMap.get(annoRiferimento).getSpeseProduzione();

					if (item.annoInstallazione() == annoRiferimento) {
						speseGenerali = speseGenerali.add(item.costo());
					}

					response.add(new DatiFatturatoRecord(String.valueOf(annoRiferimento), ricaviVendita, speseGenerali,
							new BigDecimal(0), new BigDecimal(0), item.idParticella()));
				}
			}

		}
		
		return response;
	}

}