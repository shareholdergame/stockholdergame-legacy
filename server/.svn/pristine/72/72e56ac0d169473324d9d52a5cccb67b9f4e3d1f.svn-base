package com.stockholdergame.server.dto;

import com.stockholdergame.server.dto.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

/**
 * @author Alexander Savin
 *         Date: 20.2.2010 17.10.13
 */
public class PasswordChangingDto {

    @NotBlank
    private String oldPassword;

    @NotBlank
    @Size(min = 6, max = 64)
    private String newPassword;

    public String getOldPassword() {
        return oldPassword;
    }

    public void setOldPassword(String oldPassword) {
        this.oldPassword = oldPassword;
    }

    public String getNewPassword() {
        return newPassword;
    }

    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
    }
}
