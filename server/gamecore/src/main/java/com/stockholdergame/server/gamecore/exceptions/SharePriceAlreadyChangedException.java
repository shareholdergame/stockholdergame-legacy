package com.stockholdergame.server.gamecore.exceptions;

/**
 * @author Alexander Savin
 *         Date: 17.6.12 10.43
 */
public class SharePriceAlreadyChangedException extends Exception {

    public SharePriceAlreadyChangedException(Long shareId) {
        super(shareId.toString());
    }
}
