package com.stockholdergame.server.dto.account;

import com.stockholdergame.server.dto.validation.constraints.EnumName;
import com.stockholdergame.server.dto.validation.constraints.NotBlank;
import com.stockholdergame.server.model.account.FriendRequestStatus;
import javax.validation.constraints.NotNull;

/**
 *
 */
public class FriendRequestStatusDto {

    @NotBlank
    private String requestorName;

    @NotNull
    @EnumName(enumClass = FriendRequestStatus.class)
    private String status;

    public String getRequestorName() {
        return requestorName;
    }

    public void setRequestorName(String requestorName) {
        this.requestorName = requestorName;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
