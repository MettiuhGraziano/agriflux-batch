package com.agriflux.agrifluxbatch.model;

public record DatiAmbientaliMetadata(String temperaturaMedia, String umiditaMedia, String precipitazioni,
		String irraggiamentoMedio, String ombreggiamentoMedio, String dataRilevazione, String fkIdColtura) {

}
