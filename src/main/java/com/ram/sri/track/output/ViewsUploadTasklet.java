package com.ram.sri.track.output;

import com.ram.sri.track.output.dao.IDataAccessor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;

import java.io.File;

/**
 * This class fetches all views and uploads it to S3 storage
 */
public class ViewsUploadTasklet implements Tasklet {

    private static final Logger LOGGER = LoggerFactory.getLogger(ViewsUploadTasklet.class);
    private IDataAccessor dataAccessor;

    @Override
    public RepeatStatus execute(StepContribution stepContribution, ChunkContext chunkContext) throws Exception {

        File viewsInAFile = dataAccessor.getAllViewsInFile();
        if(LOGGER.isInfoEnabled()) {
            LOGGER.info("Retrieved all views from database");
        }

        dataAccessor.uploadViewsInFile(viewsInAFile);
        if(LOGGER.isInfoEnabled()) {
            LOGGER.info("Uploaded all views to storage");
        }

        viewsInAFile.delete();
        if(LOGGER.isInfoEnabled()) {
            LOGGER.info("Deleted the temp view file- " + viewsInAFile.toString());
        }

        return RepeatStatus.FINISHED ;
    }

    public IDataAccessor getDataAccessor() {
        return dataAccessor;
    }

    public void setDataAccessor(IDataAccessor dataAccessor) {
        this.dataAccessor = dataAccessor;
    }
}
