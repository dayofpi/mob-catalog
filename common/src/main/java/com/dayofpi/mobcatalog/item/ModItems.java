package com.dayofpi.mobcatalog.item;

import com.dayofpi.mobcatalog.MobCatalog;
import com.dayofpi.mobcatalog.entity.ModEntityTypes;
import dev.architectury.core.item.ArchitecturySpawnEggItem;
import dev.architectury.registry.registries.DeferredRegister;
import dev.architectury.registry.registries.RegistrySupplier;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.item.Item;

public class ModItems {
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(MobCatalog.MOD_ID, Registries.ITEM);
    public static final RegistrySupplier<Item> CRAB_CLAW = ITEMS.register("crab_claw", () -> new Item(new Item.Properties().stacksTo(1).arch$tab(MobCatalog.MAIN)));
    public static final RegistrySupplier<Item> CRAB_SPAWN_EGG = ITEMS.register("crab_spawn_egg", () -> new ArchitecturySpawnEggItem(ModEntityTypes.CRAB, 3561107, 14440491, new Item.Properties().arch$tab(MobCatalog.MAIN)));
    public static final RegistrySupplier<Item> PENGUIN_SPAWN_EGG = ITEMS.register("penguin_spawn_egg", () -> new ArchitecturySpawnEggItem(ModEntityTypes.PENGUIN, 2829362, 12698034, new Item.Properties().arch$tab(MobCatalog.MAIN)));
}
