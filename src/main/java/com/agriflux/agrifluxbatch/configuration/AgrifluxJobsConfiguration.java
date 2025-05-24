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
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.FlatFileItemWriter;
import org.springframework.batch.item.file.builder.FlatFileItemReaderBuilder;
import org.springframework.batch.item.file.builder.FlatFileItemWriterBuilder;
import org.springframework.batch.item.file.mapping.DefaultLineMapper;
import org.springframework.batch.item.file.transform.BeanWrapperFieldExtractor;
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
import com.agriflux.agrifluxbatch.model.StaticRandomMetadata;

@Configuration
@EnableBatchProcessing(dataSourceRef = "batchDataSource")
public class AgrifluxJobsConfiguration {
	
	@Bean
	DataSource batchDataSource() {
		return new EmbeddedDatabaseBuilder().setType(EmbeddedDatabaseType.H2)
				.addDefaultScripts()
				.generateUniqueName(true).build();
	}

    @Bean
    Job testJob(JobRepository jobRepository, Step testStep) {
        return new JobBuilder("testJob", jobRepository)
                         .start(testStep)
                         .build();
    }
    
	@Bean
    Step testStep(JobRepository jobRepository, 
    		PlatformTransactionManager batchTransactionManager,
    		ItemReader<StaticMetadata> staticMetadataFileReader,
    		ItemWriter<StaticRandomMetadata> staticMetadataFileWriter,
    		ItemProcessor<StaticMetadata, StaticRandomMetadata> staticMetadataProcessor) { 
    	return new StepBuilder("testStep", jobRepository)
    				.<StaticMetadata, StaticRandomMetadata>chunk(10, batchTransactionManager) 
    				.reader(staticMetadataFileReader)
    				.processor(staticMetadataProcessor)
    				.writer(staticMetadataFileWriter)
    				.build();
    }
    
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
	FlatFileItemWriter<StaticRandomMetadata> staticMetadataFileWriter() {
		BeanWrapperFieldExtractor<StaticRandomMetadata> fieldExtractor = new BeanWrapperFieldExtractor<>();
		fieldExtractor.setNames(new String[] {"temperatura", "umidita", "quantita_raccolto", "costo_raccolto"});
		fieldExtractor.afterPropertiesSet();
		
		DelimitedLineAggregator<StaticRandomMetadata> lineAggregator = new DelimitedLineAggregator<>();
		lineAggregator.setDelimiter(" / ");
		lineAggregator.setFieldExtractor(fieldExtractor);

		return new FlatFileItemWriterBuilder<StaticRandomMetadata>()
				.name("staticMetadataFileWriter")
				.resource(new FileSystemResource("src/main/resources/output.txt"))
				.lineAggregator(lineAggregator)
				.build();
	}
	
	@Bean
    GenerateStaticRandomMetadataProcessor staticMetadataProcessor() {
    	return new GenerateStaticRandomMetadataProcessor();
    }
}
