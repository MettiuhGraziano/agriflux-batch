package com.agriflux.agrifluxbatch.configuration;

import javax.sql.DataSource;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
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
import com.agriflux.agrifluxbatch.processor.ColturaRecordProcessor;

@Configuration
@EnableBatchProcessing(dataSourceRef = "batchDataSource", transactionManagerRef = "batchTransactionManager")
public class AgrifluxJobsConfiguration {
	
	@Bean
	DataSource batchDataSource() {
		return new EmbeddedDatabaseBuilder().setType(EmbeddedDatabaseType.H2)
				.addDefaultScripts()
				.generateUniqueName(true)
				.build();
	}
	
	@Bean
	JdbcTransactionManager batchTransactionManager(DataSource dataSource) {
		return new JdbcTransactionManager(dataSource);
	}
	
	@Bean
	Job simulatorJob(JobRepository jobRepository, Step createColturaStep) {
		return new JobBuilder("simulatorJob", jobRepository)
				.start(createColturaStep)
				.build();
	}
	
	@Bean
	Step createColturaStep(JobRepository jobRepository, 
			JdbcTransactionManager batchTransactionManager,
			ItemReader<ColturaMetadata> colturaMetadataFileReader,
			ItemWriter<ColturaRecord> colturaDataTableWriter,
			ItemProcessor<ColturaMetadata, ColturaRecord> colturaRecordProcessor) {
		return new StepBuilder("createColturaStep", jobRepository).<ColturaMetadata, ColturaRecord>chunk(5, batchTransactionManager)
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
	            .targetType(ColturaMetadata.class)
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

}
