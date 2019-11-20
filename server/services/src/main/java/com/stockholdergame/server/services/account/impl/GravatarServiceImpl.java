package com.stockholdergame.server.services.account.impl;

import com.stockholdergame.server.exceptions.ApplicationException;
import com.stockholdergame.server.services.account.GravatarService;
import com.stockholdergame.server.util.security.MD5Util;
import org.apache.commons.io.IOUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.net.URL;
import java.security.NoSuchAlgorithmException;

/**
 * @author Alexander Savin
 *         Date: 3.4.12 22.50
 */
@Service
public class GravatarServiceImpl implements GravatarService {

    private static Logger LOGGER = LogManager.getLogger(GravatarService.class);

    private final static int DEFAULT_SIZE = 48;

    private final static int SMALL_SIZE = 32;

    private final static String GRAVATAR_URL = "http://www.gravatar.com/avatar/";

    public byte[] downloadAvatar(String userEmail, boolean isSmall) {
        String emailHash = generateHash(userEmail);
        String url = createUrl(emailHash, "404", isSmall ? SMALL_SIZE : DEFAULT_SIZE);
        return download(url);
    }

    private byte[] download(String urlString) {
        InputStream stream = null;
        try {
            URL url = new URL(urlString);
            stream = url.openStream();
            return IOUtils.toByteArray(stream);
        } catch (FileNotFoundException e) {
            LOGGER.warn("Avatar not found on Gravatar service for URL: " + urlString);
            return null;
        } catch (Exception e) {
            throw new ApplicationException("Avatar download failed");
        } finally {
            IOUtils.closeQuietly(stream);
        }
    }

    private String createUrl(String emailHash, String defaultImage, int size) {
        return GRAVATAR_URL + emailHash + "?s=" + size + "&d=" + defaultImage;
    }

    private String generateHash(String userEmail) {
        try {
            return MD5Util.createMD5Hash(userEmail.getBytes());
        } catch (NoSuchAlgorithmException e) {
            throw new ApplicationException(e);
        }
    }
}
