package com.agriflux.agrifluxbatch.job;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecution;

public class SimulatorJob implements Job {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(SimulatorJob.class);
	
	@Override
	public String getName() {
		return "simulatorJob";
	}

	@Override
	public void execute(JobExecution execution) {
		LOGGER.info("START JOB <{}>", getName());
		LOGGER.info("EXECUTION: {}", execution.toString());
	}

}
