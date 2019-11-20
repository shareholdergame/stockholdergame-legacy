package com.stockholdergame.server.session;

/**
 * @author Alexander Savin
 */
public class UserSessionData {

    private UserInfo userInfo;

    private String userIPAddress;

    private Long sessionId;

    private String httpSessionId;

    public UserSessionData(String userIPAddress, String httpSessionId) {
        this.userIPAddress = userIPAddress;
        this.httpSessionId = httpSessionId;
    }

    public UserInfo getUserInfo() {
        return userInfo;
    }

    public void setUserInfo(UserInfo userInfo) {
        this.userInfo = userInfo;
    }

    public String getUserIPAddress() {
        return userIPAddress;
    }

    public Long getSessionId() {
        return sessionId;
    }

    public void setSessionId(Long sessionId) {
        this.sessionId = sessionId;
    }

    public String getHttpSessionId() {
        return httpSessionId;
    }
}
