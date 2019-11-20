package com.stockholdergame.server.dto.converter;

import java.util.Locale;
import org.dozer.CustomConverter;
import org.dozer.MappingException;

/**
 * @author Alexander Savin
 *         Date: 14.1.11 0.05
 */
public class LocaleCustomConverter implements CustomConverter {

    public Object convert(Object existingDestinationFieldValue,
                          Object sourceFieldValue,
                          Class<?> destinationClass,
                          Class<?> sourceClass) {
        if (sourceClass.equals(Locale.class) && sourceClass.isInstance(sourceFieldValue)) {
            return sourceFieldValue != null ? convertLocaleToString(sourceFieldValue, sourceClass) : null;
        } else if (destinationClass.equals(Locale.class)) {
            return null;
        } else {
            throw new MappingException("Converter LocaleCustomConverter used incorrectly. Arguments passed in were:"
                      + existingDestinationFieldValue + " and " + sourceFieldValue);
        }
    }

    private Object convertLocaleToString(Object sourceFieldValue, Class<?> sourceClass) {
        Locale locale = (Locale) sourceClass.cast(sourceFieldValue);
        return locale.toString();
    }
}
