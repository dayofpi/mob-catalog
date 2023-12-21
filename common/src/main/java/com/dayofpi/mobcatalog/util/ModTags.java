package com.dayofpi.mobcatalog.util;

import com.dayofpi.mobcatalog.MobCatalog;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.block.Block;

public class ModTags {
    public static class Blocks {
        public static final TagKey<Block> CRABS_SPAWNABLE_ON = tag("crabs_spawnable_on");

        private static TagKey<Block> tag(String name) {
            return TagKey.create(Registries.BLOCK, new ResourceLocation(MobCatalog.MOD_ID, name));
        }
    }

    public static class Biomes {
        public static final TagKey<Biome> SPAWNS_CRABS = tag("spawns_crabs");
        public static final TagKey<Biome> SPAWNS_PENGUINS = tag("spawns_penguins");

        private static TagKey<Biome> tag(String name) {
            return TagKey.create(Registries.BIOME, new ResourceLocation(MobCatalog.MOD_ID, name));
        }
    }
}
