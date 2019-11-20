package com.stockholdergame.server.gamecore.builders;

import com.stockholdergame.server.gamecore.SharePrice;
import com.stockholdergame.server.gamecore.ShareQuantity;
import com.stockholdergame.server.gamecore.impl.CashImpl;
import com.stockholdergame.server.gamecore.impl.CompetitorAccountImpl;
import com.stockholdergame.server.gamecore.impl.ShareQuantityImpl;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Alexander Savin
 *         Date: 3.7.12 17.31
 */
public class CompetitorAccountBuilder<T> {

    private GameStateBuilder<?> gameStateBuilder;
    private int moveOrder;
    private boolean isOut;
    private int cashValue;
    private T extraData;
    private List<ShareQuantity> shareQuantities = new ArrayList<>();

    public static <T> CompetitorAccountBuilder<T> newBuilder(GameStateBuilder<?> gameStateBuilder, int moveOrder, boolean isOut, int cashValue) {
        return new CompetitorAccountBuilder<>(gameStateBuilder, moveOrder, isOut, cashValue);
    }

    private CompetitorAccountBuilder(GameStateBuilder<?> gameStateBuilder, int moveOrder, boolean isOut, int cashValue) {
        this.gameStateBuilder = gameStateBuilder;
        this.moveOrder = moveOrder;
        this.isOut = isOut;
        this.cashValue = cashValue;
    }

    public CompetitorAccountBuilder addShareQuantity(Long shareId, int quantity) {
        for (SharePrice sharePrice : gameStateBuilder.sharePrices) {
            if (sharePrice.getShareId().equals(shareId)) {
                shareQuantities.add(new ShareQuantityImpl(sharePrice, quantity));
            }
        }
        return this;
    }

    public CompetitorAccountBuilder<T> setExtraData(T extraData) {
        this.extraData = extraData;
        return this;
    }

    public void finishBuilding() {
        CompetitorAccountImpl<T> competitorAccount = new CompetitorAccountImpl<>(moveOrder, isOut, new CashImpl(cashValue),
                shareQuantities);
        competitorAccount.setExtraData(extraData);
        gameStateBuilder.competitorAccounts.add(competitorAccount);
    }
}
