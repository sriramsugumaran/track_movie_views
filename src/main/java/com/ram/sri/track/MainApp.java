package com.ram.sri.track;

import com.ram.sri.track.exception.ApplicationConfigurationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * Main class that loads spring context & dependencies
 */
public class MainApp {

    private static final Logger LOGGER = LoggerFactory.getLogger(MainApp.class);

    @SuppressWarnings("resource")
    public static void main(String[] args) throws ApplicationConfigurationException {

        if(LOGGER.isInfoEnabled()) {
            LOGGER.info("Loading Spring Application Context");
        }

        try {
            ApplicationContext context = new ClassPathXmlApplicationContext("spring/batch-context.xml");
        } catch(Exception e) {
            LOGGER.error("Unable to load spring batch context");
            throw new ApplicationConfigurationException(e);
        }

        if(LOGGER.isInfoEnabled()) {
            LOGGER.info("Loaded Spring Application Context");
        }
    }
}
