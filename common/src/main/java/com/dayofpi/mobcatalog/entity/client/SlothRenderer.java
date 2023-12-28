package com.dayofpi.mobcatalog.entity.client;

import com.dayofpi.mobcatalog.entity.custom.SlothEntity;
import com.mojang.blaze3d.vertex.PoseStack;
import mod.azure.azurelib.cache.object.BakedGeoModel;
import mod.azure.azurelib.renderer.GeoEntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;

public class SlothRenderer extends GeoEntityRenderer<SlothEntity> {
    public SlothRenderer(EntityRendererProvider.Context renderManager) {
        super(renderManager, new SlothModel());
        this.shadowRadius = 0.6F;
    }

    @Override
    public void scaleModelForRender(float widthScale, float heightScale, PoseStack poseStack, SlothEntity animatable, BakedGeoModel model, boolean isReRender, float partialTick, int packedLight, int packedOverlay) {
        if (animatable.isBaby()) {
            super.scaleModelForRender(0.5F, 0.5F, poseStack, animatable, model, isReRender, partialTick, packedLight, packedOverlay);
        } else super.scaleModelForRender(widthScale, heightScale, poseStack, animatable, model, isReRender, partialTick, packedLight, packedOverlay);
    }
}
