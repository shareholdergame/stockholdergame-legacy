package com.stockholdergame.server.gamecore.exceptions;

/**
 * @author Alexander Savin
 *         Date: 9.6.12 21.12
 */
public class SharesLockedException extends Exception {

    private static final long serialVersionUID = 5588162177104368993L;

    public SharesLockedException(String s) {
        super(s);
    }
}
