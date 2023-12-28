package com.dayofpi.mobcatalog.entity.custom;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.MoverType;
import net.minecraft.world.entity.ai.goal.FloatGoal;
import net.minecraft.world.entity.ai.navigation.AmphibiousPathNavigation;
import net.minecraft.world.entity.ai.navigation.PathNavigation;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;

public abstract class AmphibiousAnimal extends Animal {
    protected static final EntityDataAccessor<Integer> SWIM_TIME = SynchedEntityData.defineId(AmphibiousAnimal.class, EntityDataSerializers.INT);

    protected AmphibiousAnimal(EntityType<? extends Animal> entityType, Level level) {
        super(entityType, level);
    }

    @Override
    public float maxUpStep() {
        return 1.0F;
    }

    @Override
    protected PathNavigation createNavigation(Level level) {
        return new AmphibiousPathNavigation(this, level);
    }

    protected boolean canDive() {
        return false;
    }

    @Override
    public void travel(Vec3 vec3) {
        if (this.isControlledByLocalInstance() && this.isInWater()) {
            this.moveRelative(this.getSpeed(), vec3);
            this.move(MoverType.SELF, this.getDeltaMovement());
            this.setDeltaMovement(this.getDeltaMovement().scale(0.9));
        } else {
            super.travel(vec3);
        }
    }

    @Override
    protected void defineSynchedData() {
        super.defineSynchedData();
        this.entityData.define(SWIM_TIME, 0);
    }

    @Override
    public void addAdditionalSaveData(CompoundTag compoundTag) {
        super.addAdditionalSaveData(compoundTag);
        compoundTag.putInt("swim_time", this.getSwimTime());
    }

    @Override
    public void readAdditionalSaveData(CompoundTag compoundTag) {
        super.readAdditionalSaveData(compoundTag);
        this.setSwimTime(compoundTag.getInt("swim_time"));
    }

    public boolean isInSwimmingPose() {
        return this.getSwimTime() > 0;
    }

    private int getSwimTime() {
        return this.entityData.get(SWIM_TIME);
    }

    private void setSwimTime(int swimTime) {
        this.entityData.set(SWIM_TIME, swimTime);
    }

    @Override
    public void tick() {
        super.tick();
        if (this.isInWaterOrBubble()) {
            this.setSwimTime(40);
        } else {
            this.setSwimTime(this.getSwimTime() - 1);
        }
    }

    static class UnstuckGoal extends FloatGoal {
        private final Mob mob;

        public UnstuckGoal(Mob mob) {
            super(mob);
            this.mob = mob;
        }

        @Override
        public boolean canUse() {
            return super.canUse() && this.mob.getNavigation().isStuck();
        }
    }
}
