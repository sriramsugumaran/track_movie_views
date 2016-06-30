package com.ram.sri.track.output.rest;

import com.amazonaws.util.StringUtils;
import com.ram.sri.track.exception.ApplicationConfigurationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;

/**
 * This class retrieves JSON responses from Vimeo service
 */
public class VimeoAccessor {

    private static final Logger LOGGER = LoggerFactory.getLogger(VimeoAccessor.class);
    private static String URI = "https://api.vimeo.com/videos?page=1&query=";
    private static final String AUTH_HEADER = "Authorization";
    private static VimeoAccessor instance = null;
    private static RestTemplate restTemplate;
    private static HttpEntity<String> entity;

    public VimeoAccessor() throws ApplicationConfigurationException {

        try {
            String oauthToken = StringUtils.trim(System.getProperty("vimeo.oauthToken"));
            if (StringUtils.isNullOrEmpty(oauthToken)) {
                throw new ApplicationConfigurationException("Unable to locate OAuth Token from " +
                        "Java system properties (vimeo.oauthToken)");
            } else {
                restTemplate = new RestTemplate();
                HttpHeaders headers = new HttpHeaders();
                headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
                headers.set(AUTH_HEADER, "Bearer " + oauthToken);
                entity = new HttpEntity<String>("parameters", headers);
            }
        } catch (Exception e) {
            LOGGER.error("Unable to configure Vimeo API access");
            throw new ApplicationConfigurationException(e);
        }
    }

    public static VimeoAccessor getInstance() throws ApplicationConfigurationException {
        if (instance == null) {
            instance = new VimeoAccessor();
        }
        return instance;
    }

    public String getResponse(String searchTerm) {

        String jsonResponse = null;

        if (LOGGER.isInfoEnabled()) {
            LOGGER.info("Vimeo URI to fetch- " + URI + searchTerm);
        }

        ResponseEntity<String> result = restTemplate.exchange(URI + searchTerm, HttpMethod.GET, entity, String.class);

        if (result != null) {
            jsonResponse = result.getBody();
            if (LOGGER.isInfoEnabled()) {
                LOGGER.info("Json retrieved for URI- " + URI + searchTerm);
            }

            if(LOGGER.isDebugEnabled()) {
                LOGGER.debug(result.toString());
            }
        } else {
            if (LOGGER.isInfoEnabled()) {
                LOGGER.info("No views retrieved for the search term - " + searchTerm);
            }
        }

        return jsonResponse;
    }
}
