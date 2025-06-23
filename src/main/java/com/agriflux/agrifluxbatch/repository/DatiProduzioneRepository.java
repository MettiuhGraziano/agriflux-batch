package com.agriflux.agrifluxbatch.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import com.agriflux.agrifluxbatch.entity.Produzione;
import com.agriflux.agrifluxbatch.repository.projection.ProduzioneParticellaColturaOrtaggioProjection;
import com.agriflux.agrifluxbatch.repository.projection.produzione.ProduzioneJoinColturaFatturatoProjection;
import com.agriflux.agrifluxbatch.repository.projection.produzione.ProduzioneJoinColturaTempiProjection;
import com.agriflux.agrifluxbatch.repository.projection.produzione.ProduzioneQuantitaJoinColturaProjection;

@Repository
public interface DatiProduzioneRepository extends CrudRepository<Produzione, Long>, PagingAndSortingRepository<Produzione, Long> {
	
	@Query("SELECT p.fatturatoRaccolto AS fatturatoRaccolto, p.speseProduzione AS speseProduzione, "
			+ "c.particella.idParticella AS idParticella, YEAR(c.dataRaccolto) AS annoRaccolto "
			+ "FROM Produzione p JOIN p.coltura c")
	List<ProduzioneJoinColturaFatturatoProjection> findProduzioneJoinColturaFatturatoProjection();
	
	@Query("SELECT p.quantitaRaccolto AS quantitaRaccolto, p.quantitaRaccoltoVenduto AS quantitaRaccoltoVenduto, "
			+ "p.quantitaRaccoltoMarcio AS quantitaRaccoltoMarcio, p.quantitaRaccoltoTerzi AS quantitaRaccoltoTerzi, "
			+ "p.fatturatoRaccolto AS fatturatoRaccolto, YEAR(c.dataRaccolto) AS annoRaccolto, o.nome AS nomeOrtaggio "
			+ "FROM Produzione p JOIN p.coltura c JOIN c.ortaggio o")
	List<ProduzioneQuantitaJoinColturaProjection> findProduzioneQuantitaJoinColturaProjection();
	
	@Query("SELECT o.nome AS nomeOrtaggio, " + "YEAR(c.dataSemina) AS annoSemina, "
			+ "o.giorniSemina AS tempoSemina, " + "o.giorniGerminazione AS tempoGerminazione, "
			+ "o.giorniTrapianto AS tempoTrapianto, " + "o.giorniMaturazione AS tempoMaturazione, "
			+ "o.giorniRaccolta AS tempoRaccolta " + "FROM Produzione p JOIN p.coltura c JOIN c.ortaggio o")
	List<ProduzioneJoinColturaTempiProjection> findProduzioneJoinColturaTempiProjection();
	
	@Query("SELECT p.idProduzione AS idProduzione, " + " o.nome AS nomeOrtaggio, " + "c.dataRaccolto AS dataRaccolto, "
			+ "m.idParticella AS idParticella, " + "m.estensione AS estensione, " + "m.pendenza AS pendenza, "
			+ "m.esposizione AS esposizione, " + "l.tipologia AS tipologia "
			+ "FROM Produzione p JOIN p.coltura c JOIN c.ortaggio o JOIN c.particella m JOIN m.litologia l")
	List<ProduzioneParticellaColturaOrtaggioProjection> findProduzioneParticellaColturaOrtaggioProjection();
	
}
