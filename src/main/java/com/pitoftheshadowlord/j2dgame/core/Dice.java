package com.pitoftheshadowlord.j2dgame.core;

import java.util.Random;

public class Dice {

    private static final Random RANDOM = new Random();

    public static final int roll(int max) {
        return RANDOM.nextInt(max) + 1;
    }

    public static final int roll(int min, int max) {
        if (max - min + 1 <= 0) {
            return 1;
        }
        return RANDOM.nextInt(max - min + 1) + min;
    }
}
