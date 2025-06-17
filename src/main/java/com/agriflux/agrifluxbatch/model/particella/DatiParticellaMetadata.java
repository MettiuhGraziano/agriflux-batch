package com.agriflux.agrifluxbatch.model.particella;

import java.math.BigDecimal;

public record DatiParticellaMetadata(String comune, String foglio, String qualita, String annoInstallazione,
		BigDecimal costo, BigDecimal estensione, String pendenza, String esposizione) {

}