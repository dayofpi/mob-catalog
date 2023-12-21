package com.dayofpi.mobcatalog.fabric.mixin;

import com.dayofpi.mobcatalog.fabric.MobCatalogFabricClient;
import com.dayofpi.mobcatalog.item.ModItems;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.renderer.ItemModelShaper;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(ItemRenderer.class)
public abstract class ItemRendererMixin {
    @Shadow @Final private ItemModelShaper itemModelShaper;

    @ModifyVariable(method = "render", at = @At("HEAD"), argsOnly = true)
    private BakedModel mobcatalog_guiModel(BakedModel model, ItemStack itemStack, ItemDisplayContext itemDisplayContext, boolean leftHanded, PoseStack poseStack, MultiBufferSource multiBufferSource, int light, int overlay, BakedModel bakedModel2) {
        if (itemStack.is(ModItems.CRAB_CLAW.get())) {
            if (itemDisplayContext == ItemDisplayContext.GUI || itemDisplayContext == ItemDisplayContext.GROUND || itemDisplayContext == ItemDisplayContext.FIXED) {
                return itemModelShaper.getModelManager().getModel(MobCatalogFabricClient.CRAB_CLAW_INVENTORY);
            }
        }
        return model;
    }

    @Inject(at=@At("HEAD"), method = "getModel", cancellable = true)
    private void getModel(ItemStack itemStack, Level level, LivingEntity livingEntity, int i, CallbackInfoReturnable<BakedModel> cir) {
        BakedModel bakedModel;
        if (itemStack.is(ModItems.CRAB_CLAW.get())) {
            bakedModel = this.itemModelShaper.getModelManager().getModel(MobCatalogFabricClient.CRAB_CLAW_MODEL);
            ClientLevel clientLevel = level instanceof ClientLevel ? (ClientLevel)level : null;
            BakedModel bakedModel2 = bakedModel.getOverrides().resolve(bakedModel, itemStack, clientLevel, livingEntity, i);
            cir.setReturnValue(bakedModel2 == null ? this.itemModelShaper.getModelManager().getMissingModel() : bakedModel2);
        }
    }
}
