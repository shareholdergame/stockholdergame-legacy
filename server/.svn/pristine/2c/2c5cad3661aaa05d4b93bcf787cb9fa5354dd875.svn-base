package com.stockholdergame.server.helpers;

import com.stockholdergame.server.util.security.MD5Util;
import com.stockholdergame.server.util.security.RandomStringGenerator;

import java.security.NoSuchAlgorithmException;

import static java.text.MessageFormat.format;

/**
 * @author Alexander Savin
 *         Date: 24.4.2010 18.22.28
 */
public class MD5Helper {

    private static final String HASH_WITH_SALT = "{0}/{1}";

    public static String generateMD5hash(String str) {
        try {
            return MD5Util.createStrongMD5Hash(str.getBytes());
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }

    public static String generateMD5hashWithSalt(String str) {
        try {
            String salt = RandomStringGenerator.generate();
            String digest = MD5Util.createStrongMD5Hash(salt.getBytes(), str.getBytes());
            return format(HASH_WITH_SALT, salt, digest);
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }

    public static boolean checkMD5hash(String str, String hash) {
        String[] saltAndHash = hash.split("/");
        try {
            if (saltAndHash.length > 1) {
                String hash1 = MD5Util.createStrongMD5Hash(saltAndHash[0].getBytes(), str.getBytes());
                return saltAndHash[1].equals(hash1);
            } else {
                String hash1 = MD5Util.createStrongMD5Hash(str.getBytes());
                return hash.equals(hash1);
            }
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }
}
