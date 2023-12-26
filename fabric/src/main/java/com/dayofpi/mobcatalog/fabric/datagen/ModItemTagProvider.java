package com.dayofpi.mobcatalog.fabric.datagen;

import com.dayofpi.mobcatalog.item.ModItems;
import com.dayofpi.mobcatalog.util.ModTags;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagProvider;
import net.minecraft.core.HolderLookup;
import net.minecraft.world.item.Items;

import java.util.concurrent.CompletableFuture;

public class ModItemTagProvider extends FabricTagProvider.ItemTagProvider {
    public ModItemTagProvider(FabricDataOutput output, CompletableFuture<HolderLookup.Provider> completableFuture) {
        super(output, completableFuture, new ModBlockTagProvider(output, completableFuture));
    }

    @Override
    protected void addTags(HolderLookup.Provider provider) {
        getOrCreateTagBuilder(ModTags.Items.CAPYBARA_FOOD).add(Items.MELON_SLICE);
        getOrCreateTagBuilder(ModTags.Items.CRAB_FOOD).add(Items.KELP);
        getOrCreateTagBuilder(ModTags.Items.PENGUIN_FOOD).add(ModItems.CRAB_LEG.get());
        getOrCreateTagBuilder(ModTags.Items.PENGUIN_ALTERNATE_FOOD).add(Items.COD, Items.SALMON, Items.TROPICAL_FISH);
        getOrCreateTagBuilder(ModTags.Items.STONEMAW_FOOD).add(Items.DIAMOND);
    }
}
