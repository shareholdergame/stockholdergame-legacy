package com.stockholdergame.server.gamecore.model;

import org.apache.commons.lang3.Validate;

/**
 * @author Alexander Savin
 *         Date: 1.7.12 14.48
 */
public class BuySellAction implements Comparable<BuySellAction> {

    private Long shareId;

    private int buySellQuantity;

    public BuySellAction(Long shareId, int buySellQuantity) {
        Validate.notNull(shareId);

        this.shareId = shareId;
        this.buySellQuantity = buySellQuantity;
    }

    public Long getShareId() {
        return shareId;
    }

    public int getBuySellQuantity() {
        return buySellQuantity;
    }

    public int compareTo(BuySellAction o) {
        if (this == o) {
            return 0;
        }
        int result = this.buySellQuantity - o.buySellQuantity;
        if (result == 0) {
            result = (int) (this.shareId - o.shareId);
        }
        return result;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        BuySellAction that = (BuySellAction) o;

        if (buySellQuantity != that.buySellQuantity) return false;
        if (!shareId.equals(that.shareId)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = shareId.hashCode();
        result = 31 * result + buySellQuantity;
        return result;
    }
}
