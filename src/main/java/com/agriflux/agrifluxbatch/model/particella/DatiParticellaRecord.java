package com.agriflux.agrifluxbatch.model.particella;

import java.math.BigDecimal;

public record DatiParticellaRecord(String comune, String foglio, String qualita, String annoInstallazione,
		BigDecimal costo, BigDecimal estensione, BigDecimal pendenza, String esposizione, long idLitologia) {

}
