package com.stockholdergame.server.model.game.variant;

import com.stockholdergame.server.gamecore.model.math.ArithmeticOperation;
import com.stockholdergame.server.model.Descriptable;

import static com.stockholdergame.server.model.i18n.ModelResourceBundleKeys.CARD_OPERATION_ADDITION;
import static com.stockholdergame.server.model.i18n.ModelResourceBundleKeys.CARD_OPERATION_DIVISION;
import static com.stockholdergame.server.model.i18n.ModelResourceBundleKeys.CARD_OPERATION_MULTIPLICATION;
import static com.stockholdergame.server.model.i18n.ModelResourceBundleKeys.CARD_OPERATION_SUBTRACTION;

/**
 * @author Alexander Savin
 */
public enum PriceOperationType implements Descriptable {

    ADDITION ("+", CARD_OPERATION_ADDITION, ArithmeticOperation.ADDITION),

    SUBTRACTION ("-", CARD_OPERATION_SUBTRACTION, ArithmeticOperation.SUBTRACTION),

    MULTIPLICATION ("x", CARD_OPERATION_MULTIPLICATION, ArithmeticOperation.MULTIPLICATION),

    DIVISION (":", CARD_OPERATION_DIVISION, ArithmeticOperation.DIVISION);

    private String operationSign;

    private String description;

    private ArithmeticOperation operation;

    PriceOperationType(String operationSign, String description, ArithmeticOperation operation) {
        this.operationSign = operationSign;
        this.description = description;
        this.operation = operation;
    }

    public String getOperationSign() {
        return operationSign;
    }

    public String getDescription() {
        return description;
    }

    public ArithmeticOperation getOperation() {
        return operation;
    }

    public static PriceOperationType signOf(String operationSign) {
        for (PriceOperationType priceOperationType : PriceOperationType.values()) {
            if (priceOperationType.getOperationSign().equals(operationSign)) {
                return priceOperationType;
            }
        }
        return null;
    }
}
