package com.stockholdergame.server.gamecore.impl;

import com.stockholdergame.server.gamecore.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author Alexander Savin
 *         Date: 2.7.12 9.38
 */
public final class GameStateDataProvider {

    public static final long[] SHARE_IDS = new long[] {1L, 2L, 3L, 4L};

    public static GameState generateGameState(int priceStep, int maxPrice, int[] prices, int[][] quantities, int[] cashes,
                                              int movesQuantity, int currentMove, int currentMoveOrder, int[] outCompetitors) {
        List<SharePrice> sharePrices = createSharePrices(prices);
        List<CompetitorAccount> competitorAccounts = createCompetitorAccounts(sharePrices, quantities, cashes, outCompetitors);
        PriceScale priceScale = new PriceScaleImpl(maxPrice, priceStep, false);
        return new GameStateImpl(movesQuantity, currentMove, currentMoveOrder, sharePrices, competitorAccounts, priceScale);
    }

    private static List<CompetitorAccount> createCompetitorAccounts(List<SharePrice> sharePrices, int[][] quantities,
                                                                    int[] cashes, int[] outCompetitors) {
        List<CompetitorAccount> competitorAccounts = new ArrayList<CompetitorAccount>();
        int moveOrder = 1;
        for (int[] competitorShareQuantities : quantities) {
            CompetitorAccount competitorAccount = new CompetitorAccountImpl(moveOrder, Arrays.binarySearch(outCompetitors, moveOrder) > 0,
                    new CashImpl(cashes[moveOrder - 1]), createShareQuantities(sharePrices, competitorShareQuantities));
            competitorAccounts.add(competitorAccount);
            moveOrder++;
        }
        return competitorAccounts;
    }

    private static List<ShareQuantity> createShareQuantities(List<SharePrice> sharePrices, int[] competitorShareQuantities) {
        List<ShareQuantity> shareQuantities = new ArrayList<ShareQuantity>();
        int i = 0;
        for (SharePrice sharePrice : sharePrices) {
            ShareQuantity shareQuantity = new ShareQuantityImpl(sharePrice, competitorShareQuantities[i]);
            shareQuantities.add(shareQuantity);
            i++;
        }
        return shareQuantities;
    }

    private static List<SharePrice> createSharePrices(int[] prices) {
        List<SharePrice> sharePrices = new ArrayList<SharePrice>();
        int i = 0;
        for (long shareId : SHARE_IDS) {
            SharePrice sharePrice = new SharePriceImpl(shareId, prices[i]);
            sharePrices.add(sharePrice);
            i++;
        }
        return sharePrices;
    }
}
