package com.dayofpi.mobcatalog.fabric.datagen;

import com.dayofpi.mobcatalog.MobCatalog;
import com.dayofpi.mobcatalog.item.ModItems;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricRecipeProvider;
import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.data.recipes.RecipeCategory;
import net.minecraft.data.recipes.SimpleCookingRecipeBuilder;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.crafting.Ingredient;

import java.util.function.Consumer;

public class ModRecipeProvider extends FabricRecipeProvider {
    public ModRecipeProvider(FabricDataOutput output) {
        super(output);
    }

    @Override
    public void buildRecipes(Consumer<FinishedRecipe> exporter) {
        SimpleCookingRecipeBuilder.smelting(Ingredient.of(ModItems.CRAB_LEG.get()), RecipeCategory.FOOD, ModItems.COOKED_CRAB_LEG.get(), 0.35F, 200).unlockedBy("has_crab_leg", has(ModItems.CRAB_LEG.get())).save(exporter);
        SimpleCookingRecipeBuilder.smoking(Ingredient.of(ModItems.CRAB_LEG.get()), RecipeCategory.FOOD, ModItems.COOKED_CRAB_LEG.get(), 0.35F, 100).unlockedBy("has_crab_leg", has(ModItems.CRAB_LEG.get())).save(exporter, new ResourceLocation(MobCatalog.MOD_ID, "cooked_crab_leg_from_smoking"));
        SimpleCookingRecipeBuilder.campfireCooking(Ingredient.of(ModItems.CRAB_LEG.get()), RecipeCategory.FOOD, ModItems.COOKED_CRAB_LEG.get(), 0.35F, 600).unlockedBy("has_crab_leg", has(ModItems.CRAB_LEG.get())).save(exporter, new ResourceLocation(MobCatalog.MOD_ID, "cooked_crab_leg_from_campfire_cooking"));;
    }
}
