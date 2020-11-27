package com.stockholdergame.server.exceptions;

import com.stockholdergame.server.i18n.ErrorsResourceBundleKeys;

/**
 * Business exception type enum.
 */
public enum BusinessExceptionType {

    DEBT_IS_NOT_REPAID(ErrorsResourceBundleKeys.DEBT_IS_NOT_REPAID),
    FRIEND_REQUEST_ALREADY_SENT(ErrorsResourceBundleKeys.FRIEND_REQUEST_ALREADY_SENT),
    FRIEND_REQUEST_NOT_FOUND(ErrorsResourceBundleKeys.FRIEND_REQUEST_NOT_FOUND),
    GAME_NOT_FOUND(ErrorsResourceBundleKeys.GAME_NOT_FOUND),
    GAME_VARIANT_NOT_FOUND(ErrorsResourceBundleKeys.GAME_VARIANT_NOT_FOUND),
    ILLEGAL_ACCOUNT_STATUS(ErrorsResourceBundleKeys.ILLEGAL_ACCOUNT_STATUS),
    ILLEGAL_FRIEND_REQUEST_STATUS(ErrorsResourceBundleKeys.ILLEGAL_FRIEND_REQUEST_STATUS),
    ILLEGAL_GAME_STATUS(ErrorsResourceBundleKeys.ILLEGAL_GAME_STATUS),
    ILLEGAL_INVITATION_STATUS(ErrorsResourceBundleKeys.ILLEGAL_INVITATION_STATUS),
    INCORRECT_PASSWORD(ErrorsResourceBundleKeys.INCORRECT_PASSWORD),
    INVALID_APPLIED_CARD(ErrorsResourceBundleKeys.INVALID_APPLIED_CARD),
    INVALID_CONFIRMATION_CODE(ErrorsResourceBundleKeys.INVALID_CONFIRMATION_CODE),
    INVALID_EMAIL(ErrorsResourceBundleKeys.INVALID_EMAIL),
    INVITATION_NOT_FOUND(ErrorsResourceBundleKeys.INVITATION_NOT_FOUND),
    MOVE_VALIDATION_FAILED(ErrorsResourceBundleKeys.MOVE_VALIDATION_FAILED),
    NOT_ENOUGH_FUNDS(ErrorsResourceBundleKeys.NOT_ENOUGH_FUNDS),
    NOT_ENOUGH_SHARES(ErrorsResourceBundleKeys.NOT_ENOUGH_SHARES),
    OPERATION_NOT_PERMITTED(ErrorsResourceBundleKeys.OPERATION_NOT_PERMITTED),
    OPERATION_ALREADY_EXISTS(ErrorsResourceBundleKeys.OPERATION_ALREADY_EXISTS),
    TOO_FEW_CARDS(ErrorsResourceBundleKeys.TOO_FEW_CARDS),
    UNSUPPORTED_LANGUAGE(ErrorsResourceBundleKeys.UNSUPPORTED_LANGUAGE),
    USER_ALREADY_FRIEND(ErrorsResourceBundleKeys.USER_ALREADY_FRIEND),
    USER_ALREADY_INVITED(ErrorsResourceBundleKeys.USER_ALREADY_INVITED),
    USER_ALREADY_EXISTS(ErrorsResourceBundleKeys.USER_ALREADY_EXISTS),
    USER_NOT_FOUND(ErrorsResourceBundleKeys.USER_NOT_FOUND),
    OPERATION_NOT_FOUND(ErrorsResourceBundleKeys.OPERATION_NOT_FOUND),
    COMPETITORS_CAPACITY_EXHAUSTED(ErrorsResourceBundleKeys.COMPETITORS_CAPACITY_EXHAUSTED),
    USER_REMOVED(ErrorsResourceBundleKeys.USER_REMOVED),
    USER_INITIATED_GAMES_EXCEEDED(ErrorsResourceBundleKeys.USER_INITIATED_GAMES_EXCEEDED),
    CAPTCHA_NOT_REQUESTED(ErrorsResourceBundleKeys.CAPTCHA_NOT_REQUESTED),
    CAPTCHA_ANSWER_INCORRECT(ErrorsResourceBundleKeys.CAPTCHA_ANSWER_INCORRECT),
    ROOM_NOT_FOUND(ErrorsResourceBundleKeys.ROOM_NOT_FOUND),
    INVALID_COUNTRY(ErrorsResourceBundleKeys.INVALID_COUNTRY),
    GAME_FINISHED(ErrorsResourceBundleKeys.GAME_FINISHED),
    SHARES_LOCKED(ErrorsResourceBundleKeys.SHARES_LOCKED),
    VALIDATION_FAILED(ErrorsResourceBundleKeys.VALIDATION_FAILED),
    COMPETITORS_NUMBER_EXCEEDED(ErrorsResourceBundleKeys.COMPETITORS_NUMBER_EXCEEDED);

    public static BusinessExceptionType[] NOT_FOUND_GROUP = {
            FRIEND_REQUEST_NOT_FOUND,
            GAME_NOT_FOUND,
            GAME_VARIANT_NOT_FOUND,
            INVITATION_NOT_FOUND,
            USER_NOT_FOUND,
            OPERATION_NOT_FOUND
    };

    public static BusinessExceptionType[] NOT_PERMITTED_GROUP = {
            OPERATION_NOT_PERMITTED
    };

    private String messageKey;

    BusinessExceptionType(String messageKey) {
        this.messageKey = messageKey;
    }

    public String getMessageKey() {
        return messageKey;
    }
}
