package com.stockholdergame.server.dto.account;

import com.stockholdergame.server.dto.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

/**
 * @author Alexander Savin
 *         Date: 18.6.11 18.17
 */
public class CreateFriendRequestDto {

    @NotBlank
    private String userName;

    @Size(max = 4000)
    private String message;

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
