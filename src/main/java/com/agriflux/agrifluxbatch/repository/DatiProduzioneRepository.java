package com.agriflux.agrifluxbatch.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import com.agriflux.agrifluxbatch.entity.Produzione;
import com.agriflux.agrifluxbatch.repository.projection.ProduzioneJoinColturaProjection;

@Repository
public interface DatiProduzioneRepository extends CrudRepository<Produzione, Long>, PagingAndSortingRepository<Produzione, Long> {
	
	@Query("SELECT c.prodottoColtivato AS prodottoColtivato, " +
	           "c.dataRaccolto AS dataRaccolto, " +
	           "p.quantitaRaccolto AS quantitaRaccolto, " +
	           "p.quantitaRaccoltoVenduto AS quantitaRaccoltoVenduto, " +
	           "p.fatturatoColtura AS fatturatoColtura " +
	           "FROM Produzione p JOIN p.coltura c")
	    List<ProduzioneJoinColturaProjection> findProduzioneWithColturaProjection();
}
