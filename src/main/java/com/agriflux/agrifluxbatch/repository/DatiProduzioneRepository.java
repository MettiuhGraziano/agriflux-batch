package com.agriflux.agrifluxbatch.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import com.agriflux.agrifluxbatch.entity.Produzione;
import com.agriflux.agrifluxbatch.repository.projection.ProduzioneJoinColturaProjection;
import com.agriflux.agrifluxbatch.repository.projection.ProduzioneJoinColturaTempiProjection;
import com.agriflux.agrifluxbatch.repository.projection.ProduzioneMorfologiaColturaProjection;

@Repository
public interface DatiProduzioneRepository extends CrudRepository<Produzione, Long>, PagingAndSortingRepository<Produzione, Long> {
	
	@Query("SELECT c.prodottoColtivato AS prodottoColtivato, " + "YEAR(c.dataRaccolto) AS annoRaccolto, "
			+ "p.quantitaRaccolto AS quantitaRaccolto, " + "p.quantitaRaccoltoVenduto AS quantitaRaccoltoVenduto, "
			+ "p.fatturatoColtura AS fatturatoColtura " + "FROM Produzione p JOIN p.coltura c")
	List<ProduzioneJoinColturaProjection> findProduzioneWithColturaProjection();
	
	
	@Query("SELECT c.prodottoColtivato AS prodottoColtivato, " + "YEAR(c.dataSemina) AS annoSemina, "
			+ "p.tempoSemina AS tempoSemina, " + "p.tempoGerminazione AS tempoGerminazione, "
			+ "p.tempoTrapianto AS tempoTrapianto, " + "p.tempoMaturazione AS tempoMaturazione, "
			+ "p.tempoRaccolta AS tempoRaccolta " + "FROM Produzione p JOIN p.coltura c")
	List<ProduzioneJoinColturaTempiProjection> findProduzioneWithColturaTempiProjection();
	
	@Query("SELECT p.id AS idProduzione, " + " c.prodottoColtivato AS prodottoColtivato, "
			+ "c.dataRaccolto AS dataRaccolto, " + "m.idMorfologia AS idMorfologia, "
			+ "m.estensioneTerreno AS estensioneTerreno, " + "m.pendenza AS pendenza, "
			+ "m.esposizione AS esposizione, " + "m.litologia AS litologia "
			+ "FROM Produzione p JOIN p.coltura c JOIN p.morfologia m")
	List<ProduzioneMorfologiaColturaProjection> findProduzioneWithColturaAndMorfologiaProjection();
	
}
