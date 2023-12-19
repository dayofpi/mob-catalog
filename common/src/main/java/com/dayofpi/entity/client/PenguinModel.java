package com.dayofpi.entity.client;

import com.dayofpi.MobCatalog;
import com.dayofpi.entity.custom.PenguinEntity;
import mod.azure.azurelib.constant.DataTickets;
import mod.azure.azurelib.core.animatable.model.CoreGeoBone;
import mod.azure.azurelib.core.animation.AnimationState;
import mod.azure.azurelib.model.DefaultedEntityGeoModel;
import mod.azure.azurelib.model.data.EntityModelData;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;

public class PenguinModel extends DefaultedEntityGeoModel<PenguinEntity> {
    public PenguinModel() {
        super(new ResourceLocation(MobCatalog.MOD_ID, "penguin"));
    }

    @Override
    public void setCustomAnimations(PenguinEntity animatable, long instanceId, AnimationState<PenguinEntity> animationState) {
        CoreGeoBone head = getAnimationProcessor().getBone("head");

        if (head != null) {
            EntityModelData entityData = animationState.getData(DataTickets.ENTITY_MODEL_DATA);

            if (!animatable.isInWaterOrBubble()) {
                head.setRotX(entityData.headPitch() * Mth.DEG_TO_RAD);
                head.setRotY(entityData.netHeadYaw() * Mth.DEG_TO_RAD);
            }

            if (entityData.isChild()) {
                head.setScaleX(2.0F);
                head.setScaleY(2.0F);
                head.setScaleZ(2.0F);
            } else {
                head.setScaleX(1.0F);
                head.setScaleY(1.0F);
                head.setScaleZ(1.0F);
            }
        }
    }
}
