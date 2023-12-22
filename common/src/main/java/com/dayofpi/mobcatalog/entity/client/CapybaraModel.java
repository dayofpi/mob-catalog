package com.dayofpi.mobcatalog.entity.client;

import com.dayofpi.mobcatalog.MobCatalog;
import com.dayofpi.mobcatalog.entity.custom.CapybaraEntity;
import mod.azure.azurelib.constant.DataTickets;
import mod.azure.azurelib.core.animatable.model.CoreGeoBone;
import mod.azure.azurelib.core.animation.AnimationState;
import mod.azure.azurelib.model.DefaultedEntityGeoModel;
import mod.azure.azurelib.model.data.EntityModelData;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;

public class CapybaraModel extends DefaultedEntityGeoModel<CapybaraEntity> {
    public CapybaraModel() {
        super(new ResourceLocation(MobCatalog.MOD_ID, "capybara"));
    }

    @Override
    public void setCustomAnimations(CapybaraEntity animatable, long instanceId, AnimationState<CapybaraEntity> animationState) {
        CoreGeoBone head = getAnimationProcessor().getBone("head");

        if (head != null) {
            EntityModelData entityData = animationState.getData(DataTickets.ENTITY_MODEL_DATA);

            head.setRotX(entityData.headPitch() * Mth.DEG_TO_RAD);
            head.setRotY(entityData.netHeadYaw() * Mth.DEG_TO_RAD);

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
