package com.dayofpi.mobcatalog.forge;

public class ModExpectPlatformImpl {
    public static boolean shouldCrabsSpawn() {
        return ModForgeConfigs.SPAWN_CRABS.get();
    }

    public static boolean shouldPenguinsSpawn() {
        return ModForgeConfigs.SPAWN_PENGUINS.get();
    }
}
