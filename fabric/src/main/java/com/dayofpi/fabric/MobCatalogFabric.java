package com.dayofpi.fabric;

import com.dayofpi.MobCatalog;
import com.dayofpi.entity.ModEntityTypes;
import com.dayofpi.entity.custom.CrabEntity;
import com.dayofpi.entity.custom.PenguinEntity;
import net.fabricmc.api.ModInitializer;
import net.minecraft.world.entity.SpawnPlacements;
import net.minecraft.world.level.levelgen.Heightmap;

public class MobCatalogFabric implements ModInitializer {
    public static final com.dayofpi.fabric.ModFabricConfigs CONFIG = com.dayofpi.fabric.ModFabricConfigs.createAndLoad();

    @Override
    public void onInitialize() {
        MobCatalog.init();
        SpawnPlacements.register(ModEntityTypes.CRAB.get(), SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, CrabEntity::checkCrabSpawnRules);
        SpawnPlacements.register(ModEntityTypes.PENGUIN.get(), SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, PenguinEntity::checkPenguinSpawnRules);
    }
}
