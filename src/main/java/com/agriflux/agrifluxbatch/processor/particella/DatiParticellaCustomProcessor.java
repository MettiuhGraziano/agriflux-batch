package com.agriflux.agrifluxbatch.processor.particella;

import java.math.BigDecimal;

import org.springframework.batch.core.annotation.BeforeProcess;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.beans.factory.annotation.Autowired;

import com.agriflux.agrifluxbatch.model.particella.DatiParticellaMetadata;
import com.agriflux.agrifluxbatch.model.particella.DatiParticellaRecord;
import com.agriflux.agrifluxbatch.processor.DatiProcessor;
import com.agriflux.agrifluxbatch.repository.DatiLitologiaRepository;
import com.agriflux.agrifluxshared.enumeratori.EsposizioneEnum;

public class DatiParticellaCustomProcessor extends DatiProcessor implements ItemProcessor<DatiParticellaMetadata, DatiParticellaRecord>{
	
	private int numeroLitologie = 0;
	
	@Autowired
	private DatiLitologiaRepository litologiaRepository;
	
	@BeforeProcess
	public void beforeProcess() {
		
		if (numeroLitologie == 0) {
			numeroLitologie = litologiaRepository.countAllLitologie();
		}
	}
	
	@Override
	public DatiParticellaRecord process(DatiParticellaMetadata item) throws Exception {
		
		BigDecimal pendenza = generaRandomBigDecimalFromRange(item.pendenza());
		
		String esposizione = item.esposizione();
		for (EsposizioneEnum esp : EsposizioneEnum.values()) {
			if (esp.name().equalsIgnoreCase(esposizione)) {
				esposizione = esp.getEsposizione();
			}
		}
		
		long idLitologia = generaRandomIntFromRange(1, numeroLitologie + 1);
		
		return new DatiParticellaRecord(item.comune(), item.foglio(), item.qualita(),
				item.annoInstallazione(), item.costo(), item.estensione(), pendenza, esposizione, idLitologia);
	}

}
