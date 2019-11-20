package com.stockholdergame.server.gamecore.model.result;

import com.stockholdergame.server.gamecore.model.BuySellAction;

/**
 * @author Alexander Savin
 *         Date: 25.8.12 14.38
 */
public class BuySellActionResult extends BuySellAction {

    private int quantity;

    private int lockedQuantity;

    public BuySellActionResult(Long shareId, int buySellQuantity, int quantity, int lockedQuantity) {
        super(shareId, buySellQuantity);
        this.quantity = quantity;
        this.lockedQuantity = lockedQuantity;
    }

    public int getQuantity() {
        return quantity;
    }

    public int getLockedQuantity() {
        return lockedQuantity;
    }
}
