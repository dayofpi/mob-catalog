package com.dayofpi.mobcatalog.entity.custom;

import com.dayofpi.mobcatalog.entity.StonemawContainer;
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
import net.minecraft.nbt.ListTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.util.RandomSource;
import net.minecraft.world.*;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.control.LookControl;
import net.minecraft.world.entity.ai.control.MoveControl;
import net.minecraft.world.entity.ai.goal.*;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.NonTameRandomTargetGoal;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ChestMenu;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.Nullable;

public class StonemawEntity extends TamableAnimal implements GeoEntity, HasCustomInventoryScreen, MenuProvider {
    private static final EntityDataAccessor<Boolean> MOUTH_OPEN = SynchedEntityData.defineId(StonemawEntity.class, EntityDataSerializers.BOOLEAN);
    private final AnimatableInstanceCache cache = AzureLibUtil.createInstanceCache(this);
    private SimpleContainer inventory;

    public StonemawEntity(EntityType<? extends TamableAnimal> entityType, Level level) {
        super(entityType, level);
        this.createInventory();
        this.lookControl = new StonemawLookControl();
        this.moveControl = new StonemawMoveControl();
        this.xpReward = 5;
    }

    private void createInventory() {
        SimpleContainer simpleContainer = this.inventory;
        this.inventory = new StonemawContainer(this);
        if (simpleContainer != null) {
            int i = Math.min(simpleContainer.getContainerSize(), this.inventory.getContainerSize());

            for(int j = 0; j < i; ++j) {
                ItemStack itemStack = simpleContainer.getItem(j);
                if (!itemStack.isEmpty()) {
                    this.inventory.setItem(j, itemStack.copy());
                }
            }
        }
    }

    @Override
    protected void registerGoals() {
        this.goalSelector.addGoal(1, new FloatGoal(this));
        this.goalSelector.addGoal(2, new SitWhenOrderedToGoal(this));
        this.goalSelector.addGoal(3, new MeleeAttackGoal(this, 1.0, false));
        this.goalSelector.addGoal(4, new FollowOwnerGoal(this, 1.0, 10.0F, 5.0F, false));
        this.goalSelector.addGoal(5, new WaterAvoidingRandomStrollGoal(this, 1.0));
        this.goalSelector.addGoal(6, new LookAtPlayerGoal(this, Player.class, 6.0F));
        this.goalSelector.addGoal(7, new RandomLookAroundGoal(this));
        this.targetSelector.addGoal(1, new HurtByTargetGoal(this));
        this.targetSelector.addGoal(2, new NonTameRandomTargetGoal<>(this, Player.class, false, null));
    }

    public static AttributeSupplier.Builder createAttributes() {
        return Mob.createMobAttributes().add(Attributes.MAX_HEALTH, 30.0).add(Attributes.MOVEMENT_SPEED, 0.25).add(Attributes.ATTACK_DAMAGE, 4.0);
    }

    @Nullable
    @Override
    protected SoundEvent getAmbientSound() {
        return ModSoundEvents.STONEMAW_AMBIENT.get();
    }

    @Nullable
    @Override
    protected SoundEvent getHurtSound(DamageSource pDamageSource) {
        return ModSoundEvents.STONEMAW_HURT.get();
    }

    @Nullable
    @Override
    protected SoundEvent getDeathSound() {
        return ModSoundEvents.STONEMAW_DEATH.get();
    }

    @Override
    public SoundEvent getEatingSound(ItemStack pStack) {
        return ModSoundEvents.STONEMAW_EAT.get();
    }

    @Override
    protected void usePlayerItem(Player pPlayer, InteractionHand pHand, ItemStack pStack) {
        super.usePlayerItem(pPlayer, pHand, pStack);
        this.playSound(this.getEatingSound(pStack), 1.0F, 1.0F + (this.random.nextFloat() - this.random.nextFloat()) * 0.2F);
    }

    @Override
    protected void playStepSound(BlockPos pPos, BlockState pBlock) {
        this.playSound(ModSoundEvents.STONEMAW_STEP.get(), 0.15F, 1.0F);
    }

    @Override
    public boolean isFood(ItemStack itemStack) {
        return itemStack.is(ModTags.Items.STONEMAW_FOOD);
    }

    @Override
    public float getWalkTargetValue(BlockPos blockPos, LevelReader levelReader) {
        return -levelReader.getPathfindingCostFromLightLevels(blockPos);
    }

    public static boolean checkStonemawSpawnRules(EntityType<? extends StonemawEntity> entityType, ServerLevelAccessor serverLevelAccessor, MobSpawnType mobSpawnType, BlockPos blockPos, RandomSource randomSource) {
        return serverLevelAccessor.getDifficulty() != Difficulty.PEACEFUL && blockPos.getY() < 60 && Monster.isDarkEnoughToSpawn(serverLevelAccessor, blockPos, randomSource) && checkMobSpawnRules(entityType, serverLevelAccessor, mobSpawnType, blockPos, randomSource);
    }

    @Override
    public boolean removeWhenFarAway(double d) {
        return !this.isTame();
    }

    @Override
    public InteractionResult mobInteract(Player player, InteractionHand interactionHand) {
        ItemStack itemStack = player.getItemInHand(interactionHand);
        if (this.isOwnedBy(player) && this.isTame() && (!this.isFood(itemStack) || this.getHealth() >= this.getMaxHealth())) {
            if (player.isSecondaryUseActive()) {
                this.openCustomInventoryScreen(player);
                this.setMouthOpen(true);
                this.playSound(ModSoundEvents.STONEMAW_OPEN.get(), 1.0F, this.getVoicePitch());
            } else if (this.isOwnedBy(player))
                this.setOrderedToSit(!this.isOrderedToSit());
            return InteractionResult.sidedSuccess(this.level().isClientSide);
        }
        else if (this.isFood(itemStack)) {
            if (!this.isTame()) {
                if (!this.level().isClientSide) {
                    this.usePlayerItem(player, interactionHand, itemStack);
                    this.tame(player);
                    this.setOrderedToSit(true);
                    this.setTarget(null);
                    this.level().broadcastEntityEvent(this, (byte)7);
                    return InteractionResult.SUCCESS;
                }

                if (this.level().isClientSide) {
                    return InteractionResult.CONSUME;
                }
            } else if (this.getHealth() < this.getMaxHealth()) {
                this.usePlayerItem(player, interactionHand, itemStack);
                this.heal(10);
                return InteractionResult.sidedSuccess(this.level().isClientSide);
            }
        }
        return InteractionResult.PASS;
    }

    @Override
    public boolean isBaby() {
        return false;
    }

    @Nullable
    @Override
    public AgeableMob getBreedOffspring(ServerLevel serverLevel, AgeableMob ageableMob) {
        return null;
    }

    @Override
    protected void defineSynchedData() {
        super.defineSynchedData();
        this.entityData.define(MOUTH_OPEN, false);
    }

    @Override
    public void addAdditionalSaveData(CompoundTag compoundTag) {
        super.addAdditionalSaveData(compoundTag);
        compoundTag.putBoolean("mouth_open", this.isMouthOpen());
        ListTag listTag = new ListTag();

        for(int i = 0; i < inventory.getContainerSize(); ++i) {
            ItemStack itemStack = inventory.getItem(i);
            if (!itemStack.isEmpty()) {
                CompoundTag compoundTag2 = new CompoundTag();
                compoundTag2.putByte("Slot", (byte)i);
                itemStack.save(compoundTag2);
                listTag.add(compoundTag2);
            }
        }

        if (!listTag.isEmpty()) {
            compoundTag.put("Items", listTag);
        }
    }

    @Override
    public void readAdditionalSaveData(CompoundTag compoundTag) {
        super.readAdditionalSaveData(compoundTag);
        this.setMouthOpen(compoundTag.getBoolean("mouth_open"));
        this.createInventory();
        ListTag listTag = compoundTag.getList("Items", 10);

        for(int i = 0; i < listTag.size(); ++i) {
            CompoundTag compoundTag2 = listTag.getCompound(i);
            int j = compoundTag2.getByte("Slot") & 255;
            if (j < inventory.getContainerSize()) {
                inventory.setItem(j, ItemStack.of(compoundTag2));
            }
        }
    }

    @Override
    public SlotAccess getSlot(int k) {
        return k >= 0 && k < inventory.getContainerSize() ? new SlotAccess() {
            public ItemStack get() {
                return inventory.getItem(k);
            }

            public boolean set(ItemStack arg) {
                inventory.setItem(k, arg);
                return true;
            }
        } : SlotAccess.NULL;
    }

    private boolean isMouthOpen() {
        return this.entityData.get(MOUTH_OPEN);
    }

    public void setMouthOpen(boolean mouthOpen) {
        this.entityData.set(MOUTH_OPEN, mouthOpen);
    }

    @Override
    public void registerControllers(AnimatableManager.ControllerRegistrar controllers) {
        AnimationController<StonemawEntity> main = new AnimationController<>(this, "main", 10, event -> {
            if (this.isInSittingPose()) {
                return event.setAndContinue(RawAnimation.begin().thenPlayAndHold("sit"));
            } else if (event.isMoving()) {
                event.setControllerSpeed(ModUtil.getAnimationWalkSpeed(this, 2.0F));
                return event.setAndContinue(RawAnimation.begin().thenLoop("walk"));
            } else return PlayState.STOP;
        });
        AnimationController<StonemawEntity> mouth = new AnimationController<>(this, "mouth", 10, event -> {
            if (this.isAggressive())
                return event.setAndContinue(RawAnimation.begin().thenLoop("attack"));
            else if (this.isMouthOpen())
                return event.setAndContinue(RawAnimation.begin().thenPlayAndHold("open"));
            else
                return PlayState.STOP;
        });
        AnimationController<StonemawEntity> mouth2 = new AnimationController<>(this, "mouth2", 0, event -> PlayState.CONTINUE).triggerableAnim("close", RawAnimation.begin().thenPlay("close"));
        controllers.add(main, mouth, mouth2);
    }

    @Override
    public AnimatableInstanceCache getAnimatableInstanceCache() {
        return this.cache;
    }

    @Override
    public void openCustomInventoryScreen(Player player) {
        if (!this.level().isClientSide)
            player.openMenu(this);
    }

    @Override
    protected void dropEquipment() {
        super.dropEquipment();
        if (this.inventory != null) {
            for(int i = 0; i < this.inventory.getContainerSize(); ++i) {
                ItemStack itemstack = this.inventory.getItem(i);
                if (!itemstack.isEmpty() && !EnchantmentHelper.hasVanishingCurse(itemstack)) {
                    this.spawnAtLocation(itemstack);
                }
            }
        }
    }

    @Override
    protected boolean shouldDropLoot() {
        return true;
    }

    @Nullable
    @Override
    public AbstractContainerMenu createMenu(int i, Inventory inventory, Player player) {
        if (this.inventory == null) {
            return null;
        }
        return new ChestMenu(MenuType.GENERIC_9x3, i, inventory, this.inventory, 3);
    }

    private class StonemawLookControl extends LookControl {
        protected StonemawLookControl() {
            super(StonemawEntity.this);
        }

        @Override
        public void tick() {
            if (!StonemawEntity.this.isMouthOpen())
                super.tick();
        }
    }

    private class StonemawMoveControl extends MoveControl {

        public StonemawMoveControl() {
            super(StonemawEntity.this);
        }

        @Override
        public void tick() {
            if (!StonemawEntity.this.isMouthOpen())
                super.tick();
        }
    }
}
