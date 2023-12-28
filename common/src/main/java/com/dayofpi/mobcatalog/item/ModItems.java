package com.dayofpi.mobcatalog.item;

import com.dayofpi.mobcatalog.MobCatalog;
import com.dayofpi.mobcatalog.entity.ModEntityTypes;
import dev.architectury.core.item.ArchitecturyMobBucketItem;
import dev.architectury.core.item.ArchitecturySpawnEggItem;
import dev.architectury.registry.registries.DeferredRegister;
import dev.architectury.registry.registries.RegistrySupplier;
import net.minecraft.core.registries.Registries;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.food.Foods;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.material.Fluids;

public class ModItems {
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(MobCatalog.MOD_ID, Registries.ITEM);
    public static final RegistrySupplier<Item> CRAB_LEG = ITEMS.register("crab_leg", () -> new Item(new Item.Properties().food(Foods.COD).arch$tab(MobCatalog.MAIN)));
    public static final RegistrySupplier<Item> COOKED_CRAB_LEG = ITEMS.register("cooked_crab_leg", () -> new Item(new Item.Properties().food(Foods.COOKED_COD).arch$tab(MobCatalog.MAIN)));
    public static final RegistrySupplier<Item> CRAB_CLAW = ITEMS.register("crab_claw", () -> new Item(new Item.Properties().stacksTo(1).arch$tab(MobCatalog.MAIN)));
    public static final RegistrySupplier<Item> CRAB_BUCKET = ITEMS.register("crab_bucket", () -> new ArchitecturyMobBucketItem(ModEntityTypes.CRAB, () -> Fluids.WATER, () -> SoundEvents.BUCKET_EMPTY_FISH, new Item.Properties().stacksTo(1).arch$tab(MobCatalog.MAIN)));
    public static final RegistrySupplier<Item> CAPYBARA_SPAWN_EGG = ITEMS.register("capybara_spawn_egg", () -> new ArchitecturySpawnEggItem(ModEntityTypes.CAPYBARA, 12873501, 6569761, new Item.Properties().arch$tab(MobCatalog.MAIN)));
    public static final RegistrySupplier<Item> CRAB_SPAWN_EGG = ITEMS.register("crab_spawn_egg", () -> new ArchitecturySpawnEggItem(ModEntityTypes.CRAB, 14440491, 3561107, new Item.Properties().arch$tab(MobCatalog.MAIN)));
    public static final RegistrySupplier<Item> PENGUIN_SPAWN_EGG = ITEMS.register("penguin_spawn_egg", () -> new ArchitecturySpawnEggItem(ModEntityTypes.PENGUIN, 2829362, 12698034, new Item.Properties().arch$tab(MobCatalog.MAIN)));
    public static final RegistrySupplier<Item> SLOTH_SPAWN_EGG = ITEMS.register("sloth_spawn_egg", () -> new ArchitecturySpawnEggItem(ModEntityTypes.SLOTH, 6702371, 14333290, new Item.Properties().arch$tab(MobCatalog.MAIN)));
    public static final RegistrySupplier<Item> STONEMAW_SPAWN_EGG = ITEMS.register("stonemaw_spawn_egg", () -> new ArchitecturySpawnEggItem(ModEntityTypes.STONEMAW, 9408399, 8037416, new Item.Properties().arch$tab(MobCatalog.MAIN)));
}
