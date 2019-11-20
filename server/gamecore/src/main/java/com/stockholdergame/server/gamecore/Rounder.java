package com.stockholdergame.server.gamecore;

/**
 * Rounds calculated price values to scale step.
 */
public final class Rounder {

    private Rounder() {
    }

    public static int round(int value, int scaleStep, boolean isRoundingDown) {
        int roundedValue = value;
        int remainder = value % scaleStep;
        if (value == scaleStep / 2) {
            return roundedValue;
        } else if (remainder > 0) {
            if (isRoundingDown) {
                roundedValue -= remainder;
            } else {
                roundedValue += (scaleStep - remainder);
            }
        }
        return roundedValue;
    }
}
