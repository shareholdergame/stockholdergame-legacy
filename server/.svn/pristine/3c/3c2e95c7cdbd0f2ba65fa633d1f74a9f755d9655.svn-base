package com.stockholdergame.server.services.account;

import com.stockholdergame.server.dto.ConfirmationDto;
import com.stockholdergame.server.dto.PasswordChangingDto;
import com.stockholdergame.server.dto.RegistrationDto;
import com.stockholdergame.server.dto.account.MyAccountDto;
import com.stockholdergame.server.dto.account.OperationTypeDto;
import com.stockholdergame.server.dto.account.UserFilterDto;
import com.stockholdergame.server.dto.account.UsersList;

/**
 * Account service interface.
 *
 * @author Alexander Savin
 *         Date: 20.2.2010 13.32.38
 */
public interface AccountService {

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

    void changeEmail(String newEmail);

    void confirmNewEmail(ConfirmationDto confirmationDto);

    /**
     * Checks user name existence.
     * @param userName user name.
     * @return true if user exists otherwise - false.
     */
    boolean checkUserNameExistence(String userName);

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

    /**
     * Returns filtered list of users.
     * @param userFilter users filter.
     * @return list of users.
     */
    UsersList getUsers(UserFilterDto userFilter);

    void resendConfirmationEmail(OperationTypeDto operationTypeDto);

    void cancelAccountOperation(OperationTypeDto operationTypeDto);

    boolean checkEmailUsage(String email);

    void changeUserName(String newUserName);

    void changeLanguage(String language);
}
