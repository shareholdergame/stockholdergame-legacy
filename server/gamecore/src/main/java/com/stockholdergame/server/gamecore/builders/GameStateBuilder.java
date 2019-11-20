package com.stockholdergame.server.gamecore.builders;

import com.stockholdergame.server.gamecore.CompetitorAccount;
import com.stockholdergame.server.gamecore.GameState;
import com.stockholdergame.server.gamecore.PriceScale;
import com.stockholdergame.server.gamecore.SharePrice;
import com.stockholdergame.server.gamecore.impl.GameStateImpl;
import com.stockholdergame.server.gamecore.impl.PriceScaleImpl;
import com.stockholdergame.server.gamecore.impl.SharePriceImpl;
import org.apache.commons.lang3.Validate;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Alexander Savin
 *         Date: 3.7.12 17.09
 */
public class GameStateBuilder<T> {

    private int movesQuantity;
    private int lastMoveNumber;
    private int lastMoveOrder;
    private PriceScale priceScale;
    private T extraData;
    List<SharePrice> sharePrices = new ArrayList<>();
    List<CompetitorAccount> competitorAccounts = new ArrayList<>();

    public static <T> GameStateBuilder<T> newBuilder(int movesQuantity, int lastMoveNumber, int lastMoveOrder, int priceStep, int maxPrice,
                                                     boolean roundingDown) {
        return new GameStateBuilder<>(movesQuantity, lastMoveNumber, lastMoveOrder, priceStep, maxPrice, roundingDown);
    }

    private GameStateBuilder(int movesQuantity, int lastMoveNumber, int lastMoveOrder, int priceStep, int maxPrice, boolean roundingDown) {
        this.movesQuantity = movesQuantity;
        this.lastMoveNumber = lastMoveNumber;
        this.lastMoveOrder = lastMoveOrder;
        priceScale = new PriceScaleImpl(maxPrice, priceStep, roundingDown);
    }

    public GameStateBuilder addShare(Long shareId, int price) {
        sharePrices.add(new SharePriceImpl(shareId, price));
        return this;
    }

    public <X> CompetitorAccountBuilder<X> addCompetitor(int moveOrder, boolean isOut, int cashValue) {
        return CompetitorAccountBuilder.newBuilder(this, moveOrder, isOut, cashValue);
    }

    public GameStateBuilder setExtraData(T extraData) {
        this.extraData = extraData;
        return this;
    }

    public GameState getGameState() {
        Validate.notEmpty(sharePrices, "Share prices weren't specified");
        Validate.notEmpty(competitorAccounts, "Competitors weren't added");

        GameStateImpl<T> gameState = new GameStateImpl<>(movesQuantity, lastMoveNumber, lastMoveOrder, sharePrices, competitorAccounts, priceScale);
        gameState.setExtraData(extraData);
        gameState.commit();
        return gameState;
    }
}
