package com.dayofpi.mobcatalog.entity.client;

import com.dayofpi.mobcatalog.MobCatalog;
import com.dayofpi.mobcatalog.entity.custom.SlothEntity;
import mod.azure.azurelib.constant.DataTickets;
import mod.azure.azurelib.core.animatable.model.CoreGeoBone;
import mod.azure.azurelib.core.animation.AnimationState;
import mod.azure.azurelib.model.DefaultedEntityGeoModel;
import mod.azure.azurelib.model.data.EntityModelData;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;

public class SlothModel extends DefaultedEntityGeoModel<SlothEntity> {
    public SlothModel() {
        super(new ResourceLocation(MobCatalog.MOD_ID, "sloth"));
    }

    @Override
    public ResourceLocation getTextureResource(SlothEntity animatable) {
        if (animatable.getVariant().equals(SlothEntity.SlothVariant.THREE_TOED)) {
            return new ResourceLocation(MobCatalog.MOD_ID, "textures/entity/sloth/three_toed.png");
        }
        return new ResourceLocation(MobCatalog.MOD_ID, "textures/entity/sloth/two_toed.png");
    }

    @Override
    public void setCustomAnimations(SlothEntity animatable, long instanceId, AnimationState<SlothEntity> animationState) {
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
