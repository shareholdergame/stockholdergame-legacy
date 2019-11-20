package com.stockholdergame.server.dto.account;

import com.stockholdergame.server.dto.validation.constraints.NotBlank;

import javax.validation.constraints.Size;

/**
 * @author Aliaksandr Savin
 */
public class ChangeUserNameDto {

    @NotBlank
    @Size(min = 3, max = 32)
    private String newUserName;

    public String getNewUserName() {
        return newUserName;
    }

    public void setNewUserName(String newUserName) {
        this.newUserName = newUserName;
    }
}
