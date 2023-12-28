package com.dayofpi.mobcatalog.entity.custom;

import com.dayofpi.mobcatalog.entity.ModEntityTypes;
import com.dayofpi.mobcatalog.sound.ModSoundEvents;
import com.dayofpi.mobcatalog.util.ModUtil;
import mod.azure.azurelib.animatable.GeoEntity;
import mod.azure.azurelib.core.animatable.instance.AnimatableInstanceCache;
import mod.azure.azurelib.core.animation.AnimatableManager;
import mod.azure.azurelib.core.animation.AnimationController;
import mod.azure.azurelib.core.animation.RawAnimation;
import mod.azure.azurelib.util.AzureLibUtil;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.ItemTags;
import net.minecraft.util.StringRepresentable;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.control.SmoothSwimmingMoveControl;
import net.minecraft.world.entity.ai.goal.*;
import net.minecraft.world.entity.ai.navigation.AmphibiousPathNavigation;
import net.minecraft.world.entity.ai.navigation.PathNavigation;
import net.minecraft.world.entity.ai.navigation.WallClimberNavigation;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.pathfinder.BlockPathTypes;
import org.jetbrains.annotations.Nullable;

public class SlothEntity extends AmphibiousAnimal implements GeoEntity, ClimbingEntity, VariantHolder<SlothEntity.SlothVariant> {
    private static final EntityDataAccessor<String> DATA_VARIANT = SynchedEntityData.defineId(SlothEntity.class, EntityDataSerializers.STRING);
    private final AnimatableInstanceCache cache = AzureLibUtil.createInstanceCache(this);

    public SlothEntity(EntityType<? extends Animal> entityType, Level level) {
        super(entityType, level);
        this.setPathfindingMalus(BlockPathTypes.WATER, 0.0F);
        this.moveControl = new SlothMoveControl();
    }

    @Override
    protected void registerGoals() {
        this.goalSelector.addGoal(0, new BreathAirGoal(this));
        this.goalSelector.addGoal(1, new UnstuckGoal(this));
        this.goalSelector.addGoal(2, new BreedGoal(this, 1.0D));
        this.goalSelector.addGoal(3, new TemptGoal(this, 1.2D, Ingredient.of(ItemTags.LEAVES), false));
        this.goalSelector.addGoal(4, new FollowParentGoal(this, 1.1D));
        this.goalSelector.addGoal(5, new WaterAvoidingRandomStrollGoal(this, 1.0D));
        this.goalSelector.addGoal(6, new RandomSwimmingGoal(this, 1.0D, 10));
        this.goalSelector.addGoal(7, new LookAtPlayerGoal(this, Player.class, 6.0F));
        this.goalSelector.addGoal(8, new RandomLookAroundGoal(this));
    }

    @Override
    protected PathNavigation createNavigation(Level level) {
        return ClimbingEntity.isClimbableBlockNearby(this) ? new WallClimberNavigation(this, level) : new AmphibiousPathNavigation(this, level);
    }

    @Override
    public boolean onClimbable() {
        if (ClimbingEntity.canClimb(this)) return true;
        return super.onClimbable();
    }

    public static AttributeSupplier.Builder createAttributes() {
        return Mob.createMobAttributes().add(Attributes.MAX_HEALTH, 8.0D).add(Attributes.MOVEMENT_SPEED, 0.1D);
    }

    public boolean isClingingToTree() {
        return !this.isInWaterOrBubble() && !this.onGround() && level().getBlockState(blockPosition().above()).is(BlockTags.LOGS);
    }

    @Nullable
    @Override
    protected SoundEvent getAmbientSound() {
        return ModSoundEvents.SLOTH_AMBIENT.get();
    }

    @Nullable
    @Override
    protected SoundEvent getHurtSound(DamageSource pDamageSource) {
        return ModSoundEvents.SLOTH_HURT.get();
    }

    @Nullable
    @Override
    protected SoundEvent getDeathSound() {
        return ModSoundEvents.SLOTH_DEATH.get();
    }

    @Override
    public SoundEvent getEatingSound(ItemStack pStack) {
        return ModSoundEvents.SLOTH_EAT.get();
    }

    @Override
    protected void usePlayerItem(Player pPlayer, InteractionHand pHand, ItemStack pStack) {
        super.usePlayerItem(pPlayer, pHand, pStack);
        this.playSound(this.getEatingSound(pStack), 1.0F, 1.0F + (this.random.nextFloat() - this.random.nextFloat()) * 0.2F);
    }

    @Override
    protected void playStepSound(BlockPos pPos, BlockState pBlock) {
        this.playSound(ModSoundEvents.SLOTH_STEP.get(), 0.15F, 1.0F);
    }

    @Override
    public boolean isFood(ItemStack itemStack) {
        return itemStack.is(ItemTags.LEAVES);
    }

    @Override
    public boolean canClimbBlock(BlockState blockState) {
        return blockState.is(BlockTags.LOGS);
    }

    @Nullable
    @Override
    public AgeableMob getBreedOffspring(ServerLevel serverLevel, AgeableMob ageableMob) {
        SlothEntity babySloth = ModEntityTypes.SLOTH.get().create(serverLevel);
        if (babySloth != null)
            babySloth.setVariant(this.getVariant());
        return babySloth;
    }

    @Override
    public boolean canMate(Animal animal) {
        return super.canMate(animal) && animal instanceof SlothEntity && ((SlothEntity) animal).getVariant().equals(this.getVariant());
    }

    @Override
    public void registerControllers(AnimatableManager.ControllerRegistrar controllers) {
        controllers.add(new AnimationController<>(this, "main", 5, event -> {
            if (this.isInSwimmingPose()) {
                event.setControllerSpeed(ModUtil.getAnimationWalkSpeed(this, 1.0F));
                return event.setAndContinue(RawAnimation.begin().thenLoop("swim"));
            }
            else if (this.isClingingToTree()) {
                return event.setAndContinue(RawAnimation.begin().thenPlayAndHold("cling"));
            }
            else if (walkAnimation.isMoving()) {
                event.setControllerSpeed(ModUtil.getAnimationWalkSpeed(this, 1.0F));
                return event.setAndContinue(RawAnimation.begin().thenLoop("crawl"));
            }
            return event.setAndContinue(RawAnimation.begin().thenPlayAndHold("idle"));
        }));
    }

    @Override
    public AnimatableInstanceCache getAnimatableInstanceCache() {
        return this.cache;
    }

    @Override
    public SpawnGroupData finalizeSpawn(ServerLevelAccessor serverLevelAccessor, DifficultyInstance difficultyInstance, MobSpawnType mobSpawnType, @Nullable SpawnGroupData spawnGroupData, @Nullable CompoundTag compoundTag) {
        if (serverLevelAccessor.getRandom().nextBoolean()) {
            this.setVariant(SlothVariant.THREE_TOED);
        }
        return super.finalizeSpawn(serverLevelAccessor, difficultyInstance, mobSpawnType, spawnGroupData, compoundTag);
    }

    @Override
    protected void defineSynchedData() {
        super.defineSynchedData();
        this.entityData.define(DATA_VARIANT, SlothVariant.TWO_TOED.name);
    }

    public void addAdditionalSaveData(CompoundTag compoundTag) {
        super.addAdditionalSaveData(compoundTag);
        compoundTag.putString("variant", this.getVariant().getSerializedName());
    }

    public void readAdditionalSaveData(CompoundTag compoundTag) {
        super.readAdditionalSaveData(compoundTag);
        this.setVariant(SlothVariant.byName(compoundTag.getString("variant")));
    }

    @Override
    public void setVariant(SlothVariant slothVariant) {
        this.entityData.set(DATA_VARIANT, slothVariant.name);
    }

    @Override
    public SlothVariant getVariant() {
        return SlothVariant.byName(this.entityData.get(DATA_VARIANT));
    }

    private class SlothMoveControl extends SmoothSwimmingMoveControl {

        public SlothMoveControl() {
            super(SlothEntity.this, 85, 10, 0.5F, 1.0F, true);
        }

        @Override
        public void tick() {
            if (SlothEntity.this.isClingingToTree()) {
                SlothEntity.this.setNoGravity(true);
            }
            else {
                SlothEntity.this.setNoGravity(false);
                super.tick();
            }
        }
    }

    public enum SlothVariant implements StringRepresentable {
        TWO_TOED("two_toed"),
        THREE_TOED("three_toed");
        public static final StringRepresentable.EnumCodec<SlothVariant> CODEC = StringRepresentable.fromEnum(SlothVariant::values);
        final String name;

        SlothVariant(String name) {
            this.name = name;
        }

        @Override
        public String getSerializedName() {
            return this.name;
        }

        static SlothVariant byName(String string) {
            return CODEC.byName(string, TWO_TOED);
        }
    }
}
