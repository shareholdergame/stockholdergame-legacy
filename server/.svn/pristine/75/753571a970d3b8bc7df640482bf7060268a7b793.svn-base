package com.stockholdergame.server.services.account;

import com.stockholdergame.server.model.account.AccountStatus;

/**
 * @author Alexander Savin
 *         Date: 27.11.11 22.25
 */
public interface AccountLifecycleService {

    void createNewAccount(String userName, String email, String password, String language);

    void changeStatus(Long gamerId, AccountStatus toStatus);

    void confirmStatusChange(Long gamerId, String verificationCode);

    void removeAccount(Long gamerId);
}
