package com.ram.sri.track.listener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.ExitStatus;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.StepExecutionListener;

public class StepListener implements StepExecutionListener {

    private static final Logger LOGGER = LoggerFactory.getLogger(StepListener.class);

    @Override
    public void beforeStep(StepExecution stepExecution) {
        if(LOGGER.isInfoEnabled()) {
            LOGGER.info("Started executing " + stepExecution.getStepName());
        }
    }

    @Override
    public ExitStatus afterStep(StepExecution stepExecution) {
        if(LOGGER.isInfoEnabled()) {
            LOGGER.info("Completed executing " + stepExecution.getStepName());
        }
        return ExitStatus.COMPLETED;
    }
}
