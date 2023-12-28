package com.dayofpi.mobcatalog;

import com.dayofpi.mobcatalog.entity.ModEntityTypes;
import com.dayofpi.mobcatalog.entity.client.*;
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
import net.minecraft.tags.BiomeTags;
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
        ModEntityTypes.ENTITY_TYPES.register();
        ModEntityTypes.registerAttributes();
        ModItems.ITEMS.register();
        ModSoundEvents.SOUND_EVENTS.register();
        BiomeModifications.addProperties((biomeContext, mutable) -> {
            if (ModConfigs.SPAWN_CAPYBARAS.get() && biomeContext.hasTag(ModTags.Biomes.SPAWNS_CAPYBARAS)) {
                mutable.getSpawnProperties().addSpawn(MobCategory.CREATURE, new MobSpawnSettings.SpawnerData(ModEntityTypes.CAPYBARA.get(), 10, 4, 4));
            }
            if (ModConfigs.SPAWN_CRABS.get() && biomeContext.hasTag(ModTags.Biomes.SPAWNS_CRABS)) {
                mutable.getSpawnProperties().addSpawn(MobCategory.CREATURE, new MobSpawnSettings.SpawnerData(ModEntityTypes.CRAB.get(), 8, 2, 5));
            }
            if (ModConfigs.SPAWN_PENGUINS.get() && biomeContext.hasTag(ModTags.Biomes.SPAWNS_PENGUINS)) {
                mutable.getSpawnProperties().addSpawn(MobCategory.CREATURE, new MobSpawnSettings.SpawnerData(ModEntityTypes.PENGUIN.get(), 5, 2, 5));
            }
            if (ModConfigs.SPAWN_SLOTHS.get() && biomeContext.hasTag(ModTags.Biomes.SPAWNS_SLOTHS)) {
                mutable.getSpawnProperties().addSpawn(MobCategory.CREATURE, new MobSpawnSettings.SpawnerData(ModEntityTypes.SLOTH.get(), 10, 2, 4));
            }
            if (ModConfigs.SPAWN_STONEMAWS.get() && biomeContext.hasTag(BiomeTags.IS_OVERWORLD) && !biomeContext.hasTag(ModTags.Biomes.NO_REGULAR_MOB_SPAWNS)) {
                mutable.getSpawnProperties().addSpawn(MobCategory.MONSTER, new MobSpawnSettings.SpawnerData(ModEntityTypes.STONEMAW.get(), 20, 1, 2));
            }
        });
        EnvExecutor.runInEnv(Env.CLIENT, () -> MobCatalog::initClient);
    }

    private static void initClient() {
        EntityRendererRegistry.register(ModEntityTypes.CAPYBARA, CapybaraRenderer::new);
        EntityRendererRegistry.register(ModEntityTypes.CRAB, CrabRenderer::new);
        EntityRendererRegistry.register(ModEntityTypes.PENGUIN, PenguinRenderer::new);
        EntityRendererRegistry.register(ModEntityTypes.SLOTH, SlothRenderer::new);
        EntityRendererRegistry.register(ModEntityTypes.STONEMAW, StonemawRenderer::new);
    }
}
