package com.stockholdergame.server.model.account;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.*;

/**
 * @author Alexander Savin
 *         Date: 18.6.11 17.44
 */
@Entity
@Table(name = "a_friend_requests")
public class FriendRequest implements Serializable {

    private static final long serialVersionUID = 5038921696364109404L;

    @Id
    @GeneratedValue
    @Column(name = "friend_request_id")
    private Long id;

    @Column(name = "requestor_id")
    private Long requestorId;

    @Column(name = "requestee_id")
    private Long requesteeId;

    @Column(name = "status_id")
    @Enumerated
    private FriendRequestStatus status;

    @Column(name = "created_date")
    private Date createdDate;

    @Column(name = "completed_date")
    private Date completedDate;

    @OneToOne
    @JoinColumn(name = "user_message_id")
    private ChatMessage message;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "requestor_id", insertable = false, updatable = false)
    private GamerAccount requestor;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "requestee_id", insertable = false, updatable = false)
    private GamerAccount requestee;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getRequestorId() {
        return requestorId;
    }

    public void setRequestorId(Long requestorId) {
        this.requestorId = requestorId;
    }

    public Long getRequesteeId() {
        return requesteeId;
    }

    public void setRequesteeId(Long requesteeId) {
        this.requesteeId = requesteeId;
    }

    public FriendRequestStatus getStatus() {
        return status;
    }

    public void setStatus(FriendRequestStatus status) {
        this.status = status;
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    public Date getCompletedDate() {
        return completedDate;
    }

    public void setCompletedDate(Date completedDate) {
        this.completedDate = completedDate;
    }

    public ChatMessage getMessage() {
        return message;
    }

    public void setMessage(ChatMessage message) {
        this.message = message;
    }

    public GamerAccount getRequestor() {
        return requestor;
    }

    public void setRequestor(GamerAccount requestor) {
        this.requestor = requestor;
    }

    public GamerAccount getRequestee() {
        return requestee;
    }

    public void setRequestee(GamerAccount requestee) {
        this.requestee = requestee;
    }
}
