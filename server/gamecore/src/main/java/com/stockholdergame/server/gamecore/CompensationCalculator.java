package com.stockholdergame.server.gamecore;

/**
 * @author Alexander Savin
 *         Date: 19.6.12 23.54
 */
public final class CompensationCalculator {

    private CompensationCalculator() {
    }

    public static int calculateCompensation(int oldPrice, int finalPrice, int calculatedPrice, int shareQuantity,
                                            boolean isCurrentCompetitor) {
        int compensation = 0;
        if (finalPrice <= oldPrice && calculatedPrice <= finalPrice) {
            if (isCurrentCompetitor) {
                compensation = (oldPrice - finalPrice) * shareQuantity;
            } /*else if (calculatedPrice < finalPrice) {
                compensation = -((finalPrice - calculatedPrice) * shareQuantity);
            }*/
        } else if (finalPrice >= oldPrice && calculatedPrice > finalPrice) {
            compensation = (calculatedPrice - finalPrice) * shareQuantity;
        }

        return compensation;
    }

    public static int calculateRedemptionSum(int finalPrice, int calculatedPrice) {
        return finalPrice - calculatedPrice;
    }
}
