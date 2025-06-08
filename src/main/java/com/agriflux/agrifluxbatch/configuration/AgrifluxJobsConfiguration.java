package com.agriflux.agrifluxbatch.configuration;

import javax.sql.DataSource;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.database.JdbcBatchItemWriter;
import org.springframework.batch.item.database.builder.JdbcBatchItemWriterBuilder;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.builder.FlatFileItemReaderBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.FileSystemResource;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;
import org.springframework.jdbc.support.JdbcTransactionManager;

import com.agriflux.agrifluxbatch.job.ColturaMetadataFieldSetMapper;
import com.agriflux.agrifluxbatch.job.DatiProduzioneFieldSetMapper;
import com.agriflux.agrifluxbatch.job.DatiTerrenoFieldSetMapper;
import com.agriflux.agrifluxbatch.model.ColturaMetadata;
import com.agriflux.agrifluxbatch.model.ColturaRecord;
import com.agriflux.agrifluxbatch.model.DatiAmbientaliMetadata;
import com.agriflux.agrifluxbatch.model.DatiAmbientaliRecord;
import com.agriflux.agrifluxbatch.model.DatiMorfologiciMetadata;
import com.agriflux.agrifluxbatch.model.DatiMorfologiciRecord;
import com.agriflux.agrifluxbatch.model.DatiProduzioneMetadata;
import com.agriflux.agrifluxbatch.model.DatiProduzioneRecord;
import com.agriflux.agrifluxbatch.model.DatiTerrenoMetadata;
import com.agriflux.agrifluxbatch.model.DatiTerrenoRecord;
import com.agriflux.agrifluxbatch.processor.ColturaEnricherProcessor;
import com.agriflux.agrifluxbatch.processor.DatiAmbientaliEnricherProcessor;
import com.agriflux.agrifluxbatch.processor.DatiMorfologiciEnricherProcessor;
import com.agriflux.agrifluxbatch.processor.DatiProduzioneEnricherProcessor;
import com.agriflux.agrifluxbatch.processor.DatiTerrenoEnricherProcessor;

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
	Job simulatorJob(JobRepository jobRepository, Step createColturaStep, Step createDatiAmbientaliStep,
			Step createDatiMorfologiciStep, Step createDatiTerrenoStep, Step createDatiProduzioneStep) {
		return new JobBuilder("simulatorJob", jobRepository)
				.start(createColturaStep)
				.next(createDatiAmbientaliStep)
				.next(createDatiMorfologiciStep)
				.next(createDatiTerrenoStep)
				.next(createDatiProduzioneStep)
				.build();
	}
	
	//PRIMO STEP
	
	@Bean
	Step createColturaStep(JobRepository jobRepository, 
			JdbcTransactionManager transactionManager, 
			ItemReader<ColturaMetadata> colturaMetadataFileReader,
			ItemProcessor<ColturaMetadata, ColturaRecord> colturaEnricherProcessor,
			ItemWriter<ColturaRecord> colturaDataTableWriter) {
		return new StepBuilder("createColturaStep", jobRepository).<ColturaMetadata, ColturaRecord>chunk(10, transactionManager)
					.reader(colturaMetadataFileReader)
					.processor(colturaEnricherProcessor)
					.writer(colturaDataTableWriter)
					.build();
	}
	
	@Bean
	FlatFileItemReader<ColturaMetadata> colturaMetadataFileReader() {
	    return new FlatFileItemReaderBuilder<ColturaMetadata>()
	            .name("colturaMetadataFileReader")
	            .resource(new FileSystemResource("src/main/resources/dati-coltura-metadata.txt"))
	            .delimited()
	            .names("prezzoKg", "dataSemina", "dataRaccolto")
	            .fieldSetMapper(new ColturaMetadataFieldSetMapper())
	            .build();
	}
	
	@Bean
	ColturaEnricherProcessor colturaEnricherProcessor() {
		return new ColturaEnricherProcessor();
	}
	
	@Bean
	JdbcBatchItemWriter<ColturaRecord> colturaDataTableWriter(DataSource dataSource) {
		String sql = """
				INSERT INTO DATI_COLTURA (PRODOTTO_COLTIVATO, PREZZO_KG, DATA_SEMINA, DATA_RACCOLTO)
				VALUES (:prodottoColtivato, :prezzoKg, :dataSemina, :dataRaccolto)
				""";
	    return new JdbcBatchItemWriterBuilder<ColturaRecord>()
	            .dataSource(dataSource)
	            .sql(sql)
	            .beanMapped()
	            .build();
	}
	
	//SECONDO STEP
	
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
						"ombreggiamentoMedio", "dataRilevazione", "fkIdColtura")
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
						IRRAGGIAMENTO_MEDIO, OMBREGGIAMENTO_MEDIO, DATA_RILEVAZIONE, FK_ID_COLTURA)
				VALUES (:temperaturaMedia, :umiditaMedia, :precipitazioni, :irraggiamentoMedio, 
						:ombreggiamentoMedio, :dataRilevazione, :fkIdColtura)
				""";
	    return new JdbcBatchItemWriterBuilder<DatiAmbientaliRecord>()
	            .dataSource(dataSource)
	            .sql(sql)
	            .beanMapped()
	            .build();
	}
	
	//TERZO STEP
	
	@Bean
	Step createDatiMorfologiciStep(JobRepository jobRepository, 
			JdbcTransactionManager transactionManager,
			ItemReader<DatiMorfologiciMetadata> datiMorfologiciItemReader,
			ItemProcessor<DatiMorfologiciMetadata, DatiMorfologiciRecord> datiMorfologiciEnricherProcessor,
			ItemWriter<DatiMorfologiciRecord> datiMorfologiciDataTableWriter) {
		return new StepBuilder("createDatiMorfologiciStep", jobRepository).<DatiMorfologiciMetadata, DatiMorfologiciRecord>chunk(10, transactionManager)
				.reader(datiMorfologiciItemReader)
				.processor(datiMorfologiciEnricherProcessor)
				.writer(datiMorfologiciDataTableWriter)
				.build();
	}
	
	@Bean
	@StepScope
	FlatFileItemReader<DatiMorfologiciMetadata> datiMorfologiciItemReader() {
		return new FlatFileItemReaderBuilder<DatiMorfologiciMetadata>()
				.name("datiMorfologiciItemReader")
				.resource(new FileSystemResource("src/main/resources/dati-morfologici-metadata.txt"))
				.delimited()
				.names("estensioneTerreno", "pendenza", "esposizione", "litologia")
				.targetType(DatiMorfologiciMetadata.class)
				.build();
	}
	
	@Bean
	DatiMorfologiciEnricherProcessor datiMorfologiciEnricherProcessor() {
		return new DatiMorfologiciEnricherProcessor();
	}
	
	@Bean
	JdbcBatchItemWriter<DatiMorfologiciRecord> datiMorfologiciDataTableWriter(DataSource dataSource) {
		String sql = """
				INSERT INTO DATI_MORFOLOGICI (ESTENSIONE_TERRENO, PENDENZA, ESPOSIZIONE, LITOLOGIA)
				VALUES (:estensioneTerreno, :pendenza, :esposizione, :litologia)
				""";
	    return new JdbcBatchItemWriterBuilder<DatiMorfologiciRecord>()
	            .dataSource(dataSource)
	            .sql(sql)
	            .beanMapped()
	            .build();
	}
	
	//QUARTO STEP
	
	@Bean
	Step createDatiTerrenoStep(JobRepository jobRepository, 
			JdbcTransactionManager transactionManager,
			ItemReader<DatiTerrenoMetadata> datiTerrenoItemReader,
			ItemProcessor<DatiTerrenoMetadata, DatiTerrenoRecord> datiTerrenoErnicherProcessor,
			ItemWriter<DatiTerrenoRecord> datiTerrenoDataTableWriter) {
		return new StepBuilder("createDatiTerrenoStep", jobRepository).<DatiTerrenoMetadata, DatiTerrenoRecord>chunk(10, transactionManager)
				.reader(datiTerrenoItemReader)
				.processor(datiTerrenoErnicherProcessor)
				.writer(datiTerrenoDataTableWriter)
				.build();
	}
	
	@Bean
	@StepScope
	FlatFileItemReader<DatiTerrenoMetadata> datiTerrenoItemReader(){
		return new FlatFileItemReaderBuilder<DatiTerrenoMetadata>()
				.name("datiTerrenoItemReader")
				.resource(new FileSystemResource("src/main/resources/dati-terreno-metadata.txt"))
				.delimited()
				.names("phSuolo", "umidita", "capacitaAssorbente", "porosita", "temperatura", 
						"disponibilitaIrrigua", "dataRilevazione", "fkIdColtura", "fkIdMorfologia")
				.fieldSetMapper(new DatiTerrenoFieldSetMapper())
				.build();
	}
	
	@Bean
	DatiTerrenoEnricherProcessor datiTerrenoErnicherProcessor() {
		return new DatiTerrenoEnricherProcessor();
	}
	
	@Bean
	JdbcBatchItemWriter<DatiTerrenoRecord> datiTerrenoDataTableWriter(DataSource dataSource) {
		String sql = """
				INSERT INTO DATI_TERRENO (PH_SUOLO, UMIDITA, CAPACITA_ASSORBENTE, POROSITA, TEMPERATURA, 
					DISPONIBILITA_IRRIGUA, DATA_RILEVAZIONE, FK_ID_COLTURA, FK_ID_MORFOLOGIA)
				VALUES (:phSuolo, :umidita, :capacitaAssorbente, :porosita, :temperatura, :disponibilitaIrrigua,
					:dataRilevazione, :fkIdColtura, :fkIdMorfologia)
				""";
	    return new JdbcBatchItemWriterBuilder<DatiTerrenoRecord>()
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
						"tempoTrapianto", "tempoMaturazione", "tempoRaccolta", "fkIdColtura",
						"fkIdMorfologia")
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
				 	TEMPO_TRAPIANTO, TEMPO_MATURAZIONE, TEMPO_RACCOLTA, FK_ID_COLTURA,
				 	FK_ID_MORFOLOGIA)
				VALUES (:quantitaRaccolto, :quantitaRaccoltoVenduto, :fatturatoColtura, :numLavoratori,
				 	:speseAccessorie, :tempoSemina, :tempoGerminazione, :tempoTrapianto,
				 	:tempoMaturazione, :tempoRaccolta, :fkIdColtura, :fkIdMorfologia)
				""";
	    return new JdbcBatchItemWriterBuilder<DatiProduzioneRecord>()
	            .dataSource(dataSource)
	            .sql(sql)
	            .beanMapped()
	            .build();
	}
	
}
