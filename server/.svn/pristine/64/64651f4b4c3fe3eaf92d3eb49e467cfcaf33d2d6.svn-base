package com.stockholdergame.server.facade;

import com.stockholdergame.server.dto.ConfirmationDto;
import com.stockholdergame.server.dto.PasswordChangingDto;
import com.stockholdergame.server.dto.ProfileDto;
import com.stockholdergame.server.dto.RegistrationDto;
import com.stockholdergame.server.dto.account.ChangeEmailDto;
import com.stockholdergame.server.dto.account.ChangeUserNameDto;
import com.stockholdergame.server.dto.account.MyAccountDto;
import com.stockholdergame.server.dto.account.OperationTypeDto;

/**
 *
 */
public interface AccountFacade {

    void setUnauthLanguage(String language);

/**
     * Resisters new user.
     * @param registrationDto DTO contains registration information: user name, password and e-mail.
     */
    void registerNewUser(RegistrationDto registrationDto);

    void confirmAccountStatus(ConfirmationDto confirmationDto);

    /**
     * Changes password for user who's nickname specified in DTO.
     * @param passwordChangingDto DTO contains user nick name, old password and new password for the specified user.
     */
    void changePassword(PasswordChangingDto passwordChangingDto);

    void changeUserName(ChangeUserNameDto changeUserNameDto);

    void changeEmail(ChangeEmailDto changeEmailDto);

    void confirmNewEmail(ConfirmationDto confirmationDto);

    /**
     * Checks user name existence.
     * @param userName user name.
     * @return true if user exists otherwise - false.
     */
    boolean checkUserNameExistence(String userName);

    boolean checkEmailUsage(String email);

    /**
     * Resets user password. Sends e-mail with new password for specified user.
     * @param userName contains user name or e-mail.
     */
    void resetPassword(String userName);

    /**
     * Removes user account.
     */
    void removeAccount();

    /**
     * Restores user account.
     */
    void restoreAccount();

    /**
     * Returns account information.
     * @return account information DTO.
     */
    MyAccountDto getAccountInfo();

    void changeProfileData(ProfileDto profileDto);

    boolean updateAvatar(boolean update);

    void resendConfirmationEmail(OperationTypeDto operationTypeDto);

    void cancelAccountOperation(OperationTypeDto operationTypeDto);

    void changeLanguage(String language);

    byte[] getCaptcha();
}
