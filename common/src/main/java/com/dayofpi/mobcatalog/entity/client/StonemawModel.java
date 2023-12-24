package com.dayofpi.mobcatalog.entity.client;

import com.dayofpi.mobcatalog.MobCatalog;
import com.dayofpi.mobcatalog.entity.custom.StonemawEntity;
import mod.azure.azurelib.model.DefaultedEntityGeoModel;
import net.minecraft.resources.ResourceLocation;

public class StonemawModel extends DefaultedEntityGeoModel<StonemawEntity> {
    public StonemawModel() {
        super(new ResourceLocation(MobCatalog.MOD_ID, "stonemaw"), true);
    }

    @Override
    public ResourceLocation getTextureResource(StonemawEntity animatable) {
        if (animatable.isTame()) {
            return new ResourceLocation(MobCatalog.MOD_ID, "textures/entity/stonemaw_tame.png");
        }
        return new ResourceLocation(MobCatalog.MOD_ID, "textures/entity/stonemaw.png");
    }
}
