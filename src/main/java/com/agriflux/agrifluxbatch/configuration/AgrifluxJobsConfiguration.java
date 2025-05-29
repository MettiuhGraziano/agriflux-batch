package com.agriflux.agrifluxbatch.configuration;

import java.util.List;

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

import com.agriflux.agrifluxbatch.model.ColturaMetadata;
import com.agriflux.agrifluxbatch.model.ColturaRecord;
import com.agriflux.agrifluxbatch.model.DatiAmbientaliMetadata;
import com.agriflux.agrifluxbatch.model.DatiAmbientaliRecord;
import com.agriflux.agrifluxbatch.processor.ColturaRecordProcessor;
import com.agriflux.agrifluxbatch.processor.DatiAmbientaliEnricherProcessor;

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
	
	//JOB SIMULATORE
	
	@Bean
	Job simulatorJob(JobRepository jobRepository, Step createColturaStep, Step createDatiAmbientaliStep) {
		return new JobBuilder("simulatorJob", jobRepository)
				.start(createColturaStep)
				.next(createDatiAmbientaliStep)
				.build();
	}
	
	//PRIMO STEP
	
	@Bean
	Step createColturaStep(JobRepository jobRepository, 
			JdbcTransactionManager transactionManager,
			ItemReader<ColturaMetadata> colturaMetadataFileReader,
			ItemWriter<ColturaRecord> colturaDataTableWriter,
			ItemProcessor<ColturaMetadata, ColturaRecord> colturaRecordProcessor) {
		return new StepBuilder("createColturaStep", jobRepository).<ColturaMetadata, ColturaRecord>chunk(10, transactionManager)
					.reader(colturaMetadataFileReader)
					.processor(colturaRecordProcessor)
					.writer(colturaDataTableWriter)
					.build();
	}
	
	@Bean
	FlatFileItemReader<ColturaMetadata> colturaMetadataFileReader() {
	    return new FlatFileItemReaderBuilder<ColturaMetadata>()
	            .name("colturaMetadataFileReader")
	            .resource(new FileSystemResource("src/main/resources/coltura-metadata.txt"))
	            .delimited()
	            .names("annoMeseSemina", "annoMeseRaccolto")
	            .fieldSetMapper(new ColturaMetadataFieldSetMapper())
	            .build();
	}
	
	@Bean
	JdbcBatchItemWriter<ColturaRecord> colturaDataTableWriter(DataSource dataSource) {
		String sql = """
				INSERT INTO DATI_COLTURA (PRODOTTO_COLTIVATO, ANNO_MESE_SEMINA, ANNO_MESE_RACCOLTO)
				VALUES (:prodottoColtivato, :annoMeseSemina, :annoMeseRaccolto)
				""";
	    return new JdbcBatchItemWriterBuilder<ColturaRecord>()
	            .dataSource(dataSource)
	            .sql(sql)
	            .beanMapped()
	            .build();
	}
	
	@Bean
	ColturaRecordProcessor colturaRecordProcessor() {
		return new ColturaRecordProcessor();
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
	JdbcBatchItemWriter<DatiAmbientaliRecord> datiAmbientaliDataTableWriter(DataSource dataSource) {
		String sql = """
				INSERT INTO DATI_AMBIENTALI (TEMPERATURA_MEDIA, UMIDITA_MEDIA, PRECIPITAZIONI, 
						IRRAGGIAMENTO_MEDIO, OMBREGGIAMENTO_MEDIO, ANNO_MESE_RILEVAZIONE, FK_ID_COLTURA)
				VALUES (:temperaturaMedia, :umiditaMedia, :precipitazioni, :irraggiamentoMedio, 
						:ombreggiamentoMedio, :annoMeseRilevazione, :fkIdColtura)
				""";
	    return new JdbcBatchItemWriterBuilder<DatiAmbientaliRecord>()
	            .dataSource(dataSource)
	            .sql(sql)
	            .beanMapped()
	            .build();
	}
	
	@Bean
	DatiAmbientaliEnricherProcessor datiAmbientaliEnricherProcessor(){
		return new DatiAmbientaliEnricherProcessor();
	}

}
