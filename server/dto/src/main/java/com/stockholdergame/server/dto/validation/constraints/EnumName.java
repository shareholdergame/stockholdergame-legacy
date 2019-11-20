package com.stockholdergame.server.dto.validation.constraints;

import com.stockholdergame.server.dto.validation.EnumValidator;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;
import javax.validation.Constraint;
import javax.validation.Payload;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * @author Alexander Savin
 *         Date: 30.12.11 20.57
 */
@Target({METHOD, FIELD, ANNOTATION_TYPE, CONSTRUCTOR, PARAMETER})
@Retention(RUNTIME)
@Documented
@Constraint(validatedBy = {EnumValidator.class})
public @interface EnumName {
    String message() default "{com.stockholdergame.server.dto.validation.constraints.EnumName.message}";

    Class enumClass();

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
