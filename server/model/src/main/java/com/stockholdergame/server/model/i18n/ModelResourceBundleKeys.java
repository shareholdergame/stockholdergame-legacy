package com.stockholdergame.server.model.i18n;

/**
 * @author Alexander Savin
 */
public final class ModelResourceBundleKeys {

    private ModelResourceBundleKeys() {
    }

    public static final String BUNDLE_BASE_NAME = "i18n.model";

    public static final String ACCOUNT_STATUS_NEW                    = "account.status.new";
    public static final String ACCOUNT_STATUS_ACTIVE                 = "account.status.active";
    public static final String ACCOUNT_STATUS_REMOVED                = "account.status.removed";
    public static final String ACCOUNT_STATUS_REMOVED_COMPLETELY     = "account.status.removed.completely";

    public static final String OPERATION_TYPE_STATUS_CHANGED         = "operation.type.status.changed";
    public static final String OPERATION_TYPE_USER_NAME_CHANGED      = "operation.type.user.name.changed";
    public static final String OPERATION_TYPE_EMAIL_CHANGED          = "operation.type.email.changed";
    public static final String OPERATION_TYPE_PASSWORD_CHANGED       = "operation.type.password.changed";

    public static final String OPERATION_STATUS_VERIFICATION_PENDING = "operation.status.verification.pending";
    public static final String OPERATION_STATUS_PAYMENT_PENDING      = "operation.status.payment.pending";
    public static final String OPERATION_STATUS_COMPLETED            = "operation.status.completed";
    public static final String OPERATION_STATUS_CANCELLED            = "operation.status.cancelled";

    public static final String SEX_MALE                              = "sex.male";
    public static final String SEX_FEMALE                            = "sex.female";

    public static final String ROUNDING_DOUN                         = "rounding.down";
    public static final String ROUNDING_UP                           = "rounding.up";

    public static final String CARD_OPERATION_ADDITION               = "card.operation.addition";
    public static final String CARD_OPERATION_SUBTRACTION            = "card.operation.subtraction";
    public static final String CARD_OPERATION_MULTIPLICATION         = "card.operation.multiplication";
    public static final String CARD_OPERATION_DIVISION               = "card.operation.division";

    public static final String GAME_STATUS_OPEN                      = "game.status.open";
    public static final String GAME_STATUS_RUNNING                   = "game.status.running";
    public static final String GAME_STATUS_FINISHED                  = "game.status.finished";
    public static final String GAME_STATUS_CANCELLED                 = "game.status.cancelled";
    public static final String GAME_STATUS_INTERRUPTED                = "game.status.interrupted";

    public static final String INVITATION_STATUS_CREATED             = "invitation.status.created";
    public static final String INVITATION_STATUS_ACCEPTED            = "invitation.status.accepted";
    public static final String INVITATION_STATUS_REJECTED            = "invitation.status.rejected";
    public static final String INVITATION_STATUS_EXPIRED             = "invitation.status.expired";
    public static final String INVITATION_STATUS_CANCELLED           = "invitation.status.cancelled";

    public static final String FRIEND_STATUS_CREATED                 = "friend.status.created";
    public static final String FRIEND_STATUS_CONFIRMED               = "friend.status.confirmed";
    public static final String FRIEND_STATUS_REJECTED                = "friend.status.rejected";
    public static final String FRIEND_STATUS_CANCELLED               = "friend.status.cancelled";

    public static final String CARD_GROUP_BIG                        = "card.group.big";
    public static final String CARD_GROUP_SMALL                      = "card.group.small";

    public static final String EVENT_GAME_CREATED = "event.game.created";
    public static final String EVENT_GAME_STARTED = "event.game.started";
    public static final String EVENT_GAME_FINISHED = "event.game.finished";
}
