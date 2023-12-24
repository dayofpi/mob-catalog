package com.dayofpi.mobcatalog.entity.client;

import com.dayofpi.mobcatalog.entity.custom.StonemawEntity;
import mod.azure.azurelib.renderer.GeoEntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;

public class StonemawRenderer extends GeoEntityRenderer<StonemawEntity> {
    public StonemawRenderer(EntityRendererProvider.Context renderManager) {
        super(renderManager, new StonemawModel());
        this.shadowRadius = 0.5F;
    }
}
