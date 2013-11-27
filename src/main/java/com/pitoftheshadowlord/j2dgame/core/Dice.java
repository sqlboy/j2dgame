package com.pitoftheshadowlord.j2dgame.core;

import java.util.Random;

public class Dice {

    private static final Random RANDOM = new Random();

    public static final int roll(int max) {
        return RANDOM.nextInt(max) + 1;
    }
}
