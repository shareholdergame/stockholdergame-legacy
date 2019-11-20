package com.stockholdergame.server.localization;

import static java.text.MessageFormat.format;
import com.stockholdergame.server.exceptions.NonUniqueMessageKeyException;
import com.stockholdergame.server.exceptions.UnsupportedLanguageException;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

/**
 * @author Alexander Savin
 *         Date: 12.9.2010 22.29.43
 */
public class MessageHolder {

    private static MessageHolder instance = new MessageHolder();

    public static MessageHolder getInstance() {
        return instance;
    }

    private MessageHolder() {
    }

    private Map<Locale, Map<String, String>> messageMap = new HashMap<>();

    public void addMessage(Locale locale, String messageKey, String message) throws NonUniqueMessageKeyException {
        if (!messageMap.containsKey(locale)) {
            messageMap.put(locale, new HashMap<String, String>());
        }
        if (messageMap.get(locale).containsKey(messageKey)) {
            throw new NonUniqueMessageKeyException(messageKey);
        }
        messageMap.get(locale).put(messageKey, message);
    }

    public static String getMessage(String messageKey) {
        return getMessage(LocaleRegistry.getDefaultLocale(), messageKey);
    }

    public static String getMessage(String messageKey, Object... arguments) {
        return getMessage(LocaleRegistry.getDefaultLocale(), messageKey, arguments);
    }

    public static String getMessage(Locale locale, String messageKey) {
        if (locale == null) {
            locale = LocaleRegistry.getDefaultLocale();
        }
        if (getMessageMap().containsKey(locale)) {
            return getMessageMap().get(locale).get(messageKey);
        } else {
            throw new UnsupportedLanguageException(locale.getLanguage());
        }
    }

    public static String getMessage(Locale locale, String messageKey, Object... arguments) {
        if (locale == null) {
            locale = LocaleRegistry.getDefaultLocale();
        }
        if (getMessageMap().containsKey(locale)) {
            String message = getMessageMap().get(locale).get(messageKey);
            return format(message, arguments);
        } else {
            throw new UnsupportedLanguageException(locale.getLanguage());
        }
    }

    private static Map<Locale, Map<String, String>> getMessageMap() {
        return getInstance().messageMap;
    }
}
