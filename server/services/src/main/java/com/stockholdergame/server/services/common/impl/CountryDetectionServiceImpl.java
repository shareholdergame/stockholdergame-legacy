package com.stockholdergame.server.services.common.impl;

import com.stockholdergame.server.exceptions.ApplicationException;
import com.stockholdergame.server.model.common.UserLocation;
import com.stockholdergame.server.services.common.CountryDetectionService;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.map.ObjectMapper;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.text.MessageFormat;

/**
 *
 */
@Service
public class CountryDetectionServiceImpl implements CountryDetectionService {

    private static final String IPSTACK_URL = "http://api.ipstack.com/{0}?access_key={1}";
    private static final String COUNTRY_NAME = "country_name";
    private static final String REGION_NAME = "region_name";
    private static final String CITY = "city";
    private static final String ERROR_NODE = "error";
    private static final String INFO_NODE = "info";

    private static Logger LOGGER = LogManager.getLogger(CountryDetectionServiceImpl.class);

    @Autowired
    @Qualifier("ipStackApiKey")
    private String ipStackApiKey;

    private ObjectMapper mapper = new ObjectMapper();

    @Override
    public UserLocation detectUserLocation(String userIPAddress) {
        LOGGER.info("Detect country for user IP: " + userIPAddress);
        String response = sendGeoIpRequest(userIPAddress);
        LOGGER.info("Country detection service response: " + response);
        try {
            JsonNode jsonNode = mapper.readTree(response);
            if (jsonNode.has(COUNTRY_NAME)) {
                UserLocation userLocation = new UserLocation();
                userLocation.setCountry(jsonNode.get(COUNTRY_NAME).asText());
                userLocation.setRegion(jsonNode.get(REGION_NAME).asText());
                userLocation.setCity(jsonNode.get(CITY).asText());
                return userLocation;
            } else if (jsonNode.has(ERROR_NODE) && jsonNode.get(ERROR_NODE).has(INFO_NODE)) {
                throw new ApplicationException(jsonNode.get(ERROR_NODE).get(INFO_NODE).asText());
            } else {
                throw new ApplicationException("Unexpected response format: " + response);
            }
        } catch (IOException e) {
            throw new ApplicationException(e);
        }
    }

    private String sendGeoIpRequest(String userIPAddress) {
        String urlString = MessageFormat.format(IPSTACK_URL, userIPAddress, ipStackApiKey);
        InputStream stream = null;
        try {
            URL url = new URL(urlString);
            stream = url.openStream();
            return IOUtils.toString(stream);
        } catch (Exception e) {
            throw new ApplicationException("Location by IP request failed");
        } finally {
            IOUtils.closeQuietly(stream);
        }
    }
}
