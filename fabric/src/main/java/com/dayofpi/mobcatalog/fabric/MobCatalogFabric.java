package com.dayofpi.mobcatalog.fabric;

import com.dayofpi.mobcatalog.MobCatalog;
import com.dayofpi.mobcatalog.ModConfigs;
import com.dayofpi.mobcatalog.entity.ModEntityTypes;
import com.dayofpi.mobcatalog.entity.custom.CrabEntity;
import com.dayofpi.mobcatalog.entity.custom.PenguinEntity;
import com.dayofpi.mobcatalog.entity.custom.StonemawEntity;
import fuzs.forgeconfigapiport.api.config.v2.ForgeConfigRegistry;
import net.fabricmc.api.ModInitializer;
import net.minecraft.world.entity.SpawnPlacements;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraftforge.fml.config.ModConfig;

public class MobCatalogFabric implements ModInitializer {
    @Override
    public void onInitialize() {
        MobCatalog.init();
        ForgeConfigRegistry.INSTANCE.register(MobCatalog.MOD_ID, ModConfig.Type.COMMON, ModConfigs.SPEC);
        SpawnPlacements.register(ModEntityTypes.CAPYBARA.get(), SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, Animal::checkAnimalSpawnRules);
        SpawnPlacements.register(ModEntityTypes.CRAB.get(), SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, CrabEntity::checkCrabSpawnRules);
        SpawnPlacements.register(ModEntityTypes.PENGUIN.get(), SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, PenguinEntity::checkPenguinSpawnRules);
        SpawnPlacements.register(ModEntityTypes.SLOTH.get(), SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, Animal::checkAnimalSpawnRules);
        SpawnPlacements.register(ModEntityTypes.STONEMAW.get(), SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, StonemawEntity::checkStonemawSpawnRules);
    }
}
