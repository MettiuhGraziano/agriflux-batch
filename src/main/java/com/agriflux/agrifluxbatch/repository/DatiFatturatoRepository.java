package com.agriflux.agrifluxbatch.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import com.agriflux.agrifluxbatch.entity.Fatturato;

@Repository
public interface DatiFatturatoRepository extends CrudRepository<Fatturato, Long>, PagingAndSortingRepository<Fatturato, Long> {
	
	List<Fatturato> findAllByOrderByIdFatturatoAsc();
}
