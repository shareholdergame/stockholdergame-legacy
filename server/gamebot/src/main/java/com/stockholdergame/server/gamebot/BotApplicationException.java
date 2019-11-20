package com.stockholdergame.server.gamebot;

/**
 * Application exception.
 */
public class BotApplicationException extends RuntimeException {

    private static final long serialVersionUID = -4331040078367872461L;

    public BotApplicationException() {
        super();
    }

    public BotApplicationException(String s) {
        super(s);
    }

    public BotApplicationException(String s, Throwable throwable) {
        super(s, throwable);
    }

    public BotApplicationException(Throwable throwable) {
        super(throwable);
    }
}
