package com.agriflux.agrifluxbatch.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import com.agriflux.agrifluxbatch.entity.Coltura;

@Repository
public interface ColturaRepository extends CrudRepository<Coltura, Long>, PagingAndSortingRepository<Coltura, Long>{

}
