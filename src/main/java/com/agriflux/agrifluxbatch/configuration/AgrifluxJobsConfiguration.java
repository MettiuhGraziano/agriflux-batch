package com.agriflux.agrifluxbatch.configuration;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import javax.sql.DataSource;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.Chunk;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.database.JdbcBatchItemWriter;
import org.springframework.batch.item.database.JdbcCursorItemReader;
import org.springframework.batch.item.database.builder.JdbcBatchItemWriterBuilder;
import org.springframework.batch.item.database.builder.JdbcCursorItemReaderBuilder;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.builder.FlatFileItemReaderBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.FileSystemResource;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;
import org.springframework.jdbc.support.JdbcTransactionManager;

import com.agriflux.agrifluxbatch.job.particella.DatiParticellaFatturatoRowMapper;
import com.agriflux.agrifluxbatch.job.particella.DatiParticellaRowMapper;
import com.agriflux.agrifluxbatch.job.produzione.DatiProduzioneRowMapper;
import com.agriflux.agrifluxbatch.job.stagione.DatiStagioneRowMapper;
import com.agriflux.agrifluxbatch.job.terreno.DatiTerrenoFieldSetMapper;
import com.agriflux.agrifluxbatch.model.ambiente.DatiAmbienteRecord;
import com.agriflux.agrifluxbatch.model.coltura.DatiColturaRecord;
import com.agriflux.agrifluxbatch.model.fatturato.DatiFatturatoRecord;
import com.agriflux.agrifluxbatch.model.particella.DatiParticellaFatturatoRecord;
import com.agriflux.agrifluxbatch.model.particella.DatiParticellaMetadata;
import com.agriflux.agrifluxbatch.model.particella.DatiParticellaRecord;
import com.agriflux.agrifluxbatch.model.particella.DatiParticellaRecordReduce;
import com.agriflux.agrifluxbatch.model.produzione.DatiColturaJoinParticellaRecord;
import com.agriflux.agrifluxbatch.model.produzione.DatiProduzioneRecord;
import com.agriflux.agrifluxbatch.model.stagione.DatiStagioneRecord;
import com.agriflux.agrifluxbatch.model.terreno.DatiRilevazioneTerrenoMetadata;
import com.agriflux.agrifluxbatch.model.terreno.DatiRilevazioneTerrenoRecord;
import com.agriflux.agrifluxbatch.processor.ambiente.DatiAmbienteCustomProcessor;
import com.agriflux.agrifluxbatch.processor.coltura.DatiColturaCustomProcessor;
import com.agriflux.agrifluxbatch.processor.fatturato.DatiFatturatoCustomProcessor;
import com.agriflux.agrifluxbatch.processor.particella.DatiParticellaCustomProcessor;
import com.agriflux.agrifluxbatch.processor.produzione.DatiProduzioneCustomProcessor;
import com.agriflux.agrifluxbatch.processor.terreno.DatiRilevazioneTerrenoCustomProcessor;

@Configuration
@EnableBatchProcessing(dataSourceRef = "batchDataSource", transactionManagerRef = "transactionManager")
public class AgrifluxJobsConfiguration {

	@Bean
	DataSource batchDataSource() {
		return new EmbeddedDatabaseBuilder().setType(EmbeddedDatabaseType.H2)
				.addDefaultScripts()
				.generateUniqueName(true)
				.build();
	}
	
	@Bean
	JdbcTransactionManager transactionManager(DataSource dataSource) {
		return new JdbcTransactionManager(dataSource);
	}
	
	//JOB SIMULAZIONE
	
	@Bean
	Job simulationJob(JobRepository jobRepository, Step createDatiParticellaStep, Step createDatiColturaStep,
			Step createDatiRilevazioneTerrenoStep, Step createDatiProduzioneStep, Step createDatiAmbienteStep,
			Step createDatiFatturatoStep) {
		return new JobBuilder("simulationJob", jobRepository)
				.start(createDatiParticellaStep)
				.next(createDatiColturaStep)
				.next(createDatiRilevazioneTerrenoStep)
				.next(createDatiProduzioneStep)
				.next(createDatiAmbienteStep)
				.next(createDatiFatturatoStep)
				.build();
	}
	
	//PRIMO STEP
	
	@Bean
	Step createDatiParticellaStep(JobRepository jobRepository, 
			JdbcTransactionManager transactionManager,
			ItemReader<DatiParticellaMetadata> datiParticellaItemReader,
			ItemProcessor<DatiParticellaMetadata, DatiParticellaRecord> datiParticellaCustomItemProcessor,
			ItemWriter<DatiParticellaRecord> datiParticellaDataTableWriter) {
		return new StepBuilder("createDatiParticellaStep", jobRepository).<DatiParticellaMetadata, DatiParticellaRecord>chunk(10, transactionManager)
				.reader(datiParticellaItemReader)
				.processor(datiParticellaCustomItemProcessor)
				.writer(datiParticellaDataTableWriter)
				.build();
	}
	
	@Bean
	@StepScope
	FlatFileItemReader<DatiParticellaMetadata> datiParticellaItemReader() {
		return new FlatFileItemReaderBuilder<DatiParticellaMetadata>()
				.name("datiParticellaItemReader")
				.resource(new FileSystemResource("src/main/resources/dati-particella-metadata.txt"))
				.delimited()
				.names("comune", "foglio", "qualita", "annoInstallazione", "costo", "estensione", "pendenza", "esposizione")
				.targetType(DatiParticellaMetadata.class)
				.build();
	}
	
	@Bean
	DatiParticellaCustomProcessor datiParticellaCustomProcessor() {
		return new DatiParticellaCustomProcessor();
	}
	
	@Bean
	JdbcBatchItemWriter<DatiParticellaRecord> datiParticellaDataTableWriter(DataSource dataSource) {
		String sql = """
				INSERT INTO DATI_PARTICELLA (COMUNE, FOGLIO, QUALITA, ANNO_INSTALLAZIONE, COSTO, ESTENSIONE, PENDENZA, ESPOSIZIONE, ID_LITOLOGIA)
				VALUES (:comune, :foglio, :qualita, :annoInstallazione, :costo, :estensione, :pendenza, :esposizione, :idLitologia)
				""";
	    return new JdbcBatchItemWriterBuilder<DatiParticellaRecord>()
	            .dataSource(dataSource)
	            .sql(sql)
	            .beanMapped()
	            .build();
	}
	
	//SECONDO STEP
	
	@Bean
	Step createDatiColturaStep(JobRepository jobRepository, 
			JdbcTransactionManager transactionManager, 
			ItemReader<DatiParticellaRecordReduce> datiParticellaCustomItemReader,
			ItemProcessor<DatiParticellaRecordReduce, List<DatiColturaRecord>> datiColturaCustomProcessor,
			JdbcBatchItemWriter<DatiColturaRecord> datiColturaDataTableWriter) {
		return new StepBuilder("createDatiColturaStep", jobRepository).<DatiParticellaRecordReduce, List<DatiColturaRecord>>chunk(10, transactionManager)
					.reader(datiParticellaCustomItemReader)
					.processor(datiColturaCustomProcessor)
					.writer(appiattisciColturaRecordListItemWriter(datiColturaDataTableWriter))
					.build();
	}
	
	@Bean
	JdbcCursorItemReader<DatiParticellaRecordReduce> datiParticellaCustomItemReader() {
		return new JdbcCursorItemReaderBuilder<DatiParticellaRecordReduce>()
				.dataSource(batchDataSource())
				.name("datiParticellaCustomItemReader")
				.sql("SELECT ID_PARTICELLA, ANNO_INSTALLAZIONE FROM DATI_PARTICELLA")
				.rowMapper(new DatiParticellaRowMapper())
				.build();
	}
	
	@Bean
	DatiColturaCustomProcessor datiColturaCustomProcessor() {
		return new DatiColturaCustomProcessor();
	}

	@Bean
	JdbcBatchItemWriter<DatiColturaRecord> datiColturaDataTableWriter(DataSource dataSource) {
		String sql = """
				INSERT INTO DATI_COLTURA (PREZZO_KG, DATA_SEMINA, DATA_RACCOLTO, ID_PARTICELLA, ID_ORTAGGIO)
				VALUES (:prezzoKg, :dataSemina, :dataRaccolto, :idParticella, :idOrtaggio)
				""";
	    return new JdbcBatchItemWriterBuilder<DatiColturaRecord>()
	            .dataSource(dataSource)
	            .sql(sql)
	            .beanMapped()
	            .build();
	}
	
	@Bean
	ItemWriter<List<DatiColturaRecord>> appiattisciColturaRecordListItemWriter(JdbcBatchItemWriter<DatiColturaRecord> jdbcItemWriter) {
	    return items -> {
	        List<DatiColturaRecord> listaAppiattita = items.getItems().stream()
	            .flatMap(Collection::stream)
	            .collect(Collectors.toList());

	        /*
	         * Dato che Spring Batch non gestisce in scrittura una lista di oggetti ma un Chunk,
	         * ne creo uno manuale ad hoc.
	         */
	        Chunk<DatiColturaRecord> chunk = new Chunk<>(listaAppiattita);

	        jdbcItemWriter.write(chunk);
	    };
	}


	//TERZO STEP
	
	@Bean
	Step createDatiRilevazioneTerrenoStep(JobRepository jobRepository, 
			JdbcTransactionManager transactionManager,
			ItemReader<DatiRilevazioneTerrenoMetadata> datiRilevazioneTerrenoItemReader,
			ItemProcessor<DatiRilevazioneTerrenoMetadata, List<DatiRilevazioneTerrenoRecord>> datiRilevazioneTerrenoCustomProcessor,
			JdbcBatchItemWriter<DatiRilevazioneTerrenoRecord> datiRilevazioneTerrenoDataTableWriter) {
		return new StepBuilder("createDatiRilevazioneTerrenoStep", jobRepository).<DatiRilevazioneTerrenoMetadata, List<DatiRilevazioneTerrenoRecord>>chunk(10, transactionManager)
				.reader(datiRilevazioneTerrenoItemReader)
				.processor(datiRilevazioneTerrenoCustomProcessor)
				.writer(appiattisciRilevazioneTerrenoRecordListItemWriter(datiRilevazioneTerrenoDataTableWriter))
				.build();
	}
	
	@Bean
	@StepScope
	FlatFileItemReader<DatiRilevazioneTerrenoMetadata> datiRilevazioneTerrenoItemReader(){
		return new FlatFileItemReaderBuilder<DatiRilevazioneTerrenoMetadata>()
				.name("datiTerrenoItemReader")
				.resource(new FileSystemResource("src/main/resources/dati-rilevazione-terreno-metadata.txt"))
				.delimited()
				.names("phSuolo", "umidita", "capacitaAssorbente", "porosita", "temperatura")
				.fieldSetMapper(new DatiTerrenoFieldSetMapper())
				.build();
	}
	
	@Bean
	DatiRilevazioneTerrenoCustomProcessor datiRilevazioneTerrenoCustomProcessor() {
		return new DatiRilevazioneTerrenoCustomProcessor();
	}
	
	@Bean
	JdbcBatchItemWriter<DatiRilevazioneTerrenoRecord> datiRilevazioneTerrenoDataTableWriter(DataSource dataSource) {
		String sql = """
				INSERT INTO DATI_RILEVAZIONE_TERRENO (PH_SUOLO, UMIDITA, CAPACITA_ASSORBENTE, POROSITA, TEMPERATURA,
				 	DATA_RILEVAZIONE, ID_PARTICELLA)
				VALUES (:phSuolo, :umidita, :capacitaAssorbente, :porosita, :temperatura,
					:dataRilevazione, :idParticella)
				""";
	    return new JdbcBatchItemWriterBuilder<DatiRilevazioneTerrenoRecord>()
	            .dataSource(dataSource)
	            .sql(sql)
	            .beanMapped()
	            .build();
	}
	
	@Bean
	ItemWriter<List<DatiRilevazioneTerrenoRecord>> appiattisciRilevazioneTerrenoRecordListItemWriter(JdbcBatchItemWriter<DatiRilevazioneTerrenoRecord> jdbcItemWriter) {
	    return items -> {
	        List<DatiRilevazioneTerrenoRecord> listaAppiattita = items.getItems().stream()
	            .flatMap(Collection::stream)
	            .collect(Collectors.toList());

	        /*
	         * Dato che Spring Batch non gestisce in scrittura una lista di oggetti ma un Chunk,
	         * ne creo uno manuale ad hoc.
	         */
	        Chunk<DatiRilevazioneTerrenoRecord> chunk = new Chunk<>(listaAppiattita);

	        jdbcItemWriter.write(chunk);
	    };
	}
	
	//QUARTO STEP
	
	@Bean
	Step createDatiProduzioneStep(JobRepository jobRepository, 
			JdbcTransactionManager transactionManager,
			ItemReader<DatiColturaJoinParticellaRecord> datiProduzioneCustomItemReader,
			ItemProcessor<DatiColturaJoinParticellaRecord, DatiProduzioneRecord> datiProduzioneCustomProcessor,
			ItemWriter<DatiProduzioneRecord> datiProduzioneDataTableWriter) {
		return new StepBuilder("createDatiProduzioneStep", jobRepository).<DatiColturaJoinParticellaRecord, DatiProduzioneRecord>chunk(10, transactionManager)
				.reader(datiProduzioneCustomItemReader)
				.processor(datiProduzioneCustomProcessor)
				.writer(datiProduzioneDataTableWriter)
				.build();
	}
	
	@Bean
	JdbcCursorItemReader<DatiColturaJoinParticellaRecord> datiProduzioneCustomItemReader() {
		return new JdbcCursorItemReaderBuilder<DatiColturaJoinParticellaRecord>()
				.dataSource(batchDataSource())
				.name("datiProduzioneCustomItemReader")
				.sql("""
						SELECT c.ID_COLTURA, c.PREZZO_KG, p.ESTENSIONE, o.PESO_MEDIO, o.VOLUME_MQ
						FROM DATI_COLTURA c
						LEFT JOIN DATI_PARTICELLA p ON c.ID_PARTICELLA = p.ID_PARTICELLA
						LEFT JOIN DATI_ORTAGGIO o ON c.ID_ORTAGGIO = o.ID_ORTAGGIO
						ORDER BY c.ID_COLTURA ASC
						""")
				.rowMapper(new DatiProduzioneRowMapper())
				.build();
	}
	
	@Bean
	DatiProduzioneCustomProcessor datiProduzioneCustomProcessor() {
		return new DatiProduzioneCustomProcessor();
	}
	
	@Bean
	JdbcBatchItemWriter<DatiProduzioneRecord> datiProduzioneDataTableWriter(DataSource dataSource) {
		String sql = """
				INSERT INTO DATI_PRODUZIONE (QUANTITA_RACCOLTO, QUANTITA_RACCOLTO_VENDUTO,
				QUANTITA_RACCOLTO_MARCIO, QUANTITA_RACCOLTO_TERZI, FATTURATO_RACCOLTO,
				 	NUM_LAVORATORI, SPESE_PRODUZIONE, ID_COLTURA)
				VALUES (:quantitaRaccolto, :quantitaRaccoltoVenduto, :quantitaRaccoltoMarcio,
					:quantitaRaccoltoTerzi, :fatturatoRaccolto, :numLavoratori, :speseProduzione, :idColtura)
				""";
		return new JdbcBatchItemWriterBuilder<DatiProduzioneRecord>().dataSource(dataSource).sql(sql).beanMapped()
				.build();
	}
	
	//QUINTO STEP
	
	@Bean
	Step createDatiAmbienteStep(JobRepository jobRepository, 
			JdbcTransactionManager transactionManager,
			ItemReader<DatiStagioneRecord> datiStagioneItemReader,
			ItemProcessor<DatiStagioneRecord, List<DatiAmbienteRecord>> datiAmbienteCustomProcessor,
			JdbcBatchItemWriter<DatiAmbienteRecord> datiAmbientaliDataTableWriter) {
		return new StepBuilder("createDatiAmbienteStep", jobRepository).<DatiStagioneRecord, List<DatiAmbienteRecord>>chunk(10, transactionManager)
				.reader(datiStagioneItemReader)
				.processor(datiAmbienteCustomProcessor)
				.writer(appiattisciAmbienteRecordListItemWriter(datiAmbientaliDataTableWriter))
				.build();
	}
	
	@Bean
	JdbcCursorItemReader<DatiStagioneRecord> datiStagioneItemReader() {
		return new JdbcCursorItemReaderBuilder<DatiStagioneRecord>()
				.dataSource(batchDataSource())
				.name("datiStagioneItemReader")
				.sql("SELECT NOME, MESE_GIORNO_INIZIO, MESE_GIORNO_FINE, RANGE_TEMPERATURA, "
						+ "RANGE_UMIDITA, RANGE_PRECIPITAZIONI, RANGE_IRRAGGIAMENTO, "
						+ "RANGE_OMBREGGIAMENTO FROM DATI_STAGIONE")
				.rowMapper(new DatiStagioneRowMapper())
				.build();
	}
	
	@Bean
	DatiAmbienteCustomProcessor datiAmbienteCustomProcessor(){
		return new DatiAmbienteCustomProcessor();
	}
	
	@Bean
	JdbcBatchItemWriter<DatiAmbienteRecord> datiAmbientaliDataTableWriter(DataSource dataSource) {
		String sql = """
				INSERT INTO DATI_AMBIENTE (TEMPERATURA_MEDIA, UMIDITA_MEDIA, PRECIPITAZIONI, 
						IRRAGGIAMENTO_MEDIO, OMBREGGIAMENTO_MEDIO, DATA_RILEVAZIONE)
				VALUES (:temperaturaMedia, :umiditaMedia, :precipitazioni, :irraggiamentoMedio, 
						:ombreggiamentoMedio, :dataRilevazione)
				""";
		return new JdbcBatchItemWriterBuilder<DatiAmbienteRecord>()
				.dataSource(dataSource)
				.sql(sql)
				.beanMapped()
				.build();
	}
	
	@Bean
	ItemWriter<List<DatiAmbienteRecord>> appiattisciAmbienteRecordListItemWriter(JdbcBatchItemWriter<DatiAmbienteRecord> jdbcItemWriter) {
	    return items -> {
	        List<DatiAmbienteRecord> listaAppiattita = items.getItems().stream()
	            .flatMap(Collection::stream)
	            .collect(Collectors.toList());

	        /*
	         * Dato che Spring Batch non gestisce in scrittura una lista di oggetti ma un Chunk,
	         * ne creo uno manuale ad hoc.
	         */
	        Chunk<DatiAmbienteRecord> chunk = new Chunk<>(listaAppiattita);

	        jdbcItemWriter.write(chunk);
	    };
	}
	
	//TODO SESTO STEP
	
	@Bean
	Step createDatiFatturatoStep(JobRepository jobRepository, 
			JdbcTransactionManager transactionManager,
			ItemReader<DatiParticellaFatturatoRecord> datiParticellaFatturatoCustomItemReader,
			ItemProcessor<DatiParticellaFatturatoRecord, List<DatiFatturatoRecord>> datiFatturatoCustomProcessor,
			JdbcBatchItemWriter<DatiFatturatoRecord> datiFatturatoDataTableWriter) {
		return new StepBuilder("createDatiFatturatoStep", jobRepository).<DatiParticellaFatturatoRecord, List<DatiFatturatoRecord>>chunk(10, transactionManager)
				.reader(datiParticellaFatturatoCustomItemReader)
				.processor(datiFatturatoCustomProcessor)
				.writer(appiattisciFatturatoRecordListItemWriter(datiFatturatoDataTableWriter))
				.build();
	}
	
	@Bean
	JdbcCursorItemReader<DatiParticellaFatturatoRecord> datiParticellaFatturatoCustomItemReader() {
		return new JdbcCursorItemReaderBuilder<DatiParticellaFatturatoRecord>()
				.dataSource(batchDataSource())
				.name("datiParticellaFatturatoCustomItemReader")
				.sql("SELECT ID_PARTICELLA, ANNO_INSTALLAZIONE, COSTO FROM DATI_PARTICELLA")
				.rowMapper(new DatiParticellaFatturatoRowMapper())
				.build();
	}
	
	@Bean
	DatiFatturatoCustomProcessor datiFatturatoCustomProcessor(){
		return new DatiFatturatoCustomProcessor();
	}
	
	@Bean
	JdbcBatchItemWriter<DatiFatturatoRecord> datiFatturatoDataTableWriter(DataSource dataSource) {
		String sql = """
				INSERT INTO DATI_FATTURATO (ANNO_RIFERIMENTO, RICAVI_VENDITA, SPESE_GENERALI, 
						INTERESSI_ATTIVI, INTERESSI_PASSIVI, ID_PARTICELLA)
				VALUES (:annoRiferimento, :ricaviVendita, :speseGenerali, :interessiAttivi, 
						:interessiPassivi, :idParticella)
				""";
		return new JdbcBatchItemWriterBuilder<DatiFatturatoRecord>()
				.dataSource(dataSource)
				.sql(sql)
				.beanMapped()
				.build();
	}
	
	@Bean
	ItemWriter<List<DatiFatturatoRecord>> appiattisciFatturatoRecordListItemWriter(JdbcBatchItemWriter<DatiFatturatoRecord> jdbcItemWriter) {
	    return items -> {
	        List<DatiFatturatoRecord> listaAppiattita = items.getItems().stream()
	            .flatMap(Collection::stream)
	            .collect(Collectors.toList());

	        /*
	         * Dato che Spring Batch non gestisce in scrittura una lista di oggetti ma un Chunk,
	         * ne creo uno manuale ad hoc.
	         */
	        Chunk<DatiFatturatoRecord> chunk = new Chunk<>(listaAppiattita);

	        jdbcItemWriter.write(chunk);
	    };
	}
	
}
