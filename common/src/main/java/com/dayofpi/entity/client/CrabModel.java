package com.dayofpi.entity.client;

import com.dayofpi.MobCatalog;
import com.dayofpi.entity.custom.CrabEntity;
import mod.azure.azurelib.model.DefaultedEntityGeoModel;
import net.minecraft.resources.ResourceLocation;

public class CrabModel extends DefaultedEntityGeoModel<CrabEntity> {
    public CrabModel() {
        super(new ResourceLocation(MobCatalog.MOD_ID, "crab"));
    }

    @Override
    public ResourceLocation getTextureResource(CrabEntity animatable) {
        if (animatable.getVariant().equals(CrabEntity.CrabVariant.BLUE)) {
            return new ResourceLocation(MobCatalog.MOD_ID, "textures/entity/crab/blue.png");
        }
        return new ResourceLocation(MobCatalog.MOD_ID, "textures/entity/crab/red.png");
    }
}
