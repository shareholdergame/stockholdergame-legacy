package com.stockholdergame.server.dto.validation;

import com.stockholdergame.server.dto.validation.constraints.NotBlank;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import org.apache.commons.lang.StringUtils;

/**
 * @author Alexander Savin
 *         Date: 30.12.11 20.11
 */
public class NotBlankValidator implements ConstraintValidator<NotBlank, String> {

    public void initialize(NotBlank constraintAnnotation) {
    }

    public boolean isValid(String value, ConstraintValidatorContext context) {
        return !StringUtils.isBlank(value);
    }
}
