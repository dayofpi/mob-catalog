package com.dayofpi.mobcatalog.fabric.mixin;

import com.dayofpi.mobcatalog.item.ModItems;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import virtuoel.pehkui.api.ScaleTypes;

@Mixin(LivingEntity.class)
public abstract class LivingEntityMixin extends Entity {
    public LivingEntityMixin(EntityType<?> entityType, Level level) {
        super(entityType, level);
    }

    @Shadow public abstract ItemStack getMainHandItem();

    @Shadow public abstract ItemStack getOffhandItem();

    @Inject(at=@At("HEAD"), method = "equipmentHasChanged")
    private void equipmentHasChanged(ItemStack itemStack, ItemStack itemStack2, CallbackInfoReturnable<Boolean> cir) {
        if (this.getMainHandItem().is(ModItems.CRAB_CLAW.get()) || this.getOffhandItem().is(ModItems.CRAB_CLAW.get())) {
            ScaleTypes.BLOCK_REACH.getScaleData(this).setScale(2.0F);
        } else {
            ScaleTypes.BLOCK_REACH.getScaleData(this).setScale(1.0F);
        }
    }
}
