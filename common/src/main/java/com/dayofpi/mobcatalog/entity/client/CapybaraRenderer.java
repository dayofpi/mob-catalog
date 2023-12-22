package com.dayofpi.mobcatalog.entity.client;

import com.dayofpi.mobcatalog.entity.custom.CapybaraEntity;
import com.mojang.blaze3d.vertex.PoseStack;
import mod.azure.azurelib.cache.object.BakedGeoModel;
import mod.azure.azurelib.renderer.GeoEntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;

public class CapybaraRenderer extends GeoEntityRenderer<CapybaraEntity> {
    public CapybaraRenderer(EntityRendererProvider.Context renderManager) {
        super(renderManager, new CapybaraModel());
        this.shadowRadius = 0.7F;
    }

    @Override
    public void scaleModelForRender(float widthScale, float heightScale, PoseStack poseStack, CapybaraEntity animatable, BakedGeoModel model, boolean isReRender, float partialTick, int packedLight, int packedOverlay) {
        if (animatable.isBaby()) {
            super.scaleModelForRender(0.5F, 0.5F, poseStack, animatable, model, isReRender, partialTick, packedLight, packedOverlay);
        } else super.scaleModelForRender(widthScale, heightScale, poseStack, animatable, model, isReRender, partialTick, packedLight, packedOverlay);
    }
}
