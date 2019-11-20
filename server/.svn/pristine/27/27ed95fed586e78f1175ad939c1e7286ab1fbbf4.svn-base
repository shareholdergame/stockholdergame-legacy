package com.stockholdergame.server.validation;

import static java.text.MessageFormat.format;

import java.lang.annotation.Annotation;
import java.lang.reflect.Array;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;
import javax.validation.ConstraintViolation;
import javax.validation.Validator;
import javax.validation.constraints.NotNull;
import javax.validation.groups.Default;

/**
 * @author Alexander Savin
 *         Date: 28.12.11 20.48
 */
public class FacadeValidator {

    private static final Class[] wrappers = new Class[]{
            Byte.class,
            Character.class,
            Short.class,
            Integer.class,
            Long.class,
            Boolean.class,
            String.class,
            Float.class,
            Double.class
    };

    private Validator validator;

    public FacadeValidator(Validator validator) {
        this.validator = validator;
    }

    @SuppressWarnings("unchecked")
    public ValidationResult validate(Method method, Object... args) {
        Collection<Argument> validatableArgs = getValidatableArgs(method, args);

        for (Argument argument : validatableArgs) {
            Object arg = argument.getArg();
            Annotation[] annotations = argument.getAnnotations();
            if (arg == null) {
                if (isAnnotatedWithNonNull(annotations)) {
                    return new ValidationResult(false, null, format("Argument #{0} can't be null", argument.getIndex()));
                } else {
                    return ValidationResult.SUCCESS_RESULT;
                }
            }

            if (isPrimitiveOrWrapper(arg.getClass())) {
                return ValidationResult.SUCCESS_RESULT;
            }

            List<Object> validatedObjects = new ArrayList<Object>();
            if (isArray(arg)) {
                Object[] array = (Object[]) arg;
                validatedObjects.addAll(Arrays.asList(array));
            } else if (isCollection(arg)) {
                Collection<Object> collection = (Collection<Object>) arg;
                validatedObjects.addAll(collection);
            } else if (isMap(arg)) {
                Collection<Object> values = ((Map) arg).values();
                validatedObjects.addAll(values);
            } else {
                validatedObjects.add(arg);
            }

            for (Object validatedObject : validatedObjects) {
                Set<ConstraintViolation<Object>> violations = validator.validate(validatedObject, Default.class);
                if (!violations.isEmpty()) {
                    ConstraintViolation<Object> violation = violations.iterator().next();
                    return new ValidationResult(false, violation.getPropertyPath().toString(), violation.getMessage());
                }
            }
        }

        return ValidationResult.SUCCESS_RESULT;
    }

    private boolean isAnnotatedWithNonNull(Annotation[] annotations) {
        for (Annotation annotation : annotations) {
            if (annotation.annotationType().equals(NotNull.class)) {
                return true;
            }
        }
        return false;
    }

    private Collection<Argument> getValidatableArgs(Method method, Object... args) {
        Collection<Argument> map = new ArrayList<Argument>();
        Annotation[][] annotationParameters = method.getParameterAnnotations();
        for (int i = 0; i < annotationParameters.length; i++) {
            Annotation[] annotations = annotationParameters[i];
            for (Annotation annotation : annotations) {
                if (annotation.annotationType().equals(Validatable.class)) {
                    Object arg = args[i];
                    map.add(new Argument(i, arg, annotations));
                }
            }
        }
        return map;
    }

    private boolean isArray(Object arg) {
        return arg instanceof Array;
    }

    private boolean isCollection(Object arg) {
        return arg instanceof Collection;
    }

    private boolean isMap(Object arg) {
        return arg instanceof Map;
    }

    private boolean isPrimitiveOrWrapper(Class argClass) {
        boolean result;
        result = argClass.isPrimitive();
        if (!result) {
            for (Class type : wrappers) {
                if (argClass.equals(type)) {
                    result = true;
                    break;
                }
            }
        }
        return result;
    }

    private static class Argument {

        private int index;

        private Object arg;

        private Annotation[] annotations;

        private Argument(int index, Object arg, Annotation[] annotations) {
            this.index = index;
            this.arg = arg;
            this.annotations = annotations;
        }

        public int getIndex() {
            return index;
        }

        public Object getArg() {
            return arg;
        }

        public Annotation[] getAnnotations() {
            return annotations;
        }
    }
}
