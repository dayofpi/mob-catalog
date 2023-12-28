package com.dayofpi.mobcatalog.util;

import com.dayofpi.mobcatalog.MobCatalog;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.block.Block;

public class ModTags {
    public static class Blocks {
        public static final TagKey<Block> CRABS_SPAWNABLE_ON = tag("crabs_spawnable_on");

        private static TagKey<Block> tag(String name) {
            return TagKey.create(Registries.BLOCK, new ResourceLocation(MobCatalog.MOD_ID, name));
        }
    }

    public static class Items {
        public static final TagKey<Item> CAPYBARA_FOOD = tag("capybara_food");
        public static final TagKey<Item> CRAB_FOOD = tag("crab_food");
        public static final TagKey<Item> PENGUIN_FOOD = tag("penguin_food");
        public static final TagKey<Item> PENGUIN_ALTERNATE_FOOD = tag("penguin_alternate_food");
        public static final TagKey<Item> STONEMAW_FOOD = tag("stonemaw_food");

        private static TagKey<Item> tag(String name) {
            return TagKey.create(Registries.ITEM, new ResourceLocation(MobCatalog.MOD_ID, name));
        }
    }

    public static class Biomes {
        public static final TagKey<Biome> SPAWNS_CAPYBARAS = tag("spawns_capybaras");
        public static final TagKey<Biome> SPAWNS_CRABS = tag("spawns_crabs");
        public static final TagKey<Biome> SPAWNS_PENGUINS = tag("spawns_penguins");
        public static final TagKey<Biome> SPAWNS_SLOTHS = tag("spawns_sloths");
        public static final TagKey<Biome> NO_REGULAR_MOB_SPAWNS = tag("no_regular_mob_spawns");

        private static TagKey<Biome> tag(String name) {
            return TagKey.create(Registries.BIOME, new ResourceLocation(MobCatalog.MOD_ID, name));
        }
    }
}
