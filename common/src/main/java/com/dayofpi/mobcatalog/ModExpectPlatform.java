package com.dayofpi.mobcatalog;

import dev.architectury.injectables.annotations.ExpectPlatform;

public class ModExpectPlatform {
    @ExpectPlatform
    public static boolean shouldCrabsSpawn() {
        throw new AssertionError();
    }

    @ExpectPlatform
    public static boolean shouldPenguinsSpawn() {
        throw new AssertionError();
    }
}
