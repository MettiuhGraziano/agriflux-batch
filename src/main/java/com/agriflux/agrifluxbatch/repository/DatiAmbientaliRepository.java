package com.agriflux.agrifluxbatch.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import com.agriflux.agrifluxbatch.entity.Ambiente;

@Repository
public interface DatiAmbientaliRepository extends CrudRepository<Ambiente, Long>, PagingAndSortingRepository<Ambiente, Long> {

}
