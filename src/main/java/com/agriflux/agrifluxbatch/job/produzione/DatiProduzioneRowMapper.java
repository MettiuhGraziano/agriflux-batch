package com.agriflux.agrifluxbatch.job.produzione;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.agriflux.agrifluxbatch.model.produzione.DatiColturaJoinParticellaRecord;

public class DatiProduzioneRowMapper implements RowMapper<DatiColturaJoinParticellaRecord>{

	@Override
	public DatiColturaJoinParticellaRecord mapRow(ResultSet rs, int rowNum) throws SQLException {
		return new DatiColturaJoinParticellaRecord(rs.getLong("ID_COLTURA"), rs.getBigDecimal("PREZZO_KG"),
				rs.getBigDecimal("ESTENSIONE"), rs.getBigDecimal("PESO_MEDIO"), rs.getBigDecimal("VOLUME_MQ"));
	}

}
