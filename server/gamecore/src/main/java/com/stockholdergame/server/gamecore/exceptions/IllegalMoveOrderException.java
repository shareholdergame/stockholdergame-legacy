package com.stockholdergame.server.gamecore.exceptions;

/**
 * @author Alexander Savin
 *         Date: 23.9.12 16.38
 */
public class IllegalMoveOrderException extends Exception {

    public IllegalMoveOrderException(int moveOrder) {
        super(String.valueOf(moveOrder));
    }
}
