package com.agriflux.agrifluxbatch.controller.job;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.configuration.JobRegistry;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController("api/jobLauncher")
public class JobLauncherController {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(JobLauncherController.class);
	
	private final JobLauncher jobLauncher;
	private final JobRegistry jobRegistry;
	private final JobRepository jobRepository;
	
	public JobLauncherController(JobLauncher jobLauncher, JobRegistry jobRegistry, JobRepository jobRepository) {
		this.jobLauncher = jobLauncher;
		this.jobRegistry = jobRegistry;
		this.jobRepository = jobRepository;
	}
	
	//TODO PER GARANTIRE UNICITA' NEI SINGOLI JOB INSERIRE UN ID INCREMENTALE O UN PARAMETRO CHE CAMBIA AD OGNI LANCIO
	// USANDO UN METODO POST POSSO PASSARE I PARAMETRI DI LANCIO IN MANIERA DINAMICA
	// RICORDA CHE NEL CONTROLLER NON SI ESEGUONO LOGICHE DI BUSINESS MA SI RITORNA SOLO LA ResponseEntity<String>
    @PostMapping("/simulationJob.html")
    public void simulationJob() throws Exception{
    	
    	LOGGER.info("Inizio fase di lancio del job: {}", jobRegistry.getJob("simulationJob"));
    	
    	JobParameters params = new JobParametersBuilder()
    		    .addLong("time", System.currentTimeMillis())
    		    .toJobParameters();
    	
        jobLauncher.run(jobRegistry.getJob("simulationJob"), params);
    }
    
    //TODO CENSIRE METODO GET CHE RITORNA LO STATO DI ESECUZIONE DEL JOB "X"
    
    @GetMapping("/isJobExecuted")
    public Boolean isJobExecuted() {
    	List<org.springframework.batch.core.JobInstance> jobInstancesByName = jobRepository.findJobInstancesByName("simulationJob", 0, 1);
    	
    	if (null != jobInstancesByName && !jobInstancesByName.isEmpty()) {
			return true;
		}
    	
    	return false;
    }
}
