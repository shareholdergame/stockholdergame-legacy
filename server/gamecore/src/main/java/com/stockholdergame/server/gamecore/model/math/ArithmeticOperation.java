package com.stockholdergame.server.gamecore.model.math;

/**
 *
 */
public enum ArithmeticOperation {

    ADDITION {
        @Override
        public int execute(int value1, int value2) {
            return value1 + value2;
        }
    },

    SUBTRACTION {
        @Override
        public int execute(int value1, int value2) {
            return value1 - value2;
        }
    },

    MULTIPLICATION {
        @Override
        public int execute(int value1, int value2) {
            return value1 * value2;
        }
    },

    DIVISION {
        @Override
        public int execute(int value1, int value2) {
            return value1 / value2;
        }
    };

    public abstract int execute(int value1, int value2);
}
