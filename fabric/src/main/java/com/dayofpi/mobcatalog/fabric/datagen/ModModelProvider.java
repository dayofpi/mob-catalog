package com.dayofpi.mobcatalog.fabric.datagen;

import com.dayofpi.mobcatalog.item.ModItems;
import dev.architectury.registry.registries.RegistrySupplier;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricModelProvider;
import net.minecraft.data.models.BlockModelGenerators;
import net.minecraft.data.models.ItemModelGenerators;
import net.minecraft.data.models.model.ModelLocationUtils;
import net.minecraft.data.models.model.ModelTemplates;
import net.minecraft.world.item.Item;

public class ModModelProvider extends FabricModelProvider {
    public ModModelProvider(FabricDataOutput output) {
        super(output);
    }

    @Override
    public void generateBlockStateModels(BlockModelGenerators blockStateModelGenerator) {
        generateSpawnEgg(ModItems.CAPYBARA_SPAWN_EGG, blockStateModelGenerator);
        generateSpawnEgg(ModItems.CRAB_SPAWN_EGG, blockStateModelGenerator);
        generateSpawnEgg(ModItems.PENGUIN_SPAWN_EGG, blockStateModelGenerator);
        generateSpawnEgg(ModItems.SLOTH_SPAWN_EGG, blockStateModelGenerator);
        generateSpawnEgg(ModItems.STONEMAW_SPAWN_EGG, blockStateModelGenerator);
    }

    @Override
    public void generateItemModels(ItemModelGenerators itemModelGenerator) {
        itemModelGenerator.generateFlatItem(ModItems.CRAB_LEG.get(), ModelTemplates.FLAT_ITEM);
        itemModelGenerator.generateFlatItem(ModItems.COOKED_CRAB_LEG.get(), ModelTemplates.FLAT_ITEM);
        itemModelGenerator.generateFlatItem(ModItems.CRAB_BUCKET.get(), ModelTemplates.FLAT_ITEM);
    }

    private static void generateSpawnEgg(RegistrySupplier<Item> itemRegistrySupplier, BlockModelGenerators blockModelGenerators) {
        blockModelGenerators.delegateItemModel(itemRegistrySupplier.get(), ModelLocationUtils.decorateItemModelLocation("template_spawn_egg"));
    }
}
