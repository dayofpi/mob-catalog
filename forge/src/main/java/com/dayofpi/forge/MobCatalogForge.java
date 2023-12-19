package com.dayofpi.forge;

import dev.architectury.platform.forge.EventBuses;
import com.dayofpi.MobCatalog;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

@Mod(MobCatalog.MOD_ID)
public class MobCatalogForge {
    public MobCatalogForge() {
        // Submit our event bus to let architectury register our content on the right time
        EventBuses.registerModEventBus(MobCatalog.MOD_ID, FMLJavaModLoadingContext.get().getModEventBus());
        MobCatalog.init();
        ModLoadingContext.get().registerConfig(ModConfig.Type.COMMON, ModForgeConfigs.SPEC, "mobcatalog-common.toml");
    }
}
