package com.ram.sri.track.output.aws;

import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.util.StringUtils;
import com.ram.sri.track.exception.ApplicationConfigurationException;

/**
 * This class checks for AWS & Vimeo keys required for the communication
 */
public abstract class AbstractAWSCredentials {

    private String accessKey;
    private String secretKey;

    public AbstractAWSCredentials() throws ApplicationConfigurationException {
        accessKey = StringUtils.trim(System.getProperty("aws.accessKeyId"));
        secretKey = StringUtils.trim(System.getProperty("aws.secretKey"));

        if (StringUtils.isNullOrEmpty(accessKey) || StringUtils.isNullOrEmpty(secretKey)) {
            throw new ApplicationConfigurationException("Unable to locate AWS credentials from " +
                    "Java system properties (aws.accessKeyId and aws.secretKey)");
        }
    }

    public BasicAWSCredentials getCredentials() {
        return new BasicAWSCredentials(accessKey, secretKey);
    }
}
