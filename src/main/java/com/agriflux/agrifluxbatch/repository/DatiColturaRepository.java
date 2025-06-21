package com.agriflux.agrifluxbatch.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import com.agriflux.agrifluxbatch.entity.Coltura;
import com.agriflux.agrifluxbatch.repository.projection.coltura.ColturaFamigliaOrtaggioProjection;
import com.agriflux.agrifluxbatch.repository.projection.coltura.ColturaNomeOrtaggioProjection;
import com.agriflux.agrifluxbatch.repository.projection.coltura.ColturaProdottoPrezzoDataProjection;

@Repository
public interface DatiColturaRepository extends CrudRepository<Coltura, Long>, PagingAndSortingRepository<Coltura, Long> {
	
	@Query("SELECT c.ortaggio.nome AS nomeOrtaggio FROM Coltura c")
	List<ColturaNomeOrtaggioProjection> findAllNomeOrtaggioProjection();
	
	@Query("SELECT c.ortaggio.famiglia.tipologia AS tipologia FROM Coltura c")
	List<ColturaFamigliaOrtaggioProjection> findAllFamigliaOrtaggioProjection();
	
	@Query("SELECT c.ortaggio.nome AS prodottoColtivato, c.prezzoKg AS prezzoKg, c.dataRaccolto AS dataRaccolto " +
		       "FROM Coltura c ORDER BY c.dataRaccolto ASC")
	List<ColturaProdottoPrezzoDataProjection> findPrezziAndDateColturaProjection();
}
