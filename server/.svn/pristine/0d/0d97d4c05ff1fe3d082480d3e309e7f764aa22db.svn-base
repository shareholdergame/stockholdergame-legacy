package com.stockholdergame.server.session;

import com.stockholdergame.server.model.account.AccountStatus;
import com.stockholdergame.server.util.AccountUtils;
import java.util.Locale;

/**
 * @author Alexander Savin
 */
public class UserInfo {

    private Long id;

    private String userName;

    private String email;

    private Locale locale;

    private String subtopicName;

    private AccountStatus status;

    public UserInfo(Long id, String userName, String email, Locale locale, AccountStatus status, String subtopicName) {
        this.id = id;
        this.userName = userName;
        this.email = email;
        this.locale = locale;
        this.status = status;
        this.subtopicName = subtopicName;
    }

    public Long getId() {
        return id;
    }

    public String getUserName() {
        return userName;
    }

    public String getEmail() {
        return email;
    }

    public Locale getLocale() {
        return locale;
    }

    public AccountStatus getStatus() {
        return status;
    }

    public String getSubtopicName() {
        return subtopicName;
    }

    public boolean isRemoved() {
        return AccountUtils.isRemoved(status);
    }
}
