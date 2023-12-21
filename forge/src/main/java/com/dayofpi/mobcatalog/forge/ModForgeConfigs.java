package com.dayofpi.mobcatalog.forge;

import net.minecraftforge.common.ForgeConfigSpec;

public class ModForgeConfigs {
    public static final ForgeConfigSpec.Builder BUILDER = new ForgeConfigSpec.Builder();
    public static final ForgeConfigSpec SPEC;

    public static final ForgeConfigSpec.ConfigValue<Boolean> SPAWN_CRABS;
    public static final ForgeConfigSpec.ConfigValue<Boolean> SPAWN_PENGUINS;

    static {
        BUILDER.push("Common Configs for Mob Catalog");

        SPAWN_CRABS = BUILDER.define("spawn_crabs", true);
        SPAWN_PENGUINS = BUILDER.define("spawn_penguins", true);

        BUILDER.pop();
        SPEC = BUILDER.build();
    }
}
