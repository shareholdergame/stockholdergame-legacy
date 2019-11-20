package com.stockholdergame.server.services.security;

/**
 * @author Alexander Savin
 *         Date: 24.4.12 10.49
 */
public interface CaptchaService {

    byte[] getCaptcha();

    boolean checkAnswer(String answer);
}
