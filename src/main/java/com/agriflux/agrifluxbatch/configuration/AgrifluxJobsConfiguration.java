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

import com.agriflux.agrifluxbatch.job.DatiProduzioneFieldSetMapper;
import com.agriflux.agrifluxbatch.job.particella.DatiParticellaRowMapper;
import com.agriflux.agrifluxbatch.job.terreno.DatiTerrenoFieldSetMapper;
import com.agriflux.agrifluxbatch.model.DatiAmbientaliMetadata;
import com.agriflux.agrifluxbatch.model.DatiAmbientaliRecord;
import com.agriflux.agrifluxbatch.model.DatiProduzioneMetadata;
import com.agriflux.agrifluxbatch.model.DatiProduzioneRecord;
import com.agriflux.agrifluxbatch.model.coltura.DatiColturaRecord;
import com.agriflux.agrifluxbatch.model.particella.DatiParticellaMetadata;
import com.agriflux.agrifluxbatch.model.particella.DatiParticellaRecord;
import com.agriflux.agrifluxbatch.model.particella.DatiParticellaRecordReduce;
import com.agriflux.agrifluxbatch.model.terreno.DatiRilevazioneTerrenoMetadata;
import com.agriflux.agrifluxbatch.model.terreno.DatiRilevazioneTerrenoRecord;
import com.agriflux.agrifluxbatch.processor.DatiAmbientaliEnricherProcessor;
import com.agriflux.agrifluxbatch.processor.DatiProduzioneEnricherProcessor;
import com.agriflux.agrifluxbatch.processor.coltura.DatiColturaCustomProcessor;
import com.agriflux.agrifluxbatch.processor.particella.DatiParticellaCustomProcessor;
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
			Step createDatiRilevazioneTerrenoStep) {
		return new JobBuilder("simulationJob", jobRepository)
				.start(createDatiParticellaStep)
				.next(createDatiColturaStep)
				.next(createDatiRilevazioneTerrenoStep)
//				.next(createDatiTerrenoStep)
//				.next(createDatiProduzioneStep)
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
	Step createDatiAmbientaliStep(JobRepository jobRepository, 
			JdbcTransactionManager transactionManager,
			ItemReader<DatiAmbientaliMetadata> datiAmbientaliItemReader,
			ItemWriter<DatiAmbientaliRecord> datiAmbientaliDataTableWriter,
			ItemProcessor<DatiAmbientaliMetadata, DatiAmbientaliRecord> datiAmbientaliEnricherProcessor) {
		return new StepBuilder("createDatiAmbientaliStep", jobRepository).<DatiAmbientaliMetadata, DatiAmbientaliRecord>chunk(10, transactionManager)
					.reader(datiAmbientaliItemReader)
					.processor(datiAmbientaliEnricherProcessor)
					.writer(datiAmbientaliDataTableWriter)
					.build();
	}
	
	@Bean
	@StepScope
	FlatFileItemReader<DatiAmbientaliMetadata> datiAmbientaliItemReader() {
		return new FlatFileItemReaderBuilder<DatiAmbientaliMetadata>()
				.name("datiAmbientaliItemReader")
				.resource(new FileSystemResource("src/main/resources/dati-ambientali-metadata.txt"))
				.delimited()
				.names("temperaturaMedia", "umiditaMedia", "precipitazioni", "irraggiamentoMedio",
						"ombreggiamentoMedio", "dataRilevazione", "idColtura")
				.targetType(DatiAmbientaliMetadata.class)
				.build();
	}
	
	@Bean
	DatiAmbientaliEnricherProcessor datiAmbientaliEnricherProcessor(){
		return new DatiAmbientaliEnricherProcessor();
	}
	
	@Bean
	JdbcBatchItemWriter<DatiAmbientaliRecord> datiAmbientaliDataTableWriter(DataSource dataSource) {
		String sql = """
				INSERT INTO DATI_AMBIENTALI (TEMPERATURA_MEDIA, UMIDITA_MEDIA, PRECIPITAZIONI, 
						IRRAGGIAMENTO_MEDIO, OMBREGGIAMENTO_MEDIO, DATA_RILEVAZIONE, ID_COLTURA)
				VALUES (:temperaturaMedia, :umiditaMedia, :precipitazioni, :irraggiamentoMedio, 
						:ombreggiamentoMedio, :dataRilevazione, :idColtura)
				""";
	    return new JdbcBatchItemWriterBuilder<DatiAmbientaliRecord>()
	            .dataSource(dataSource)
	            .sql(sql)
	            .beanMapped()
	            .build();
	}
	
	
	//TODO QUINTO STEP
	
	@Bean
	Step createDatiProduzioneStep(JobRepository jobRepository, 
			JdbcTransactionManager transactionManager,
			ItemReader<DatiProduzioneMetadata> datiProduzioneItemReader,
			ItemProcessor<DatiProduzioneMetadata, DatiProduzioneRecord> datiProduzioneEnricherProcessor,
			ItemWriter<DatiProduzioneRecord> datiProduzioneDataTableWriter) {
		return new StepBuilder("createDatiProduzioneStep", jobRepository).<DatiProduzioneMetadata, DatiProduzioneRecord>chunk(10, transactionManager)
				.reader(datiProduzioneItemReader)
				.processor(datiProduzioneEnricherProcessor)
				.writer(datiProduzioneDataTableWriter)
				.build();
	}
	
	@Bean
	@StepScope
	FlatFileItemReader<DatiProduzioneMetadata> datiProduzioneItemReader(){
		return new FlatFileItemReaderBuilder<DatiProduzioneMetadata>()
				.name("datiProduzioneItemReader")
				.resource(new FileSystemResource("src/main/resources/dati-produzione-metadata.txt"))
				.delimited()
				.names("numLavoratori", "speseAccessorie", "tempoSemina", "tempoGerminazione", 
						"tempoTrapianto", "tempoMaturazione", "tempoRaccolta", "idColtura",
						"idMorfologia")
				.fieldSetMapper(new DatiProduzioneFieldSetMapper())
				.build();
	}
	
	@Bean
	DatiProduzioneEnricherProcessor datiProduzioneEnricherProcessor() {
		return new DatiProduzioneEnricherProcessor();
	}
	
	@Bean
	JdbcBatchItemWriter<DatiProduzioneRecord> datiProduzioneDataTableWriter(DataSource dataSource) {
		String sql = """
				INSERT INTO DATI_PRODUZIONE (QUANTITA_RACCOLTO, QUANTITA_RACCOLTO_VENDUTO, FATTURATO_COLTURA,
				 	NUM_LAVORATORI, SPESE_ACCESSORIE, TEMPO_SEMINA, TEMPO_GERMINAZIONE, 
				 	TEMPO_TRAPIANTO, TEMPO_MATURAZIONE, TEMPO_RACCOLTA, ID_COLTURA,
				 	ID_MORFOLOGIA)
				VALUES (:quantitaRaccolto, :quantitaRaccoltoVenduto, :fatturatoColtura, :numLavoratori,
				 	:speseAccessorie, :tempoSemina, :tempoGerminazione, :tempoTrapianto,
				 	:tempoMaturazione, :tempoRaccolta, :idColtura, :idMorfologia)
				""";
	    return new JdbcBatchItemWriterBuilder<DatiProduzioneRecord>()
	            .dataSource(dataSource)
	            .sql(sql)
	            .beanMapped()
	            .build();
	}
	
}
