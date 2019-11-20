package com.stockholdergame.server.services.mail.impl;

import com.stockholdergame.server.exceptions.ApplicationException;
import com.stockholdergame.server.localization.LocaleRegistry;
import com.stockholdergame.server.localization.MessageHolder;
import com.stockholdergame.server.model.account.AccountOperation;
import com.stockholdergame.server.model.account.AccountStatus;
import com.stockholdergame.server.model.game.variant.Rounding;
import com.stockholdergame.server.services.mail.MailBoxService;
import com.stockholdergame.server.services.mail.MailPreparationService;
import com.stockholdergame.server.util.collections.MapBuilder;
import com.stockholdergame.server.util.collections.Pair;
import freemarker.cache.StrongCacheStorage;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import org.apache.commons.lang.math.NumberUtils;
import org.apache.commons.lang.time.DateFormatUtils;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.integration.annotation.MessageEndpoint;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.text.MessageFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import static com.stockholdergame.server.i18n.ServiceResourceBundleKeys.MAIL_APPLICATION_EXCEPTION;
import static com.stockholdergame.server.i18n.ServiceResourceBundleKeys.MAIL_GAME_CANCELLED;
import static com.stockholdergame.server.i18n.ServiceResourceBundleKeys.MAIL_GAME_INTERRUPTED;
import static com.stockholdergame.server.i18n.ServiceResourceBundleKeys.MAIL_GAME_STARTED;
import static com.stockholdergame.server.i18n.ServiceResourceBundleKeys.MAIL_INVITATION_CANCELLED;
import static com.stockholdergame.server.i18n.ServiceResourceBundleKeys.MAIL_INVITATION_REJECTED;
import static com.stockholdergame.server.i18n.ServiceResourceBundleKeys.MAIL_MOVE_DONE;
import static com.stockholdergame.server.i18n.ServiceResourceBundleKeys.MAIL_NEW_EMAIL_CONFIRMATION;
import static com.stockholdergame.server.i18n.ServiceResourceBundleKeys.MAIL_NEW_INVITATION;
import static com.stockholdergame.server.i18n.ServiceResourceBundleKeys.MAIL_PASSWORD_RESET;
import static com.stockholdergame.server.i18n.ServiceResourceBundleKeys.MAIL_REGISTRATION_CONFIRMATION;
import static com.stockholdergame.server.i18n.ServiceResourceBundleKeys.MAIL_REMOVE_ACCOUNT_CONFIRMATION;
import static com.stockholdergame.server.i18n.ServiceResourceBundleKeys.MAIL_RESTORE_ACCOUNT_CONFIRMATION;
import static com.stockholdergame.server.i18n.ServiceResourceBundleKeys.MAIL_UNREAD_CHAT_MESSAGES;
import static com.stockholdergame.server.i18n.ServiceResourceBundleKeys.MAIL_USER_JOINED;
import static org.apache.commons.lang.time.DateFormatUtils.ISO_DATE_FORMAT;

/**
 * @author Alexander Savin
 *         Date: 17.8.2010 8.49.46
 */
@Service("mailPreparationService")
@MessageEndpoint
public class MailPreparationServiceImpl implements MailPreparationService {

    private static final String USER_NAME = "userName";
    private static final String VERIFICATION_CODE = "verificationCode";
    private static final String EXPIRATION_DATE = "expirationDate";
    private static final String PASSWORD = "password";

    private static Map<Pair<AccountStatus, AccountStatus>, Pair<String, String>> accountLifecycleTemplatesMap;

    @Autowired
    private MailBoxService mailBoxService;

    @Autowired
    private String adminEmail;

    private Configuration configuration;

    private String hostName;

    public MailPreparationServiceImpl() {
        accountLifecycleTemplatesMap = new MapBuilder<Pair<AccountStatus, AccountStatus>, Pair<String, String>>()
                .append(new Pair<>(AccountStatus.NEW, AccountStatus.ACTIVE),
                        new Pair<>("reg-confirm.ftl", MAIL_REGISTRATION_CONFIRMATION))
                .append(new Pair<>(AccountStatus.ACTIVE, AccountStatus.REMOVED),
                        new Pair<>("remove-account.ftl", MAIL_REMOVE_ACCOUNT_CONFIRMATION))
                .append(new Pair<>(AccountStatus.REMOVED, AccountStatus.ACTIVE),
                        new Pair<>("restore-account.ftl", MAIL_RESTORE_ACCOUNT_CONFIRMATION))
                .toHashMap();
    }

    @PostConstruct
    public void initialize() {
        configuration = new Configuration(Configuration.VERSION_2_3_23);
        configuration.setClassForTemplateLoading(this.getClass(), "/templates/");
        configuration.setDefaultEncoding("UTF-8");
        configuration.setCacheStorage(new StrongCacheStorage());

        try {
            hostName = InetAddress.getLocalHost().getHostName();
        } catch (UnknownHostException e) {
            hostName = "Unknown host";
        }
    }

    public void prepareVerificationMessageForAccountOperation(String userName, String userEmail, Locale locale,
                                                              AccountOperation accountOperation) {
        Map<String, Object> data = new MapBuilder<String, Object>()
                .append(USER_NAME, userName)
                .append(VERIFICATION_CODE, accountOperation.getVerificationCode())
                .append(EXPIRATION_DATE,
                        DateFormatUtils.format(accountOperation.getExpirationDate(), ISO_DATE_FORMAT.getPattern()))
                .toHashMap();
        Pair<String, String> templateAndSubject = getTemplateAndSubject(
                AccountStatus.values()[NumberUtils.toInt(accountOperation.getOldValue())],
                AccountStatus.values()[NumberUtils.toInt(accountOperation.getNewValue())]);
        prepareMessage(userEmail, locale, data, templateAndSubject.getFirst(), templateAndSubject.getSecond());
    }

    public void prepareVerificationMessageForEmailConfirmation(String userName, String userEmail, Locale locale,
                                                               AccountOperation accountOperation) {
        Map<String, Object> data = new MapBuilder<String, Object>()
                .append(USER_NAME, userName)
                .append(VERIFICATION_CODE, accountOperation.getVerificationCode())
                .append(EXPIRATION_DATE,
                        DateFormatUtils.format(accountOperation.getExpirationDate(), ISO_DATE_FORMAT.getPattern()))
                .toHashMap();
        prepareMessage(userEmail, locale, data, "email-confirm.ftl", MAIL_NEW_EMAIL_CONFIRMATION);
    }

    /**
     * {@inheritDoc}
     */
    public void prepareResetPasswordMessage(String userName, String userEmail, String password, Locale locale) {
        Map<String, Object> data = new MapBuilder<String, Object>()
                .append(USER_NAME, userName)
                .append(PASSWORD, password)
                .toHashMap();
        prepareMessage(userEmail, locale, data, "reset-password.ftl", MAIL_PASSWORD_RESET);
    }

    /**
     * {@inheritDoc}
     */
    public void prepareRemovingConfirmedMessage(String userName, String email) {
        // todo - do we really need this?
    }

    public void prepareRestoringConfirmedMessage(String userName, String email) {
        // todo - do we really need this?
    }

    @Override
    public void prepareInvitationMessage(String userName, String email, String inviterName,
                                         Integer movesQuantity, Integer competitorsQuantity, boolean switchMoveOrder, Rounding rounding,
                                         List<String> invitedUsers, Locale locale) {
        Map<String, Object> data = new MapBuilder<String, Object>()
                .append(USER_NAME, userName)
                .append("inviterName", inviterName)
                .append("movesQuantity", movesQuantity)
                .append("competitorsQuantity", competitorsQuantity)
                .append("switchMoveOrder", switchMoveOrder)
                .append("rounding", MessageHolder.getMessage(locale, rounding.getDescription()))
                .append("invitedUsers", invitedUsers)
                .toHashMap();
        prepareMessage(email, locale, data, "invitation.ftl", MAIL_NEW_INVITATION);
    }

    @Override
    public void prepareMoveDoneMessage(String userName, String email, String competitorName, int movesQuantity, List<String> playerNames,
                                       Locale locale) {
        Map<String, Object> data = new MapBuilder<String, Object>()
                .append(USER_NAME, userName)
                .append("competitorName", competitorName)
                .append("movesQuantity", movesQuantity)
                .append("competitors", playerNames)
                .toHashMap();
        prepareMessage(email, locale, data, "move-done.ftl", MAIL_MOVE_DONE);
    }

    @Override
    public void prepareUserJoinedMessage(String userName, String email, String joinedUser, Locale locale) {
        Map<String, Object> data = new MapBuilder<String, Object>()
                .append(USER_NAME, userName)
                .append("joinedUserName", joinedUser)
                .toHashMap();
        prepareMessage(email, locale, data, "user-joined.ftl", MAIL_USER_JOINED);
    }

    @Override
    public void prepareGameStartedMessage(String userName, String email, String joinedUser, Locale locale, boolean isRecipientMove) {
        Map<String, Object> data = new MapBuilder<String, Object>()
            .append(USER_NAME, userName)
            .append("joinedUserName", joinedUser)
            .append("isRecipientMove", isRecipientMove)
            .toHashMap();
        prepareMessage(email, locale, data, "game-started.ftl", MAIL_GAME_STARTED);
    }

    @Override
    public void prepareInvitationRejectedMessage(String userName, String email, String inviteeName, Locale locale) {
        Map<String, Object> data = new MapBuilder<String, Object>()
                .append(USER_NAME, userName)
                .append("inviteeName", inviteeName)
                .toHashMap();
        prepareMessage(email, locale, data, "invitation-rejected.ftl", MAIL_INVITATION_REJECTED);
    }

    @Override
    public void prepareInvitationCancelledMessage(String userName, String email, String inviterName, Locale locale) {
        Map<String, Object> data = new MapBuilder<String, Object>()
                .append(USER_NAME, userName)
                .append("inviterName", inviterName)
                .toHashMap();
        prepareMessage(email, locale, data, "invitation-cancelled.ftl", MAIL_INVITATION_CANCELLED);
    }

    @Override
    public void prepareGameCancelledMessage(String userName, String email, String initiatorName, int movesQuantity,
                                            Rounding rounding, List<String> playerNames, Locale locale) {
        Map<String, Object> data = new MapBuilder<String, Object>()
            .append(USER_NAME, userName)
            .append("initiatorName", initiatorName)
            .append("movesQuantity", movesQuantity)
            .append("rounding", MessageHolder.getMessage(locale, rounding.getDescription()))
            .append("competitors", playerNames)
            .toHashMap();
        prepareMessage(email, locale, data, "game-cancelled.ftl", MAIL_GAME_CANCELLED);
    }

    @Override
    public void prepareMailToSupport(Throwable throwable) {
        Map<String, Object> data = new MapBuilder<String, Object>()
            .append("date", DateFormatUtils.format(new Date(), DateFormatUtils.ISO_DATETIME_FORMAT.getPattern()))
            .append("hostName", hostName)
            .append("message", throwable.getMessage() != null ? throwable.getMessage() : "")
            .append("stackTrace", stackTraceToString(throwable))
            .toHashMap();
        prepareMessage(adminEmail, LocaleRegistry.getDefaultLocale(), data, "application-exception.ftl",
            MAIL_APPLICATION_EXCEPTION);
    }

    @Override
    public void prepareGameInterruptedMessage(String userName, String email, Locale locale, String gameVariantName, Integer movesQuantity,
                                              boolean myMove, String currentCompetitorName, int timeout) {
        Map<String, Object> data = new MapBuilder<String, Object>()
                .append(USER_NAME, userName)
                .append("movesQuantity", movesQuantity)
                .append("currentCompetitorName", currentCompetitorName)
                .append("timeout", timeout)
                .toHashMap();
        prepareMessage(email, locale, data, myMove ? "game-interrupted-me.ftl" : "game-interrupted.ftl", MAIL_GAME_INTERRUPTED);
    }

    @Override
    public void prepareUnreadChatMessagesNotification(String email, String userName, Locale locale, String secondUserName, Long messagesCount) {
        Map<String, Object> data = new MapBuilder<String, Object>()
                .append(USER_NAME, userName)
                .append("secondUser", secondUserName)
                .append("messagesCount", messagesCount)
                .toHashMap();
        prepareMessage(email, locale, data, "unread-chat-messages.ftl", MAIL_UNREAD_CHAT_MESSAGES);
    }

    private String stackTraceToString(Throwable throwable) {
        return ExceptionUtils.getStackTrace(throwable);
    }

    String makeBody(Map<String, Object> data, String template) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        Writer writer = new OutputStreamWriter(baos);
        try {
            Template t = configuration.getTemplate(template);
            t.process(data, writer);
            writer.flush();
            return baos.toString();
        } catch (IOException | TemplateException e) {
            throw new ApplicationException(e);
        }
    }

    private Pair<String, String> getTemplateAndSubject(AccountStatus oldValue, AccountStatus newValue) {
        Pair<AccountStatus, AccountStatus> pair = new Pair<AccountStatus, AccountStatus>(oldValue, newValue);
        return accountLifecycleTemplatesMap.get(pair);
    }

    private void prepareMessage(String userEmail, Locale locale, Map<String, Object> data, String ftlFile,
                                String subjectKey) {
        String subject = MessageHolder.getMessage(locale, subjectKey);
        String body = makeBody(data, getTemplate(locale.getLanguage(), ftlFile));
        createMessage(userEmail, subject, body);
    }

    private String getTemplate(String lang, String fileName) {
        return MessageFormat.format("{0}/{1}", lang, fileName);
    }

    private void createMessage(String userEmail, String subject, String body) {
        mailBoxService.createMessage(userEmail, subject, body);
    }
}
