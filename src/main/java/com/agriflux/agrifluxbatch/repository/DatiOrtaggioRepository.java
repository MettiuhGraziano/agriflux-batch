package com.agriflux.agrifluxbatch.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import com.agriflux.agrifluxbatch.entity.Ortaggio;
import com.agriflux.agrifluxbatch.repository.projection.ortaggio.OrtaggioRangeStagioneProjection;

@Repository
public interface DatiOrtaggioRepository extends CrudRepository<Ortaggio, Long>, PagingAndSortingRepository<Ortaggio, Long> {
	
	@Query("SELECT o.idOrtaggio AS idOrtaggio, o.giorniSemina AS giorniSemina, "
			+ "o.giorniGerminazione AS giorniGerminazione, o.giorniTrapianto AS giorniTrapianto, "
			+ "o.giorniMaturazione AS giorniMaturazione, o.giorniRaccolta AS giorniRaccolta, "
			+ "o.pesoMedio AS pesoMedio, o.volumeMq AS volumeMq, r.meseSeminaMin AS meseSeminaMin, "
			+ "r.meseSeminaMax AS meseSeminaMax FROM Ortaggio o JOIN o.rangeStagioneSemina r")
	List<OrtaggioRangeStagioneProjection> findAllOrtaggioRangeStagioneProjection();
	
}