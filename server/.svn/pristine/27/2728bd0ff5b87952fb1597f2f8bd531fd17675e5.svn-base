package com.stockholdergame.server.services.localization.impl;

import com.stockholdergame.server.exceptions.NonUniqueMessageKeyException;
import com.stockholdergame.server.localization.LocaleRegistry;
import com.stockholdergame.server.localization.MessageHolder;
import com.stockholdergame.server.localization.ResourceBundleRegistry;
import com.stockholdergame.server.localization.UTF8Control;
import com.stockholdergame.server.services.localization.LocalizationService;
import java.util.Locale;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.Set;
import javax.annotation.PostConstruct;
import org.springframework.stereotype.Service;

/**
 * @author Alexander Savin
 *         Date: 13.9.2010 8.21.35
 */
@Service
public class LocalizationServiceImpl implements LocalizationService {

    private boolean initialized;

    @PostConstruct
    public void initializeMessageHolder() {
        if (initialized) {
            return;
        }

        Locale[] locales = LocaleRegistry.getLocales();
        for (Locale locale : locales) {
            loadBundlesForLocale(locale);
        }
        initialized = true;
    }

    private void loadBundlesForLocale(Locale locale) {
        for (Map.Entry<String, Class> entry : ResourceBundleRegistry.getBundles()) {
            ResourceBundle rb = locale.equals(LocaleRegistry.RU) ?
                    ResourceBundle.getBundle(entry.getKey(), locale, new UTF8Control()) : ResourceBundle.getBundle(entry.getKey(), locale);
            loadMessagesFromBundle(locale, rb);
        }
    }

    private void loadMessagesFromBundle(Locale locale, ResourceBundle rb) {
        Set<String> messageKeySet = rb.keySet();
        for (String messageKey : messageKeySet) {
            try {
                MessageHolder.getInstance().addMessage(locale, messageKey, rb.getString(messageKey));
            } catch (NonUniqueMessageKeyException e) {
                e.printStackTrace();
            }
        }
    }
}
