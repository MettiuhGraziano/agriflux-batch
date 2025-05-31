package com.agriflux.agrifluxbatch.repository;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import com.agriflux.agrifluxbatch.entity.Coltura;

//TODO ESTENDI ANCHE CrudRepository

@Repository
public interface ColturaRepository extends PagingAndSortingRepository<Coltura, Long>{

}
