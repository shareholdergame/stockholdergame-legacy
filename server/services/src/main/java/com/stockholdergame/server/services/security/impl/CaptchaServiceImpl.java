package com.stockholdergame.server.services.security.impl;

import com.stockholdergame.server.services.security.CaptchaService;
import org.springframework.stereotype.Service;

/**
 * @author Alexander Savin
 *         Date: 24.4.12 10.52
 */
@Service
public class CaptchaServiceImpl implements CaptchaService {
    // todo - reimplement CAPTCHA service!!!
    //private ImageCaptchaService captchaService = new DefaultManageableImageCaptchaService();

    public byte[] getCaptcha() {
        /*ByteArrayOutputStream jpegOutputStream = new ByteArrayOutputStream();
        try {
            String captchaId = UserSessionUtil.getHttpSessionId();
            BufferedImage challenge = captchaService.getImageChallengeForID(captchaId, Locale.US);
            JPEGImageEncoder jpegEncoder = JPEGCodec.createJPEGEncoder(jpegOutputStream);
            jpegEncoder.encode(challenge);
        } catch (IllegalArgumentException e) {
            throw new ApplicationException(e);
        } catch (CaptchaServiceException e) {
            throw new ApplicationException(e);
        } catch (IOException e) {
            throw new ApplicationException(e);
        }
        return jpegOutputStream.toByteArray();*/
        return null;
    }

    public boolean checkAnswer(String answer) {
        /*boolean isAnswerCorrect;
        String captchaId = UserSessionUtil.getHttpSessionId();
        try {
            isAnswerCorrect = captchaService.validateResponseForID(captchaId, answer);
        } catch (CaptchaServiceException e) {
            throw new BusinessException(BusinessExceptionType.CAPTCHA_NOT_REQUESTED);
        }
        return isAnswerCorrect;*/
        return true;
    }
}
