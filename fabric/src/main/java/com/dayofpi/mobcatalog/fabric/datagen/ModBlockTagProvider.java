package com.dayofpi.mobcatalog.fabric.datagen;

import com.dayofpi.mobcatalog.util.ModTags;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagProvider;
import net.minecraft.core.HolderLookup;
import net.minecraft.world.level.block.Blocks;

import java.util.concurrent.CompletableFuture;

public class ModBlockTagProvider extends FabricTagProvider.BlockTagProvider {
    public ModBlockTagProvider(FabricDataOutput output, CompletableFuture<HolderLookup.Provider> registriesFuture) {
        super(output, registriesFuture);
    }

    @Override
    protected void addTags(HolderLookup.Provider provider) {
        getOrCreateTagBuilder(ModTags.Blocks.CRABS_SPAWNABLE_ON).add(Blocks.SAND, Blocks.GRASS_BLOCK, Blocks.MUD, Blocks.MANGROVE_ROOTS, Blocks.MUDDY_MANGROVE_ROOTS);
    }
}
