package com.stockholdergame.server.localization;

import static java.util.Locale.US;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

/**
 * @author Alexander Savin
 *         Date: 13.9.2010 22.42.36
 */
public class LocaleRegistry {

    public static final Locale RU = new Locale("ru", "RU");

    private static LocaleRegistry instance = new LocaleRegistry();

    private LocaleRegistry() {
        register(US);
        register(RU);
    }

    public static LocaleRegistry getInstance() {
        return instance;
    }

    private Map<String, Locale> localeMap = new HashMap<>();

    public static Locale getLocale(String language) {
        for (String key : instance.localeMap.keySet()) {
            if (key.equalsIgnoreCase(language)) {
                return instance.localeMap.get(key);
            }
        }
        return getDefaultLocale();
    }

    public static Locale[] getLocales() {
        return getInstance().localeMap.values().toArray(new Locale[getInstance().localeMap.size()]);
    }

    public static Locale getDefaultLocale() {
        return US;
    }

    private void register(Locale locale) {
        localeMap.put(locale.toString(), locale);
    }

    public static boolean isLocaleValid(String language) {
        for (String key : instance.localeMap.keySet()) {
            if (key.equalsIgnoreCase(language)) {
                return true;
            }
        }
        return false;
    }
}
