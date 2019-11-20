package com.stockholdergame.server.services.mail.impl;

import com.stockholdergame.server.exceptions.ApplicationException;
import com.stockholdergame.server.model.mail.Message;
import com.stockholdergame.server.services.mail.MailBoxService;
import com.stockholdergame.server.services.mail.MailService;
import org.apache.commons.lang.math.NumberUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailException;
import org.springframework.mail.MailSendException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.stereotype.Service;

import javax.mail.Address;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import static com.stockholdergame.server.i18n.InternalResourceBundleKeys.MAIL_MESSAGES_COUNT;
import static com.stockholdergame.server.i18n.InternalResourceBundleKeys.MAIL_MESSAGE_REMOVED;
import static com.stockholdergame.server.i18n.InternalResourceBundleKeys.MAIL_MESSAGE_SEND_FAILED;
import static com.stockholdergame.server.i18n.ServiceResourceBundleKeys.SERVER_DOMAIN_NAME;
import static com.stockholdergame.server.localization.MessageHolder.getMessage;

/**
 * {@link com.stockholdergame.server.services.mail.MailService} implementation.
 *
 * @author Alexander Savin
 *         Date: 21.3.2010 10.47.12
 */
@Service("mailService")
public class MailServiceImpl implements MailService {

    public static final String ENCODING_OPTIONS = "text/plain; charset=UTF-8";
    public static final String UTF_8 = "UTF-8";

    private static Logger LOGGER = LogManager.getLogger(MailServiceImpl.class);

    public static final String INTERNAL_MESSAGE_ID = "Internal-Message-ID";

    public static final String MAIL_SYSTEM_FAILED_SBJ = "Mail system failed";

    @Autowired
    private Properties mailConfiguration;

    @Autowired
    private MailBoxService mailBoxService;

    @Autowired
    private JavaMailSender mailSender;

    @Autowired
    private String adminEmail;

    public void setMailConfiguration(Properties mailConfiguration) {
        this.mailConfiguration = mailConfiguration;
    }

    /**
     * {@inheritDoc}
     */
    public void send() {
        send(mailBoxService.findOutgoingMessages());
    }

    public void sendFailedMessages() {
        send(mailBoxService.findFailedOutgoingMessages());
    }

    private void send(List<Message> messages) {
        if (messages.isEmpty()) {
            return;
        } else {
            LOGGER.info(getMessage(MAIL_MESSAGES_COUNT, messages.size()));
        }

        List<Long> failedMessageIds = new ArrayList<>(messages.size());
        List<MimeMessagePreparator> preparators = createPreparators(messages);
        try {
            mailSender.send(preparators.toArray(new MimeMessagePreparator[preparators.size()]));
        } catch (MailSendException e) {
            Map<Object, Exception> failedMessages = e.getFailedMessages();
            for (Map.Entry<Object, Exception> entry : failedMessages.entrySet()) {
                MimeMessage mimeMessage = (MimeMessage) entry.getKey();
                Long internalId = getInternalMessageId(mimeMessage);
                if (internalId != null) {
                    failedMessageIds.add(internalId);
                }
                Exception ex = entry.getValue();
                LOGGER.error(ex.getMessage(), ex);
            }
        } catch (MailException e) {
            throw new ApplicationException(e);
        }

        for (Message message : messages) {
            try {
                Long messageId = message.getId();
                if (failedMessageIds.contains(messageId)) {
                    if (message.getAttemptsCount() < 16) {
                        mailBoxService.markAsFailed(messageId);
                        LOGGER.warn(getMessage(MAIL_MESSAGE_SEND_FAILED, message.toString(),
                                message.getAttemptsCount() + 1));
                    } else {
                        mailBoxService.removeMessage(messageId);
                        LOGGER.warn(getMessage(MAIL_MESSAGE_REMOVED, message.toString()));
                    }
                } else {
                    mailBoxService.moveToSentBox(messageId);
                }
            } catch (Exception e) {
                LOGGER.error(e.getMessage(), e);
                mailSender.send(getMimeMessagePreparator(MAIL_SYSTEM_FAILED_SBJ, stackTraceToString(e),
                        adminEmail, null));
            }
        }
    }

    private List<MimeMessagePreparator> createPreparators(List<Message> messages) {
        List<MimeMessagePreparator> preparators = new ArrayList<>(messages.size());
        for (Message message : messages) {
            preparators.add(getMimeMessagePreparator(message.getSubject(), message.getBody(),
                    message.getRecipient(), message.getId()));
        }
        return preparators;
    }

    private Long getInternalMessageId(MimeMessage mimeMessage) {
        Long id = null;
        try {
            String[] header = mimeMessage.getHeader(INTERNAL_MESSAGE_ID);
            id = header.length > 0 ? NumberUtils.createLong(header[0]) : null;
        } catch (MessagingException e) {
            LOGGER.error(e.getMessage(), e);
        }
        return id;
    }

    private String stackTraceToString(Exception ex) {
        StringWriter str = new StringWriter();
        PrintWriter writer = new PrintWriter(str);
        ex.printStackTrace(writer);
        return str.getBuffer().toString();
    }

    private MimeMessagePreparator getMimeMessagePreparator(final String subject,
                                                           final String body,
                                                           final String recipient,
                                                           final Long internalId) {
        return new MimeMessagePreparator() {
            public void prepare(MimeMessage mimeMessage) throws Exception {
                mimeMessage.setSubject(subject, UTF_8);
                Multipart mp = new MimeMultipart();
                MimeBodyPart textPart = new MimeBodyPart();
                textPart.setContent(body, ENCODING_OPTIONS);
                mp.addBodyPart(textPart);
                mimeMessage.setContent(mp);
                mimeMessage.saveChanges();
                textPart.setHeader("Content-Transfer-Encoding", "base64");
                Address fromAddress = createFromAddress();
                Address toAddress = new InternetAddress(recipient);
                mimeMessage.setFrom(fromAddress);
                mimeMessage.addRecipient(javax.mail.Message.RecipientType.TO, toAddress);
                if (internalId != null) {
                    mimeMessage.addHeader(INTERNAL_MESSAGE_ID, internalId.toString());
                }
            }
        };
    }

    private String getSenderEmail() {
        return (String) mailConfiguration.get("mail.smtp.user");
    }

    private Address createFromAddress() throws UnsupportedEncodingException {
        String serverDomainName = getMessage(SERVER_DOMAIN_NAME);
        return new InternetAddress(getSenderEmail(), serverDomainName);
    }
}
