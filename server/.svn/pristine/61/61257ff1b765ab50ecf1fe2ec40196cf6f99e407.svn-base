package com.stockholdergame.server.dto.account;

import com.stockholdergame.server.dto.validation.constraints.EnumName;
import com.stockholdergame.server.model.account.OperationType;
import javax.validation.constraints.NotNull;

/**
 * @author Alexander Savin
 *         Date: 2.1.12 14.10
 */
public class OperationTypeDto {

    @NotNull
    @EnumName(enumClass = OperationType.class)
    private String operationType;

    public String getOperationType() {
        return operationType;
    }

    public void setOperationType(String operationType) {
        this.operationType = operationType;
    }
}
