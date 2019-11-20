package com.stockholdergame.server.helpers;

import com.stockholdergame.server.util.security.RandomStringGenerator;

/**
 * @author Alexander Savin
 *         Date: 27.3.11 12.33
 */
public class MessagingHelper {

    public static String generateSubtopicName() {
        String currentTime = String.valueOf(System.currentTimeMillis());
        String randomString = RandomStringGenerator.generate();
        return MD5Helper.generateMD5hash(currentTime + randomString);
    }
}
