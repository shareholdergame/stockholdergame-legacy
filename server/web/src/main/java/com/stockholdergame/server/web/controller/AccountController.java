package com.stockholdergame.server.web.controller;

import com.stockholdergame.server.dto.ConfirmationDto;
import com.stockholdergame.server.dto.PasswordChangingDto;
import com.stockholdergame.server.dto.RegistrationDto;
import com.stockholdergame.server.dto.account.*;
import com.stockholdergame.server.facade.AccountFacade;
import com.stockholdergame.server.model.account.OperationType;
import com.stockholdergame.server.web.dto.*;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.text.SimpleDateFormat;

@Controller
@RequestMapping("/account")
public class AccountController {

    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");

    @Autowired
    private AccountFacade accountFacade;

    @RequestMapping(value = "/exists", method = RequestMethod.POST, consumes = MediaType.TEXT_PLAIN_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody ResponseWrapper<Boolean> checkUserExistence(@RequestBody String userNameOrEmail) {
        boolean userNameExists = accountFacade.checkUserNameExistence(userNameOrEmail);
        boolean emailExists = accountFacade.checkEmailUsage(userNameOrEmail);
        return ResponseWrapper.ok(userNameExists || emailExists);
    }

    @RequestMapping(method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody ResponseWrapper<AccountDetails> getAccountInfo() {
        MyAccountDto myAccountDto = accountFacade.getAccountInfo();
        return ResponseWrapper.ok(convertToAccountDetails(myAccountDto));
    }

    @RequestMapping(value = "/new", method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody ResponseWrapper<?> signUp(@RequestBody SignUp signUp) {
        RegistrationDto registrationDto = new RegistrationDto();
        registrationDto.setUserName(signUp.userName);
        registrationDto.setEmail(signUp.email);
        registrationDto.setPassword(signUp.password);
        registrationDto.setLanguage(signUp.language);
        accountFacade.registerNewUser(registrationDto);
        return ResponseWrapper.ok();
    }

    @RequestMapping(value = "/verify/{verificationCode}", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody ResponseWrapper<?> verifyAccountChange(@PathVariable("verificationCode") String verificationCode,
                                                                @RequestParam(value = "operation") AccountOperation operation) {
        ConfirmationDto confirmationDto = new ConfirmationDto();
        confirmationDto.setVerificationCode(verificationCode);
        if (operation.equals(AccountOperation.status_change)) {
            accountFacade.confirmAccountStatus(confirmationDto);
        } else {
            accountFacade.confirmNewEmail(confirmationDto);
        }
        return ResponseWrapper.ok();
    }

    @RequestMapping(value = "/resetpassword", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody ResponseWrapper<?> resetPassword(@RequestBody String email) {
        accountFacade.resetPassword(email);
        return ResponseWrapper.ok();
    }

    @RequestMapping(value = "/update", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE,
            consumes = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody ResponseWrapper<?> update(@RequestBody AccountUpdate accountUpdate) {
        MyAccountDto myAccountDto = accountFacade.getAccountInfo();
        if (StringUtils.isNotEmpty(accountUpdate.newUserName)
                && !myAccountDto.getUserName().equalsIgnoreCase(accountUpdate.newUserName)) {
            ChangeUserNameDto changeUserNameDto = new ChangeUserNameDto();
            changeUserNameDto.setNewUserName(accountUpdate.newUserName);
            accountFacade.changeUserName(changeUserNameDto);
        }

        if (StringUtils.isNotEmpty(accountUpdate.newEmail)
                && !myAccountDto.getEmail().equalsIgnoreCase(accountUpdate.newEmail)) {
            ChangeEmailDto changeEmailDto = new ChangeEmailDto();
            changeEmailDto.setNewEmail(accountUpdate.newEmail);
            accountFacade.changeEmail(changeEmailDto);
        }

        if (StringUtils.isNotEmpty(accountUpdate.language)) {
            accountFacade.changeLanguage(accountUpdate.language);
        }

        PasswordUpdate passwordUpdate = accountUpdate.passwordUpdate;
        if (passwordUpdate != null
                && StringUtils.isNotEmpty(passwordUpdate.oldPassword) && StringUtils.isNotEmpty(passwordUpdate.newPassword)
                && !passwordUpdate.oldPassword.equals(passwordUpdate.newPassword)) {
            PasswordChangingDto passwordChangingDto = new PasswordChangingDto();
            passwordChangingDto.setNewPassword(passwordUpdate.newPassword);
            passwordChangingDto.setOldPassword(passwordUpdate.oldPassword);
            accountFacade.changePassword(passwordChangingDto);
        }

        return ResponseWrapper.ok();
    }

    @RequestMapping(value = "/changestatus", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody ResponseWrapper<?> changeAccountStatus(@RequestParam("action") AccountStatusAction accountStatusAction) {
        if (accountStatusAction.equals(AccountStatusAction.remove)) {
            accountFacade.removeAccount();
        } else {
            accountFacade.restoreAccount();
        }
        return ResponseWrapper.ok();
    }

    @RequestMapping(value = "/verify/resend", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody ResponseWrapper<?> resendVerificationCode(@RequestParam("operation") AccountOperation operation) {
        OperationTypeDto operationTypeDto = new OperationTypeDto();
        operationTypeDto.setOperationType(operation.equals(AccountOperation.status_change)
                ? OperationType.STATUS_CHANGED.name() : OperationType.EMAIL_CHANGED.name());
        accountFacade.resendConfirmationEmail(operationTypeDto);
        return ResponseWrapper.ok();
    }

    private AccountDetails convertToAccountDetails(MyAccountDto myAccountDto) {
        AccountDetails accountDetails = new AccountDetails();
        accountDetails.userName = myAccountDto.getUserName();
        accountDetails.email = myAccountDto.getEmail();
        accountDetails.status = myAccountDto.getStatus();
        accountDetails.creationDate = simpleDateFormat.format(myAccountDto.getRegistrationDate());
        accountDetails.language = myAccountDto.getLocale();
        return accountDetails;
    }
}
