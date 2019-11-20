package com.stockholdergame.server.dto.validation;

import com.stockholdergame.server.dto.validation.constraints.EnumName;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 * @author Alexander Savin
 *         Date: 30.12.11 20.58
 */
public class EnumValidator implements ConstraintValidator<EnumName, String> {

    private static Map<Class, List<String>> enumConstantsCacheMap = new HashMap<Class, List<String>>();

    private Class enumClass;

    public void initialize(EnumName constraintAnnotation) {
        this.enumClass = constraintAnnotation.enumClass();
        validateParameters();

        if (!enumConstantsCacheMap.containsKey(this.enumClass)) {
            List<String> names = new ArrayList<String>();
            Object[] constants = enumClass.getEnumConstants();
            for (Object constant : constants) {
                names.add(((Enum) constant).name());
            }
            enumConstantsCacheMap.put(this.enumClass, names);
        }
    }

    public boolean isValid(String value, ConstraintValidatorContext context) {
        return value == null || enumConstantsCacheMap.get(enumClass).contains(value);
    }

    private void validateParameters() {
        if (!enumClass.isEnum()) {
            throw new IllegalArgumentException("The enumClass parameter is not enum.");
        }
    }
}
