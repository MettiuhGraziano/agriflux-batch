package com.agriflux.agrifluxbatch.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import com.agriflux.agrifluxbatch.entity.DatoEconomico;
import com.agriflux.agrifluxbatch.repository.projection.DatoEconomicoOrtaggioProjection;

@Repository
public interface DatiEconomiciRepository extends CrudRepository<DatoEconomico, Long>, PagingAndSortingRepository<DatoEconomico, Long> {
	
	@Query("SELECT d.annoRiferimento AS annoRiferimento, d.prezzoVenditaMedio AS prezzoVenditaMedio, d.ortaggio.idOrtaggio AS idOrtaggio "
			+ "FROM DatoEconomico d")
	List<DatoEconomicoOrtaggioProjection> findDatoEconomicoOrtaggioProjection();
}
