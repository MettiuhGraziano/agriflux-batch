package com.agriflux.agrifluxbatch.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import com.agriflux.agrifluxbatch.entity.Litologia;

@Repository
public interface DatiLitologiaRepository extends CrudRepository<Litologia, Long>, PagingAndSortingRepository<Litologia, Long> {
	
	@Query("SELECT COUNT(l) FROM Litologia l")
	int countAllLitologie();
}
