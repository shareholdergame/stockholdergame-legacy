package com.stockholdergame.server.gamecore.model;

import com.stockholdergame.server.gamecore.model.math.ArithmeticOperation;
import org.apache.commons.lang3.Validate;

/**
 * @author Aliaksandr Savin
 */
public class PriceChangeAction implements Comparable<PriceChangeAction> {

    private Long shareId;

    private ArithmeticOperation arithmeticOperation;

    private int operandValue;

    public PriceChangeAction(Long shareId, ArithmeticOperation arithmeticOperation, int operandValue) {
        Validate.notNull(shareId);

        this.shareId = shareId;
        this.arithmeticOperation = arithmeticOperation;
        this.operandValue = operandValue;
    }

    public Long getShareId() {
        return shareId;
    }

    public ArithmeticOperation getArithmeticOperation() {
        return arithmeticOperation;
    }

    public int getOperandValue() {
        return operandValue;
    }

    @Override
    public int compareTo(PriceChangeAction o) {
        if (this == o) {
            return 0;
        }
        return (int) (shareId - o.shareId);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        PriceChangeAction that = (PriceChangeAction) o;

        if (operandValue != that.operandValue) return false;
        if (arithmeticOperation != that.arithmeticOperation) return false;
        if (!shareId.equals(that.shareId)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = shareId.hashCode();
        result = 31 * result + operandValue + arithmeticOperation.hashCode();
        return result;
    }
}
