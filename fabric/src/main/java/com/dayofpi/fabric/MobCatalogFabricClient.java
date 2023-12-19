package com.dayofpi.fabric;

import com.dayofpi.MobCatalog;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.impl.client.model.ModelLoadingRegistryImpl;
import net.minecraft.client.resources.model.ModelResourceLocation;

public class MobCatalogFabricClient implements ClientModInitializer {
    public static final ModelResourceLocation CRAB_CLAW_INVENTORY = new ModelResourceLocation(MobCatalog.MOD_ID, "crab_claw_inventory", "inventory");
    public static final ModelResourceLocation CRAB_CLAW_MODEL = new ModelResourceLocation(MobCatalog.MOD_ID, "crab_claw_model", "inventory");
    @Override
    public void onInitializeClient() {
        ModelLoadingRegistryImpl.INSTANCE.registerModelProvider((manager, out) -> {
            out.accept(CRAB_CLAW_INVENTORY);
            out.accept(CRAB_CLAW_MODEL);
        });
    }
}
