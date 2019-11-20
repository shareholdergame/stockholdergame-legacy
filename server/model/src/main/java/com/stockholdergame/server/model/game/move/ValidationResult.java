package com.stockholdergame.server.model.game.move;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

/**
 * @author Alexander Savin
 *         Date: 21.11.2010 15.11.32
 */
public class ValidationResult {

    public static final ValidationResult SUCCESS_RESULT = new ValidationResult(true, null, null);

    private boolean success;

    private ValidationError error;

    private String[] parameters;

    public ValidationResult(boolean success, ValidationError error, String... parameters) {
        this.success = success;
        this.error = error;
        this.parameters = parameters;
    }

    public boolean isSuccess() {
        return success;
    }

    public ValidationError getError() {
        return error;
    }

    public String[] getParameters() {
        return parameters;
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof ValidationResult)) {
            return false;
        }
        ValidationResult g = (ValidationResult) o;
        return new EqualsBuilder()
                .append(success, g.success)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder()
                .append(success)
                .toHashCode();
    }
}
