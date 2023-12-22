package com.dayofpi.mobcatalog.entity.custom;

import com.dayofpi.mobcatalog.entity.ModEntityTypes;
import com.dayofpi.mobcatalog.sound.ModSoundEvents;
import com.dayofpi.mobcatalog.util.ModTags;
import com.dayofpi.mobcatalog.util.ModUtil;
import mod.azure.azurelib.animatable.GeoEntity;
import mod.azure.azurelib.core.animatable.instance.AnimatableInstanceCache;
import mod.azure.azurelib.core.animation.AnimatableManager;
import mod.azure.azurelib.core.animation.AnimationController;
import mod.azure.azurelib.core.animation.RawAnimation;
import mod.azure.azurelib.core.object.PlayState;
import mod.azure.azurelib.util.AzureLibUtil;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.control.SmoothSwimmingMoveControl;
import net.minecraft.world.entity.ai.goal.*;
import net.minecraft.world.entity.ai.navigation.AmphibiousPathNavigation;
import net.minecraft.world.entity.ai.navigation.PathNavigation;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.pathfinder.BlockPathTypes;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.Nullable;

public class PenguinEntity extends Animal implements GeoEntity {
    private static final EntityDataAccessor<Integer> SWIM_TIME = SynchedEntityData.defineId(PenguinEntity.class, EntityDataSerializers.INT);
    private final AnimatableInstanceCache cache = AzureLibUtil.createInstanceCache(this);

    public PenguinEntity(EntityType<? extends Animal> entityType, Level level) {
        super(entityType, level);
        this.setPathfindingMalus(BlockPathTypes.WATER, 0.0F);
        this.moveControl = new SmoothSwimmingMoveControl(this, 85, 10, 0.12F, 1.0F, true);
    }

    public static AttributeSupplier.Builder createAttributes() {
        return Mob.createMobAttributes().add(Attributes.MAX_HEALTH, 8.0).add(Attributes.MOVEMENT_SPEED, 0.2);
    }

    @Override
    public float maxUpStep() {
        return 1.0F;
    }

    @Override
    protected PathNavigation createNavigation(Level level) {
        return new AmphibiousPathNavigation(this, level);
    }

    public static boolean checkPenguinSpawnRules(EntityType<? extends Animal> entityType, LevelAccessor levelAccessor, MobSpawnType mobSpawnType, BlockPos blockPos, RandomSource randomSource) {
        return levelAccessor.getBlockState(blockPos.below()).is(Blocks.STONE) && isBrightEnoughToSpawn(levelAccessor, blockPos);
    }

    @Override
    public float getWalkTargetValue(BlockPos blockPos, LevelReader levelReader) {
        return levelReader.getBlockState(blockPos.below()).is(Blocks.STONE) ? 10.0F : levelReader.getPathfindingCostFromLightLevels(blockPos);
    }

    @Override
    protected void registerGoals() {
        this.goalSelector.addGoal(0, new UnstuckGoal(this));
        this.goalSelector.addGoal(1, new BreathAirGoal(this));
        this.goalSelector.addGoal(2, new PanicGoal(this, 1.4));
        this.goalSelector.addGoal(3, new BreedGoal(this, 1.0));
        this.goalSelector.addGoal(4, new TemptGoal(this, 1.0, Ingredient.of(ModTags.Items.PENGUIN_FOOD), false));
        this.goalSelector.addGoal(5, new FollowParentGoal(this, 1.1));
        this.goalSelector.addGoal(6, new RandomStrollGoal(this, 1.0));
        this.goalSelector.addGoal(6, new MoveInWaterGoal(this, 2.0, 10));
        this.goalSelector.addGoal(7, new LookAtPlayerGoal(this, Player.class, 6.0F));
        this.goalSelector.addGoal(8, new RandomLookAroundGoal(this));
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
    public boolean isFood(ItemStack itemStack) {
        return itemStack.is(ModTags.Items.PENGUIN_FOOD);
    }

    @Override
    protected void usePlayerItem(Player player, InteractionHand interactionHand, ItemStack itemStack) {
        if (this.isFood(itemStack)) {
            this.playSound(ModSoundEvents.PENGUIN_EAT.get(), 1.0F, 1.0F);
        }

        super.usePlayerItem(player, interactionHand, itemStack);
    }

    @Override
    public EntityDimensions getDimensions(Pose pose) {
        if (this.isSwimmingOrSliding()) {
            return EntityDimensions.fixed(0.65F, 0.6F);
        }
        return super.getDimensions(pose);
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

    public boolean isSwimmingOrSliding() {
        return this.getSwimTime() > 0;
    }

    private int getSwimTime() {
        return this.entityData.get(SWIM_TIME);
    }

    private void setSwimTime(int swimTime) {
        this.entityData.set(SWIM_TIME, swimTime);
    }

    @Override
    public void onSyncedDataUpdated(EntityDataAccessor<?> entityDataAccessor) {
        if (SWIM_TIME.equals(entityDataAccessor)) {
            this.refreshDimensions();
        }
        super.onSyncedDataUpdated(entityDataAccessor);
    }

    @Nullable
    @Override
    protected SoundEvent getAmbientSound() {
        return ModSoundEvents.PENGUIN_AMBIENT.get();
    }

    @Nullable
    @Override
    protected SoundEvent getHurtSound(DamageSource damageSource) {
        return ModSoundEvents.PENGUIN_HURT.get();
    }

    @Nullable
    @Override
    protected SoundEvent getDeathSound() {
        return ModSoundEvents.PENGUIN_DEATH.get();
    }

    protected void playStepSound(BlockPos blockPos, BlockState blockState) {
        if (!this.isSwimmingOrSliding()) {
            this.playSound(ModSoundEvents.PENGUIN_STEP.get(), 0.15F, 1.0F);
        }
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

    @Override
    public void registerControllers(AnimatableManager.ControllerRegistrar controllers) {
        controllers.add(new AnimationController<>(this, "main", 10, event -> {
            event.setControllerSpeed(ModUtil.getAnimationWalkSpeed(this, 1.5F));
            if (this.isSwimmingOrSliding())
                return event.setAndContinue(RawAnimation.begin().thenLoop("swim"));
            else if (event.isMoving())
                return event.setAndContinue(RawAnimation.begin().thenLoop("waddle"));
            return PlayState.STOP;
        }));
    }

    @Override
    public AnimatableInstanceCache getAnimatableInstanceCache() {
        return cache;
    }

    @Nullable
    @Override
    public AgeableMob getBreedOffspring(ServerLevel serverLevel, AgeableMob ageableMob) {
        return ModEntityTypes.PENGUIN.get().create(serverLevel);
    }

    static class UnstuckGoal extends FloatGoal {
        private final Mob mob;

        public UnstuckGoal(Mob mob) {
            super(mob);
            this.mob = mob;
        }

        @Override
        public boolean canUse() {
            return super.canUse() && mob.getNavigation().isStuck();
        }
    }

    static class MoveInWaterGoal extends RandomSwimmingGoal {
        public MoveInWaterGoal(PathfinderMob pathfinderMob, double d, int i) {
            super(pathfinderMob, d, i);
        }

        @Override
        public boolean canUse() {
            return super.canUse() && this.mob.isInWaterOrBubble();
        }

        @Override
        public boolean canContinueToUse() {
            return super.canContinueToUse() && this.mob.isInWaterOrBubble();
        }
    }
}
