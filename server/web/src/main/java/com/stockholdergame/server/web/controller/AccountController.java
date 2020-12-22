package com.stockholdergame.server.web.controller;

import com.stockholdergame.server.dto.ConfirmationDto;
import com.stockholdergame.server.dto.PasswordChangingDto;
import com.stockholdergame.server.dto.ProfileDto;
import com.stockholdergame.server.dto.RegistrationDto;
import com.stockholdergame.server.dto.account.*;
import com.stockholdergame.server.services.account.AccountService;
import com.stockholdergame.server.web.dto.AccountDetails;
import com.stockholdergame.server.web.dto.ResponseWrapper;
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
    private AccountService accountService;

    @RequestMapping(method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody ResponseWrapper<AccountDetails> getAccountInfo() {
        MyAccountDto myAccountDto = accountService.getAccountInfo();
        return ResponseWrapper.ok(convertToAccountDetails(myAccountDto));
    }

    @RequestMapping(value = "/new", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody ResponseWrapper<?> registerNewUser(@RequestBody RegistrationDto registrationDto) {
        accountService.registerNewUser(registrationDto);
        return ResponseWrapper.ok();
    }

    public void confirmAccountStatus(ConfirmationDto confirmationDto) {

    }

    public void changePassword(PasswordChangingDto passwordChangingDto) {

    }

    public void changeUserName(ChangeUserNameDto changeUserNameDto) {

    }

    public void changeEmail(ChangeEmailDto changeEmailDto) {

    }

    public void confirmNewEmail(ConfirmationDto confirmationDto) {

    }

    public boolean checkUserNameExistence(String userName) {
        return false;
    }

    public boolean checkEmailUsage(String email) {
        return false;
    }

    public void resetPassword(String userName) {

    }

    public void removeAccount() {

    }

    public void restoreAccount() {

    }


    public boolean updateAvatar(boolean update) {
        return false;
    }

    public void resendConfirmationEmail(OperationTypeDto operationTypeDto) {

    }

    public void cancelAccountOperation(OperationTypeDto operationTypeDto) {

    }

    public void changeLanguage(String language) {

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
