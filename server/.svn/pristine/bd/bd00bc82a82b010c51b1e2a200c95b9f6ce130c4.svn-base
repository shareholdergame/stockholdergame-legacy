package com.stockholdergame.server.model.account;

import com.stockholdergame.server.model.Identifiable;
import java.util.Date;
import javax.persistence.*;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

/**
 * @author Alexander Savin
 */
@Entity
@Table(name = "a_account_operations")
public class AccountOperation implements Identifiable<Long> {

    private static final long serialVersionUID = -8788115040062137834L;

    @Id
    @GeneratedValue
    @Column(name = "operation_id")
    private Long id;

    @Column(name = "gamer_id", nullable = false)
    private Long gamerId;

    @Column(name = "oper_type_id", nullable = false)
    @Enumerated
    private OperationType operationType;

    @Column(name = "old_value")
    private String oldValue;

    @Column(name = "new_value")
    private String newValue;

    @Column(name = "verification_code")
    private String verificationCode;

    @Column(name = "initiation_date", nullable = false)
    private Date initiationDate;

    @Column(name = "completion_date")
    private Date completionDate;

    @Column(name = "oper_status_id", nullable = false)
    @Enumerated
    private OperationStatus operationStatus;

    @Column(name = "expiration_date")
    private Date expirationDate;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getGamerId() {
        return gamerId;
    }

    public void setGamerId(Long gamerId) {
        this.gamerId = gamerId;
    }

    public String getOldValue() {
        return oldValue;
    }

    public void setOldValue(String oldValue) {
        this.oldValue = oldValue;
    }

    public String getNewValue() {
        return newValue;
    }

    public void setNewValue(String newValue) {
        this.newValue = newValue;
    }

    public String getVerificationCode() {
        return verificationCode;
    }

    public void setVerificationCode(String verificationCode) {
        this.verificationCode = verificationCode;
    }

    public Date getInitiationDate() {
        return initiationDate;
    }

    public void setInitiationDate(Date initiationDate) {
        this.initiationDate = initiationDate;
    }

    public OperationType getOperationType() {
        return operationType;
    }

    public void setOperationType(OperationType operationType) {
        this.operationType = operationType;
    }

    public Date getCompletionDate() {
        return completionDate;
    }

    public void setCompletionDate(Date completionDate) {
        this.completionDate = completionDate;
    }

    public OperationStatus getOperationStatus() {
        return operationStatus;
    }

    public void setOperationStatus(OperationStatus operationStatus) {
        this.operationStatus = operationStatus;
    }

    public Date getExpirationDate() {
        return expirationDate;
    }

    public void setExpirationDate(Date expirationDate) {
        this.expirationDate = expirationDate;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("id", id)
                .append("gamerId", gamerId)
                .append("operationType", operationType)
                .append("oldValue", oldValue)
                .append("newValue", newValue)
                .append("initiationDate", initiationDate)
                .append("completionDate", completionDate)
                .append("expirationDate", expirationDate)
                .append("operationStatus", operationStatus)
                .toString();
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof AccountOperation)) {
            return false;
        }
        AccountOperation ao = (AccountOperation) o;
        return new EqualsBuilder()
                .append(gamerId, ao.gamerId)
                .append(operationType, ao.operationType)
                .append(operationStatus, ao.operationStatus)
                .append(initiationDate, ao.initiationDate)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder()
                .append(gamerId)
                .append(operationType)
                .append(operationStatus)
                .append(initiationDate)
                .toHashCode();
    }
}
