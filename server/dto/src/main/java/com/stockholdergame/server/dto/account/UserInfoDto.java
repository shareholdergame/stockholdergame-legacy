package com.stockholdergame.server.dto.account;

import com.stockholdergame.server.dto.ProfileDto;

/**
 * @author Alexander Savin
 *         Date: 18.9.11 11.28
 */
public class UserInfoDto extends UserNameDto {

    private String locale;

    private boolean isBot;

    private ProfileDto profile;

    public String getLocale() {
        return locale;
    }

    public void setLocale(String locale) {
        this.locale = locale;
    }

    public boolean isBot() {
        return isBot;
    }

    public void setBot(boolean isBot) {
        this.isBot = isBot;
    }

    public ProfileDto getProfile() {
        return profile;
    }

    public void setProfile(ProfileDto profile) {
        this.profile = profile;
    }
}
