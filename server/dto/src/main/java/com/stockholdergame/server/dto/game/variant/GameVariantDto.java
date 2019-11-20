package com.stockholdergame.server.dto.game.variant;

import java.util.Set;

/**
 * @author Alexander Savin
 *         Date: 20.9.2010 8.15.00
 */
public class GameVariantDto {

    private Long id;

    private String name;

    private Integer maxGamersQuantity;

    private String rounding;

    private Integer movesQuantity;

    private Integer gamerInitialCash;

    private PriceScaleDto priceScale;

    private Set<GameShareDto> shares;

    private Set<CardGroupDto> cardGroups;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getMaxGamersQuantity() {
        return maxGamersQuantity;
    }

    public void setMaxGamersQuantity(Integer maxGamersQuantity) {
        this.maxGamersQuantity = maxGamersQuantity;
    }

    public String getRounding() {
        return rounding;
    }

    public void setRounding(String rounding) {
        this.rounding = rounding;
    }

    public Integer getMovesQuantity() {
        return movesQuantity;
    }

    public void setMovesQuantity(Integer movesQuantity) {
        this.movesQuantity = movesQuantity;
    }

    public Integer getGamerInitialCash() {
        return gamerInitialCash;
    }

    public void setGamerInitialCash(Integer gamerInitialCash) {
        this.gamerInitialCash = gamerInitialCash;
    }

    public PriceScaleDto getPriceScale() {
        return priceScale;
    }

    public void setPriceScale(PriceScaleDto priceScale) {
        this.priceScale = priceScale;
    }

    public Set<GameShareDto> getShares() {
        return shares;
    }

    public void setShares(Set<GameShareDto> shares) {
        this.shares = shares;
    }

    public Set<CardGroupDto> getCardGroups() {
        return cardGroups;
    }

    public void setCardGroups(Set<CardGroupDto> cardGroups) {
        this.cardGroups = cardGroups;
    }
}
