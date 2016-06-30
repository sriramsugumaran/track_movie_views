package com.ram.sri.track.schedule;

import com.ram.sri.track.exception.ApplicationExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.*;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.stereotype.Component;

/**
 * This class schedules the job execution based on the configured time frame and
 * sets the job parameter with a unique id
 */
@Component
public class ViewScheduler {

    private static final Logger LOGGER = LoggerFactory.getLogger(ViewScheduler.class);
    private static final String JOB_PARAMETER = "TIME";
    private JobLauncher jobLauncher;
    private Job job;

    public void launch() throws ApplicationExecutionException {

        JobParameters jobParameters = new JobParametersBuilder().addLong(JOB_PARAMETER,
                System.currentTimeMillis()).toJobParameters();

        if(LOGGER.isInfoEnabled()) {
            LOGGER.info("Setting the job parameter: time - " + jobParameters.getLong(JOB_PARAMETER));
        }

        try {
            LOGGER.info("Job started to track movie views");

            JobExecution execution = jobLauncher.run(job, jobParameters);

            LOGGER.info("Job execution is complete with status - " + execution.getStatus());
            LOGGER.info("Job started @ " + execution.getStartTime());
            LOGGER.info("Job completed @ " + execution.getEndTime());
        } catch (Exception e) {
            LOGGER.error("Unable to execute the job");
            throw new ApplicationExecutionException(e);
        }
    }

    public JobLauncher getJobLauncher() {
        return jobLauncher;
    }

    public void setJobLauncher(JobLauncher jobLauncher) {
        this.jobLauncher = jobLauncher;
    }

    public Job getJob() {
        return job;
    }

    public void setJob(Job job) {
        this.job = job;
    }
}
