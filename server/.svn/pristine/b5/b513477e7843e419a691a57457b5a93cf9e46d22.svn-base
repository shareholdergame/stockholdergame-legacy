package com.stockholdergame.server.dto.account;

import javax.validation.constraints.NotNull;
import org.hibernate.validator.constraints.Email;

/**
 * @author Alexander Savin
 *         Date: 20.11.11 22.28
 */
public class ChangeEmailDto {

    @NotNull
    @Email
    private String newEmail;

    public String getNewEmail() {
        return newEmail;
    }

    public void setNewEmail(String newEmail) {
        this.newEmail = newEmail;
    }
}
