package com.stockholdergame.server.dto.game;

/**
 * @author Alexander Savin
 *         Date: 23.10.2010 23.28.11
 */
public class PriceOperationDto {

    private Long shareId;

    private Long priceOperationId;

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
}
