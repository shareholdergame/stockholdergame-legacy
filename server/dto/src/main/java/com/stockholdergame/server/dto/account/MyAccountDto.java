package com.stockholdergame.server.dto.account;

import java.util.Date;
import java.util.List;

/**
 * @author Alexander Savin
 *         Date: 18.9.11 11.38
 */
public class MyAccountDto extends UserInfoDto {

    private String email;

    private String status;

    private Date registrationDate;

    private String subtopicName;

    private List<AccountOperationDto> accountOperations;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Date getRegistrationDate() {
        return registrationDate;
    }

    public void setRegistrationDate(Date registrationDate) {
        this.registrationDate = registrationDate;
    }

    public String getSubtopicName() {
        return subtopicName;
    }

    public void setSubtopicName(String subtopicName) {
        this.subtopicName = subtopicName;
    }

    public List<AccountOperationDto> getAccountOperations() {
        return accountOperations;
    }

    public void setAccountOperations(List<AccountOperationDto> accountOperations) {
        this.accountOperations = accountOperations;
    }
}
