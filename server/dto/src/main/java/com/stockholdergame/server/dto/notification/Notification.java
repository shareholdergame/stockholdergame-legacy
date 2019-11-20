package com.stockholdergame.server.dto.notification;

import java.io.Serializable;

/**
 * @author Alexander Savin
 *         Date: 12.1.13 15.09
 */
public class Notification implements Serializable {

    private static final long serialVersionUID = -447284077733985287L;

    private Long addresseeId;

    private Long senderId;

    private NotificationType type;

    private Object body;

    public Notification() {
    }

    public Notification(Long addresseeId, Long senderId, NotificationType type, Object body) {
        this.addresseeId = addresseeId;
        this.senderId = senderId;
        this.type = type;
        this.body = body;
    }

    public Long getAddresseeId() {
        return addresseeId;
    }

    public void setAddresseeId(Long addresseeId) {
        this.addresseeId = addresseeId;
    }

    public Long getSenderId() {
        return senderId;
    }

    public void setSenderId(Long senderId) {
        this.senderId = senderId;
    }

    public NotificationType getType() {
        return type;
    }

    public void setType(NotificationType type) {
        this.type = type;
    }

    public Object getBody() {
        return body;
    }

    public void setBody(Object body) {
        this.body = body;
    }
}
