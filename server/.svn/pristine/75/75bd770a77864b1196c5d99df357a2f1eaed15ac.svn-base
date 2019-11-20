package com.stockholdergame.server.dto;

import com.stockholdergame.server.dto.validation.constraints.NotBlank;
import org.hibernate.validator.constraints.Email;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * @author Alexander Savin
 *         Date: 20.2.2010 16.59.01
 */
public class RegistrationDto {

    @NotBlank
    @Size(min = 3, max = 24)
    private String userName;

    @NotNull
    @Email
    private String email;

    @NotBlank
    @Size(min = 6, max = 12)
    private String password;

    @NotBlank
    private String language;

    // CAPTCHA is switched off
    //@NotBlank
    private String captchaAnswer;

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public String getCaptchaAnswer() {
        return captchaAnswer;
    }

    public void setCaptchaAnswer(String captchaAnswer) {
        this.captchaAnswer = captchaAnswer;
    }
}
