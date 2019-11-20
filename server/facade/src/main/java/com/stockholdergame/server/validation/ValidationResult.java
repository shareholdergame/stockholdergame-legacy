package com.stockholdergame.server.validation;

/**
 * @author Alexander Savin
 *         Date: 28.12.11 20.53
 */
public class ValidationResult {

    public static final ValidationResult SUCCESS_RESULT = new ValidationResult(true, null, null);

    private boolean isValid;

    private String property;

    private String message;

    public ValidationResult(boolean valid, String property, String message) {
        isValid = valid;
        this.property = property;
        this.message = message;
    }

    public boolean isValid() {
        return isValid;
    }

    public String getProperty() {
        return property;
    }

    public String getMessage() {
        return message;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        if (property != null) {
            sb.append(property);
            sb.append(":");
        }
        if (message != null) {
            sb.append(message);
        }
        return sb.toString();
    }
}
