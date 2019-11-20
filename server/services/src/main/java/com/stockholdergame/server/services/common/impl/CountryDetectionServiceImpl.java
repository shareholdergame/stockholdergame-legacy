package com.stockholdergame.server.services.common.impl;

import com.stockholdergame.server.exceptions.ApplicationException;
import com.stockholdergame.server.model.common.UserLocation;
import com.stockholdergame.server.services.common.CountryDetectionService;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.JSONObject;
import org.springframework.stereotype.Service;

import java.io.InputStream;
import java.net.URL;
import java.text.MessageFormat;

/**
 *
 */
@Service
public class CountryDetectionServiceImpl implements CountryDetectionService {

    private static Logger LOGGER = LogManager.getLogger(CountryDetectionServiceImpl.class);

    @Override
    public UserLocation detectUserLocation(String userIPAddress) {
        LOGGER.info("Detect country for user IP: " + userIPAddress);
        String response = sendGeoIpRequest(userIPAddress);
        LOGGER.info("Country detection service response: " + response);
        if (StringUtils.isBlank(response)) {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            response = sendGeoIpRequest(userIPAddress);
        }
        JSONObject jsonObject = new JSONObject(response);
        UserLocation userLocation = new UserLocation();
        userLocation.setCountry(jsonObject.getString("country_name"));
        userLocation.setRegion(jsonObject.getString("region_name"));
        userLocation.setCity(jsonObject.getString("city"));
        return userLocation;
    }

    private String sendGeoIpRequest(String userIPAddress) {
        String urlString = MessageFormat.format("http://freegeoip.net/json/{0}", userIPAddress);
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
