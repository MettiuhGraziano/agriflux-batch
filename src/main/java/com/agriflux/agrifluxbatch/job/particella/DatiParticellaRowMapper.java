package com.agriflux.agrifluxbatch.job.particella;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.agriflux.agrifluxbatch.model.particella.DatiParticellaRecordReduce;

public class DatiParticellaRowMapper implements RowMapper<DatiParticellaRecordReduce> {

	private static final String ANNO_INSTALLAZIONE = "ANNO_INSTALLAZIONE";
	private static final String ID_PARTICELLA = "ID_PARTICELLA";

	@Override
	public DatiParticellaRecordReduce mapRow(ResultSet rs, int rowNum) throws SQLException {
		
		return new DatiParticellaRecordReduce(rs.getLong(ID_PARTICELLA), rs.getString(ANNO_INSTALLAZIONE));
	}

}
