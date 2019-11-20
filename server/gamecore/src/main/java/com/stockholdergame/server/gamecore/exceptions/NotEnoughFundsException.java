package com.stockholdergame.server.gamecore.exceptions;

/**
 * @author Alexander Savin
 *         Date: 11.6.12 23.09
 */
public class NotEnoughFundsException extends Exception {

    private static final long serialVersionUID = 9044092576355365532L;

    public NotEnoughFundsException(String s) {
        super(s);
    }
}
