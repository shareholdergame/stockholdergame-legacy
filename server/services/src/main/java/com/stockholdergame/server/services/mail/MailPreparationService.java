package com.stockholdergame.server.services.mail;

import com.stockholdergame.server.model.account.AccountOperation;
import com.stockholdergame.server.model.game.variant.Rounding;

import java.util.List;
import java.util.Locale;

/**
 * @author Alexander Savin
 *         Date: 17.8.2010 8.44.14
 */
public interface MailPreparationService {

    void prepareVerificationMessageForAccountOperation(String userName, String userEmail, Locale language,
                                                       AccountOperation accountOperation);

    void prepareVerificationMessageForEmailConfirmation(String userName, String userEmail, Locale language,
                                                        AccountOperation accountOperation);

    /**
     * Prepares a password restoration e-mail message to specified user.
     * @param userName user name.
     * @param userEmail user e-mail.
     * @param password password.
     * @param language language.
     */
    void prepareResetPasswordMessage(String userName, String userEmail, String password, Locale language);

    /**
     * Prepares message, that sends after account removing confirmation.
     * @param userName user name.
     * @param email user e-mail.
     */
    void prepareRemovingConfirmedMessage(String userName, String email);

    /**
     * Prepares message, that sends after account restoring confirmation.
     * @param userName user name.
     * @param email user e-mail.
     */
    void prepareRestoringConfirmedMessage(String userName, String email);

    void prepareInvitationMessage(String userName, String email, String name,
                                  Integer movesQuantity, Integer competitorsQuantity, boolean switchMoveOrder, Rounding rounding,
                                  List<String> invitedUsers, Locale locale);

    void prepareMoveDoneMessage(String userName, String email, String competitorName, int movesQuantity, List<String> playerNames, Locale locale);

    void prepareUserJoinedMessage(String userName, String email, String joinedUser, Locale locale);

    void prepareGameStartedMessage(String userName, String email, String joinedUser, Locale locale, boolean isRecipientMove);

    void prepareInvitationRejectedMessage(String userName, String email, String inviteeName, Locale locale);

    void prepareInvitationCancelledMessage(String userName, String email, String inviterName, Locale locale);

    void prepareGameCancelledMessage(String userName, String email, String initiatorName, int movesQuantity, Rounding rounding, List<String> playerNames, Locale locale);

    void prepareMailToSupport(Throwable throwable);

    void prepareGameInterruptedMessage(String userName, String email, Locale locale, String gameVariantName, Integer movesQuantity, boolean myMove,
                                       String currentCompetitorName, int timeout);

    void prepareUnreadChatMessagesNotification(String email, String userName, Locale locale, String secondUserName, Long messagesCount);
}
