package dev.scalebound.shared.commons;

import java.util.Random;

public class MathUtils
{
    private static final Random RAN = new Random();

    public static Random getRandom()
    {
        return RAN;
    }
}
