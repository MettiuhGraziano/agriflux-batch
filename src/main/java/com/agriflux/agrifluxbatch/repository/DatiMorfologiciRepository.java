package com.agriflux.agrifluxbatch.repository;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import com.agriflux.agrifluxbatch.entity.Morfologia;

//TODO ESTENDI ANCHE CrudRepository

@Repository
public interface DatiMorfologiciRepository extends PagingAndSortingRepository<Morfologia, Long>{

}
