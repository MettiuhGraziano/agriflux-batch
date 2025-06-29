package com.agriflux.agrifluxbatch.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import com.agriflux.agrifluxbatch.entity.Azienda;

@Repository
public interface DatiAziendaRepository extends CrudRepository<Azienda, Long>, PagingAndSortingRepository<Azienda, Long> {
	
	Azienda findAziendaByIdAzienda(long idAzienda);
}
