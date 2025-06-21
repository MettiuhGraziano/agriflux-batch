package com.agriflux.agrifluxbatch.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import com.agriflux.agrifluxbatch.entity.Particella;
import com.agriflux.agrifluxbatch.repository.projection.particella.ParticellaIdAnnoProjection;

@Repository
public interface DatiParticellaRepository extends CrudRepository<Particella, Long>, PagingAndSortingRepository<Particella, Long> {
	
	@Query("SELECT p.idParticella AS idParticella, p.annoInstallazione AS annoInstallazione"
			+ " FROM Particella p")
	List<ParticellaIdAnnoProjection> findAllParticellaIdAnnoProjection();
	
}
