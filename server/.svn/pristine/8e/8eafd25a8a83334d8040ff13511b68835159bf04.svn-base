package com.stockholdergame.server.dao.util;

import java.security.SecureRandom;

/**
 * @author Alexander Savin
 *         Date: 31.3.12 21.55
 */
public final class IdentifierHelper {

    private static IdentifierHelper instance = new IdentifierHelper();

    private SecureRandom rnd;

    private IdentifierHelper() {
        rnd = new SecureRandom();
    }

    public static Long generateLongId() {
        return IdentifierHelper.instance.generateId();
    }

    private Long generateId() {
        rnd.setSeed(System.currentTimeMillis());
        return (long) rnd.nextInt();
    }
}
