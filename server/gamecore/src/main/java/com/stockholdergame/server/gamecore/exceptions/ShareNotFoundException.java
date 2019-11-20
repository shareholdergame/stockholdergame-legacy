package com.stockholdergame.server.gamecore.exceptions;

/**
 * @author Alexander Savin
 *         Date: 9.6.12 21.12
 */
public class ShareNotFoundException extends Exception {

    public ShareNotFoundException(Long shareId) {
        super(shareId.toString());
    }
}
