package com.dayofpi.mobcatalog.fabric.datagen;

import com.dayofpi.mobcatalog.util.ModTags;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagProvider;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.Biomes;

import java.util.concurrent.CompletableFuture;

public class ModBiomeTagProvider extends FabricTagProvider<Biome> {
    public ModBiomeTagProvider(FabricDataOutput output, CompletableFuture<HolderLookup.Provider> registriesFuture) {
        super(output, Registries.BIOME, registriesFuture);
    }

    @Override
    protected void addTags(HolderLookup.Provider provider) {
        getOrCreateTagBuilder(ModTags.Biomes.SPAWNS_CAPYBARAS).addOptional(Biomes.SWAMP).addOptional(Biomes.JUNGLE).addOptional(Biomes.SPARSE_JUNGLE);
        getOrCreateTagBuilder(ModTags.Biomes.SPAWNS_CRABS).addOptional(Biomes.BEACH).addOptional(Biomes.MANGROVE_SWAMP);
        getOrCreateTagBuilder(ModTags.Biomes.SPAWNS_PENGUINS).addOptional(Biomes.STONY_SHORE);
        getOrCreateTagBuilder(ModTags.Biomes.SPAWNS_SLOTHS).addOptional(Biomes.JUNGLE);
        getOrCreateTagBuilder(ModTags.Biomes.NO_REGULAR_MOB_SPAWNS).addOptional(Biomes.DEEP_DARK).addOptional(Biomes.MUSHROOM_FIELDS);
    }
}
