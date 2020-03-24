package com.stockholdergame.server.facade.impl;

import com.stockholdergame.server.dto.ConfirmationDto;
import com.stockholdergame.server.dto.PasswordChangingDto;
import com.stockholdergame.server.dto.ProfileDto;
import com.stockholdergame.server.dto.RegistrationDto;
import com.stockholdergame.server.dto.account.ChangeEmailDto;
import com.stockholdergame.server.dto.account.ChangeUserNameDto;
import com.stockholdergame.server.dto.account.MyAccountDto;
import com.stockholdergame.server.dto.account.OperationTypeDto;
import com.stockholdergame.server.facade.AbstractFacade;
import com.stockholdergame.server.facade.AccountFacade;
import com.stockholdergame.server.localization.LocaleRegistry;
import com.stockholdergame.server.services.account.AccountService;
import com.stockholdergame.server.services.account.ProfileService;
import com.stockholdergame.server.services.security.CaptchaService;
import com.stockholdergame.server.session.UserSessionUtil;
import com.stockholdergame.server.validation.Validatable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.flex.remoting.RemotingDestination;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.validation.constraints.NotNull;

/**
 *
 */
@Component
@RemotingDestination(value = "accountFacade", channels = {"game-secure-amf", "game-amf"})
public class AccountFacadeImpl extends AbstractFacade implements AccountFacade {

    @Autowired
    private AccountService accountService;

    @Autowired
    private ProfileService profileService;

    @Autowired
    private CaptchaService captchaService;

    @Override
    public void setUnauthLanguage(String language) {
        if (UserSessionUtil.getSession() != null) {
            UserSessionUtil.getSession().setAttribute(UserSessionUtil.UNAUTH_LOCALE_ATTRIBUTE, LocaleRegistry.getLocale(language));
        }
    }

    @Transactional
    public void registerNewUser(@NotNull @Validatable RegistrationDto registrationDto) {
        accountService.registerNewUser(registrationDto);
    }

    @Transactional
    public void confirmAccountStatus(@NotNull @Validatable ConfirmationDto confirmationDto) {
        accountService.confirmAccountStatus(confirmationDto);
    }

    @Transactional
    public void changePassword(@NotNull @Validatable PasswordChangingDto passwordChangingDto) {
        accountService.changePassword(passwordChangingDto);
    }

    @Transactional
    public void changeUserName(@NotNull @Validatable ChangeUserNameDto changeUserNameDto) {
        // todo - blocked because of chat errors
        //accountService.changeUserName(changeUserNameDto.getNewUserName());
    }

    @Transactional
    public void changeEmail(@NotNull @Validatable ChangeEmailDto changeEmailDto) {
        accountService.changeEmail(changeEmailDto.getNewEmail());
    }

    @Transactional
    public void confirmNewEmail(@NotNull @Validatable ConfirmationDto confirmationDto) {
        accountService.confirmNewEmail(confirmationDto);
    }

    @Transactional
    public boolean checkUserNameExistence(@Validatable @NotNull String userName) {
        return accountService.checkUserNameExistence(userName);
    }

    @Transactional
    public boolean checkEmailUsage(@Validatable @NotNull String email) {
        return accountService.checkEmailUsage(email);
    }

    @Transactional
    public void resetPassword(@NotNull @Validatable String userName) {
        accountService.resetPassword(userName);
    }

    @Transactional
    public void removeAccount() {
        accountService.removeAccount();
    }

    @Transactional
    public void restoreAccount() {
        accountService.restoreAccount();
    }

    @Transactional
    public MyAccountDto getAccountInfo() {
        return accountService.getAccountInfo();
    }

    @Transactional
    public void changeProfileData(@NotNull @Validatable ProfileDto profileDto) {
        profileService.changeProfileData(profileDto);
    }

    @Override
    @Transactional
    public boolean updateAvatar(boolean update) {
        return profileService.updateAvatar(update);
    }

    @Transactional
    public void resendConfirmationEmail(@NotNull @Validatable OperationTypeDto operationTypeDto) {
        accountService.resendConfirmationEmail(operationTypeDto);
    }

    @Transactional
    public void cancelAccountOperation(@NotNull @Validatable OperationTypeDto operationTypeDto) {
        accountService.cancelAccountOperation(operationTypeDto);
    }

    @Transactional
    public void changeLanguage(@NotNull @Validatable String language) {
        accountService.changeLanguage(language);
    }

    public byte[] getCaptcha() {
        return captchaService.getCaptcha();
    }
}
