package com.dayofpi.mobcatalog;

import com.dayofpi.mobcatalog.entity.ModEntityTypes;
import com.dayofpi.mobcatalog.entity.client.CrabRenderer;
import com.dayofpi.mobcatalog.entity.client.PenguinRenderer;
import com.dayofpi.mobcatalog.item.ModItems;
import com.dayofpi.mobcatalog.sound.ModSoundEvents;
import com.dayofpi.mobcatalog.util.ModTags;
import dev.architectury.registry.CreativeTabRegistry;
import dev.architectury.registry.client.level.entity.EntityRendererRegistry;
import dev.architectury.registry.level.biome.BiomeModifications;
import dev.architectury.registry.registries.DeferredRegister;
import dev.architectury.registry.registries.RegistrySupplier;
import dev.architectury.utils.Env;
import dev.architectury.utils.EnvExecutor;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.biome.MobSpawnSettings;

public class MobCatalog {
    public static final String MOD_ID = "mobcatalog";

    public static final DeferredRegister<CreativeModeTab> TABS = DeferredRegister.create(MOD_ID, Registries.CREATIVE_MODE_TAB);

    public static final RegistrySupplier<CreativeModeTab> MAIN = TABS.register("main", () ->
            CreativeTabRegistry.create(Component.translatable("itemGroup.mobcatalog"),
                    () -> new ItemStack(ModItems.CRAB_CLAW.get())));

    public static void init() {
        TABS.register();
        ModItems.ITEMS.register();
        ModEntityTypes.ENTITY_TYPES.register();
        ModEntityTypes.registerAttributes();
        ModSoundEvents.SOUND_EVENTS.register();
        BiomeModifications.addProperties((biomeContext, mutable) -> {
            if (ModExpectPlatform.shouldCrabsSpawn() && biomeContext.hasTag(ModTags.Biomes.SPAWNS_CRABS)) {
                mutable.getSpawnProperties().addSpawn(MobCategory.CREATURE, new MobSpawnSettings.SpawnerData(ModEntityTypes.CRAB.get(), 5, 2, 5));
            }
            if (ModExpectPlatform.shouldPenguinsSpawn() && biomeContext.hasTag(ModTags.Biomes.SPAWNS_PENGUINS)) {
                mutable.getSpawnProperties().addSpawn(MobCategory.CREATURE, new MobSpawnSettings.SpawnerData(ModEntityTypes.PENGUIN.get(), 5, 2, 5));
            }
        });
        EnvExecutor.runInEnv(Env.CLIENT, () -> MobCatalog::initClient);
    }

    private static void initClient() {
        EntityRendererRegistry.register(ModEntityTypes.CRAB, CrabRenderer::new);
        EntityRendererRegistry.register(ModEntityTypes.PENGUIN, PenguinRenderer::new);
    }
}
