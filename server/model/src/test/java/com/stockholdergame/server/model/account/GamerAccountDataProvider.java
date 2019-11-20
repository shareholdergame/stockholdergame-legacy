package com.stockholdergame.server.model.account;

public final class GamerAccountDataProvider {

    private GamerAccountDataProvider() {
    }

    public static final Long USER_TEST_ID = 1L;

    public static final String USER_TEST_NAME = "test";

    public static final String USER_TEST_EMAIL = "test@test.com";

    public static final String USER_TEST_PWD = "pwd";

    public static GamerAccount createTestAccountNew() {
        return createGamerAccount(USER_TEST_ID, USER_TEST_NAME, USER_TEST_EMAIL,
            USER_TEST_PWD, AccountStatus.NEW);
    }

    public static GamerAccount createTestAccountRemoved() {
        return createGamerAccount(USER_TEST_ID, USER_TEST_NAME, USER_TEST_EMAIL,
            USER_TEST_PWD, AccountStatus.REMOVED);
    }

    public static GamerAccount createTestAccountRemovedCompletely() {
        return createGamerAccount(USER_TEST_ID, USER_TEST_NAME, USER_TEST_EMAIL,
            USER_TEST_PWD, AccountStatus.REMOVED_COMPLETELY);
    }

    private static GamerAccount createGamerAccount(Long userTestId, String userName, String email, String password,
                                                   AccountStatus status) {
        GamerAccount ga = new GamerAccount();
        ga.setId(userTestId);
        ga.setUserName(userName);
        ga.setEmail(email);
        ga.setPassword(password);
        ga.setStatus(status);
        return ga;
    }
}
