package com.agriflux.agrifluxbatch.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import com.agriflux.agrifluxbatch.entity.Coltura;
import com.agriflux.agrifluxshared.dto.ColturaGroupByProdottoDTO;

@Repository
public interface DatiColturaRepository extends CrudRepository<Coltura, Long>, PagingAndSortingRepository<Coltura, Long> {
	
	@Query("SELECT new com.agriflux.agrifluxshared.dto.ColturaGroupByProdottoDTO(coltura.prodottoColtivato, COUNT(coltura))"
			+ " FROM Coltura coltura GROUP BY coltura.prodottoColtivato")
	List<ColturaGroupByProdottoDTO> countColtureGroupByProdotto();
}
