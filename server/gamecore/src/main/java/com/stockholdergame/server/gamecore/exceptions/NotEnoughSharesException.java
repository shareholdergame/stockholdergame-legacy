package com.stockholdergame.server.gamecore.exceptions;

/**
 * @author Alexander Savin
 *         Date: 9.6.12 21.12
 */
public class NotEnoughSharesException extends Exception {
    private static final long serialVersionUID = 4602666865348787048L;

    public NotEnoughSharesException(String s) {
        super(s);
    }
}
