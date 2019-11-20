package com.stockholdergame.server.dto.game;

/**
 * @author Alexander Savin
 *         Date: 23.10.2010 14.19.24
 */
public class BuySellDto {

    private Long shareId;

    private Integer buySellQuantity;

    public Long getShareId() {
        return shareId;
    }

    public void setShareId(Long shareId) {
        this.shareId = shareId;
    }

    public Integer getBuySellQuantity() {
        return buySellQuantity;
    }

    public void setBuySellQuantity(Integer buySellQuantity) {
        this.buySellQuantity = buySellQuantity;
    }
}
