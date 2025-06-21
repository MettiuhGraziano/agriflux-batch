package com.agriflux.agrifluxbatch.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import com.agriflux.agrifluxbatch.entity.Terreno;
import com.agriflux.agrifluxbatch.repository.projection.terreno.TerrenoMorfologiaColturaProjection;

@Repository
public interface DatiRilevazioneTerrenoRepository extends CrudRepository<Terreno, Long>, PagingAndSortingRepository<Terreno, Long> {
	
//	@Query("SELECT m.idMorfologia AS idMorfologia, c.prodottoColtivato AS prodottoColtivato, c.idColtura AS idColtura, "
//			+ "t.dataRilevazione AS dataRilevazione FROM Terreno t JOIN t.morfologia m JOIN t.coltura c ORDER BY t.dataRilevazione ASC")
//	List<TerrenoMorfologiaColturaProjection> findTerrenoWithMorfologiaAndColturaProjection();
}
