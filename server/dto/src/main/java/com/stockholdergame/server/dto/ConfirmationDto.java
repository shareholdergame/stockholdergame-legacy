package com.stockholdergame.server.dto;

import com.stockholdergame.server.dto.validation.constraints.NotBlank;

/**
 * @author Alexander Savin
 *         Date: 21.3.2010 11.06.11
 */
public class ConfirmationDto {

    @NotBlank
    private String verificationCode;

    public String getVerificationCode() {
        return verificationCode;
    }

    public void setVerificationCode(String verificationCode) {
        this.verificationCode = verificationCode;
    }
}
