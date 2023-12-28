package com.dayofpi.mobcatalog;

import net.minecraftforge.common.ForgeConfigSpec;

public class ModConfigs {
    public static final ForgeConfigSpec.Builder BUILDER = new ForgeConfigSpec.Builder();
    public static final ForgeConfigSpec SPEC;

    public static final ForgeConfigSpec.ConfigValue<Float> CRAB_CLAW_REACH_MULTIPLIER;
    public static final ForgeConfigSpec.ConfigValue<Boolean> CRAB_CLAW_WORKS_IN_OFFHAND;
    public static final ForgeConfigSpec.ConfigValue<Boolean> SPAWN_CAPYBARAS;
    public static final ForgeConfigSpec.ConfigValue<Boolean> SPAWN_CRABS;
    public static final ForgeConfigSpec.ConfigValue<Boolean> SPAWN_PENGUINS;
    public static final ForgeConfigSpec.ConfigValue<Boolean> SPAWN_SLOTHS;
    public static final ForgeConfigSpec.ConfigValue<Boolean> SPAWN_STONEMAWS;

    static {
        BUILDER.push("Common Configs for Mob Catalog");

        CRAB_CLAW_REACH_MULTIPLIER = BUILDER.define("crab_claw_reach_multiplier", 2.0F);
        CRAB_CLAW_WORKS_IN_OFFHAND = BUILDER.define("crab_claw_works_in_offhand", true);
        SPAWN_CAPYBARAS = BUILDER.define("spawn_capybaras", true);
        SPAWN_CRABS = BUILDER.define("spawn_crabs", true);
        SPAWN_PENGUINS = BUILDER.define("spawn_penguins", true);
        SPAWN_SLOTHS = BUILDER.define("spawn_sloths", true);
        SPAWN_STONEMAWS = BUILDER.define("spawn_stonemaws", true);

        BUILDER.pop();
        SPEC = BUILDER.build();
    }
}
