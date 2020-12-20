package com.stockholdergame.server.exceptions;

import com.stockholdergame.server.localization.MessageHolder;

/**
 * @author Alexander Savin
 *         Date: 29.5.2010 13.38.30
 */
public class BusinessException extends RuntimeException {

    private BusinessExceptionType type;

    private Object[] args;

    public BusinessException() {
    }

    public BusinessException(BusinessExceptionType type, Object... args) {
        this.type = type;
        this.args = args;
    }

    public BusinessException(String message, BusinessExceptionType type) {
        super(message);
        this.type = type;
    }

    public String getMessage() {
        return type != null && args != null ? MessageHolder.getMessage(type.getMessageKey(), args) : super.getMessage();
    }

    public BusinessExceptionType getType() {
        return type;
    }

    public Object[] getArgs() {
        return args;
    }
}
