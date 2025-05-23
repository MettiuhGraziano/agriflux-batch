package com.agriflux.agrifluxbatch;

import javax.sql.DataSource;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.FlatFileItemWriter;
import org.springframework.batch.item.file.builder.FlatFileItemReaderBuilder;
import org.springframework.batch.item.file.builder.FlatFileItemWriterBuilder;
import org.springframework.batch.item.file.mapping.DefaultLineMapper;
import org.springframework.batch.item.file.transform.DelimitedLineAggregator;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.FileSystemResource;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;
import org.springframework.transaction.PlatformTransactionManager;

import com.agriflux.agrifluxbatch.model.StaticMetadata;
import com.agriflux.agrifluxbatch.model.StaticMetadataFieldSetMapper;

@Configuration
@EnableBatchProcessing(dataSourceRef = "batchDataSource")
public class AgrifluxJobsConfiguration {
	
	@Bean
	DataSource batchDataSource() {
		return new EmbeddedDatabaseBuilder().setType(EmbeddedDatabaseType.H2)
				.addDefaultScripts()
				.generateUniqueName(true).build();
	}

	//TODO Definire i vari job taggandoli come @Bean
    
    @Bean
    Job testJob(JobRepository jobRepository, Step testStep) {
        return new JobBuilder("testJob", jobRepository)
                         .start(testStep)
                         .build();
    }
    
	//TODO Definire gli step da inserire nei job
	
	@Bean
    Step testStep(JobRepository jobRepository, 
    		PlatformTransactionManager batchTransactionManager,
    		ItemReader<StaticMetadata> staticMetadataFileReader,
    		ItemWriter<StaticMetadata> staticMetadataFileItemWriter) { 
    	return new StepBuilder("testStep", jobRepository)
    				.<StaticMetadata, StaticMetadata>chunk(10, batchTransactionManager) 
    				.reader(staticMetadataFileReader)
    				.writer(staticMetadataFileItemWriter)
    				.build();
    }
    
    //TODO Definire itemReader e itemWriter
    
    @Bean
    FlatFileItemReader<StaticMetadata> staticMetadataFileReader() {
    	DefaultLineMapper<StaticMetadata> lineMapper = new DefaultLineMapper<StaticMetadata>();
    	DelimitedLineTokenizer lineTokenizer = new DelimitedLineTokenizer();
    	
    	lineTokenizer.setNames("temperatura", "umidita", "quantita_raccolto", "costo_raccolto");
    	
    	lineMapper.setLineTokenizer(lineTokenizer);
    	lineMapper.setFieldSetMapper(new StaticMetadataFieldSetMapper());
    	
        return new FlatFileItemReaderBuilder<StaticMetadata>()
        		.name("staticMetadataFileReader")
                .resource(new FileSystemResource("src/main/resources/metadata-exemple.txt"))
                .lineMapper(lineMapper)
                .targetType(StaticMetadata.class)
                .build();
    }
    
	@Bean
	FlatFileItemWriter<StaticMetadata> staticMetadataFileItemWriter() {
		DelimitedLineAggregator<StaticMetadata> lineAggregator = new DelimitedLineAggregator<>();
		lineAggregator.setDelimiter("||");

		return new FlatFileItemWriterBuilder<StaticMetadata>().name("staticMetadataFileItemWriter")
				.resource(new FileSystemResource("src/main/resources/output.txt")).lineAggregator(lineAggregator)
				.build();
	}
	
	//TODO Creare itemProcessor per manipolare i dati che escono dall'itemReader e inviarli all'itemWriter
    
}
