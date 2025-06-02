package com.agriflux.agrifluxbatch.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import com.agriflux.agrifluxbatch.entity.Produzione;

@Repository
public interface DatiProduzioneRepository extends CrudRepository<Produzione, Long>, PagingAndSortingRepository<Produzione, Long> {

}
