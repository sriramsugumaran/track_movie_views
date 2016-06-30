package com.ram.sri.track.exception;

public class ApplicationConfigurationException extends Exception {

    public ApplicationConfigurationException(String message, Throwable t) {
        super(message, t);
    }

    public ApplicationConfigurationException(String message) {
        super(message);
    }

    public ApplicationConfigurationException(Throwable t) {
        super(t);
    }
}
