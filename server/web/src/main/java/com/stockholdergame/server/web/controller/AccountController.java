package com.stockholdergame.server.web.controller;

import com.stockholdergame.server.dto.ConfirmationDto;
import com.stockholdergame.server.dto.PasswordChangingDto;
import com.stockholdergame.server.dto.ProfileDto;
import com.stockholdergame.server.dto.RegistrationDto;
import com.stockholdergame.server.dto.account.*;
import com.stockholdergame.server.services.account.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/account")
public class AccountController {

    @Autowired
    private AccountService accountService;

    @RequestMapping(value = "/ping", method = RequestMethod.GET)
    public @ResponseBody String ping() {
        return "Ping - OK";
    }

    @RequestMapping(value = "/secureping", method = RequestMethod.GET)
    public @ResponseBody String securePing() {
        return "Secure ping - OK";
    }

    @RequestMapping(value = "/new", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    public void registerNewUser(@RequestBody RegistrationDto registrationDto) {
        accountService.registerNewUser(registrationDto);
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

    public MyAccountDto getAccountInfo() {
        return null;
    }

    public void changeProfileData(ProfileDto profileDto) {

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
}
