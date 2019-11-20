package com.stockholdergame.server.dto.game.variant;

/**
 * @author Alexander Savin
 *         Date: 9.10.2010 0.04.52
 */
public class CardOperationDto {

    private Long shareId;

    private Long priceOperationId;

    private String operation;

    private Integer operandValue;

    public Long getShareId() {
        return shareId;
    }

    public void setShareId(Long shareId) {
        this.shareId = shareId;
    }

    public Long getPriceOperationId() {
        return priceOperationId;
    }

    public void setPriceOperationId(Long priceOperationId) {
        this.priceOperationId = priceOperationId;
    }

    public String getOperation() {
        return operation;
    }

    public void setOperation(String operation) {
        this.operation = operation;
    }

    public Integer getOperandValue() {
        return operandValue;
    }

    public void setOperandValue(Integer operandValue) {
        this.operandValue = operandValue;
    }
}
