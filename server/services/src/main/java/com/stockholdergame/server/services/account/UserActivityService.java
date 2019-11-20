package com.stockholdergame.server.services.account;

public interface UserActivityService {

    void stopUserActivity(Long gamerId);

    void removeUserActivity(Long gamerId);
}
