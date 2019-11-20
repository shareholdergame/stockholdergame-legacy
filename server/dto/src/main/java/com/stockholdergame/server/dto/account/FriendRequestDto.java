package com.stockholdergame.server.dto.account;

import java.io.Serializable;
import java.util.Date;

/**
 *
 */
public class FriendRequestDto implements Serializable {

    private static final long serialVersionUID = 1640705872933485051L;

    private String requestorName;

    private Date createdDate;

    private String status;

    public FriendRequestDto() {
    }

    public FriendRequestDto(String requestorName, Date createdDate, String status) {
        this.requestorName = requestorName;
        this.createdDate = createdDate;
        this.status = status;
    }

    public String getRequestorName() {
        return requestorName;
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setRequestorName(String requestorName) {
        this.requestorName = requestorName;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
