package com.agriflux.agrifluxbatch.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import com.agriflux.agrifluxbatch.entity.Coltura;
import com.agriflux.agrifluxbatch.repository.projection.ColturaProdottoPrezzoDataProjection;
import com.agriflux.agrifluxshared.dto.coltura.ColturaGroupByProdottoDTO;

@Repository
public interface DatiColturaRepository extends CrudRepository<Coltura, Long>, PagingAndSortingRepository<Coltura, Long> {
	
//	@Query("SELECT new com.agriflux.agrifluxshared.dto.ColturaGroupByProdottoDTO(c.prodottoColtivato, COUNT(c))"
//			+ " FROM Coltura c GROUP BY c.prodottoColtivato")
//	List<ColturaGroupByProdottoDTO> countColtureGroupByProdotto();
//	
//	@Query("SELECT c.prodottoColtivato AS prodottoColtivato, c.prezzoKg AS prezzoKg, c.dataRaccolto AS dataRaccolto " +
//		       "FROM Coltura c ORDER BY c.dataRaccolto ASC")
//	List<ColturaProdottoPrezzoDataProjection> findPrezziAndDateColturaProjection();
}
