package com.stockholdergame.server.dto.game;

/**
 * @author Alexander Savin
 */
public class ShareQuantityDto {

    private Long id;

    private Integer quantity;

    private Integer buySellQuantity;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public Integer getBuySellQuantity() {
        return buySellQuantity;
    }

    public void setBuySellQuantity(Integer buySellQuantity) {
        this.buySellQuantity = buySellQuantity;
    }
}
