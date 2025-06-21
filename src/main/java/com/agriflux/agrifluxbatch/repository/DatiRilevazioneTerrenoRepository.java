package com.agriflux.agrifluxbatch.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import com.agriflux.agrifluxbatch.entity.Terreno;

@Repository
public interface DatiRilevazioneTerrenoRepository extends CrudRepository<Terreno, Long>, PagingAndSortingRepository<Terreno, Long> {
	
}
