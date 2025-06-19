package com.agriflux.agrifluxbatch.job.stagione;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.agriflux.agrifluxbatch.model.stagione.DatiStagioneRecord;

public class DatiStagioneRowMapper implements RowMapper<DatiStagioneRecord>{

	@Override
	public DatiStagioneRecord mapRow(ResultSet rs, int rowNum) throws SQLException {
		return new DatiStagioneRecord(rs.getString("NOME"), rs.getString("MESE_GIORNO_INIZIO"),
				rs.getString("MESE_GIORNO_FINE"), rs.getString("RANGE_TEMPERATURA"), rs.getString("RANGE_UMIDITA"),
				rs.getString("RANGE_PRECIPITAZIONI"), rs.getString("RANGE_IRRAGGIAMENTO"),
				rs.getString("RANGE_OMBREGGIAMENTO"));
	}

}