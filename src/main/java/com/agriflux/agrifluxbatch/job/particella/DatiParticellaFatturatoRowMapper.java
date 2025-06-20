package com.agriflux.agrifluxbatch.job.particella;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.agriflux.agrifluxbatch.model.particella.DatiParticellaFatturatoRecord;

public class DatiParticellaFatturatoRowMapper implements RowMapper<DatiParticellaFatturatoRecord>{
	
	private static final String ANNO_INSTALLAZIONE = "ANNO_INSTALLAZIONE";
	private static final String ID_PARTICELLA = "ID_PARTICELLA";
	private static final String COSTO = "COSTO";
	
	@Override
	public DatiParticellaFatturatoRecord mapRow(ResultSet rs, int rowNum) throws SQLException {
		return new DatiParticellaFatturatoRecord(rs.getLong(ID_PARTICELLA), rs.getInt(ANNO_INSTALLAZIONE),
				rs.getBigDecimal(COSTO));
	}

}
