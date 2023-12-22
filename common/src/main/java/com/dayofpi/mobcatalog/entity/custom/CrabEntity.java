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
import net.minecraft.core.Holder;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.util.RandomSource;
import net.minecraft.util.StringRepresentable;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.*;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.Biomes;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.Nullable;

public class CrabEntity extends Animal implements GeoEntity, VariantHolder<CrabEntity.CrabVariant> {
    private static final EntityDataAccessor<String> DATA_VARIANT = SynchedEntityData.defineId(CrabEntity.class, EntityDataSerializers.STRING);
    private final AnimatableInstanceCache cache = AzureLibUtil.createInstanceCache(this);

    public CrabEntity(EntityType<? extends Animal> entityType, Level level) {
        super(entityType, level);
    }

    @Override
    protected void registerGoals() {
        this.goalSelector.addGoal(1, new MeleeAttackGoal(this, 1.0, false));
        this.goalSelector.addGoal(2, new BreedGoal(this, 1.0));
        this.goalSelector.addGoal(3, new TemptGoal(this, 1.0, Ingredient.of(ModTags.Items.CRAB_FOOD), false));
        this.goalSelector.addGoal(4, new FollowParentGoal(this, 1.1));
        this.goalSelector.addGoal(5, new WaterAvoidingRandomStrollGoal(this, 1.0));
        this.goalSelector.addGoal(6, new LookAtPlayerGoal(this, Player.class, 6.0F));
        this.goalSelector.addGoal(7, new RandomLookAroundGoal(this));
        this.targetSelector.addGoal(1, new HurtByTargetGoal(this));
    }

    public static AttributeSupplier.Builder createAttributes() {
        return Mob.createMobAttributes().add(Attributes.MAX_HEALTH, 8.0).add(Attributes.MOVEMENT_SPEED, 0.25).add(Attributes.ATTACK_DAMAGE, 2.0);
    }

    @Override
    public MobType getMobType() {
        return MobType.ARTHROPOD;
    }

    @Override
    protected int decreaseAirSupply(int i) {
        return i;
    }

    @Override
    public boolean isFood(ItemStack itemStack) {
        return itemStack.is(ModTags.Items.CRAB_FOOD);
    }

    @Override
    protected void usePlayerItem(Player player, InteractionHand interactionHand, ItemStack itemStack) {
        if (this.isFood(itemStack)) {
            this.playSound(ModSoundEvents.CRAB_EAT.get(), 1.0F, 1.0F);
        }

        super.usePlayerItem(player, interactionHand, itemStack);
    }

    @Nullable
    @Override
    protected SoundEvent getHurtSound(DamageSource damageSource) {
        return ModSoundEvents.CRAB_HURT.get();
    }

    @Nullable
    @Override
    protected SoundEvent getDeathSound() {
        return ModSoundEvents.CRAB_DEATH.get();
    }

    protected void playStepSound(BlockPos blockPos, BlockState blockState) {
        this.playSound(ModSoundEvents.CRAB_STEP.get(), 0.15F, 1.0F);
    }

    @Override
    protected float getSoundVolume() {
        return 0.4F;
    }

    @Override
    public boolean doHurtTarget(Entity entity) {
        this.triggerAnim("attack", "attack");
        return super.doHurtTarget(entity);
    }

    @Override
    public void registerControllers(AnimatableManager.ControllerRegistrar controllers) {
        AnimationController<CrabEntity> main = new AnimationController<>(this, "main", 10, event -> {
            if (event.isMoving()) {
                event.setControllerSpeed(ModUtil.getAnimationWalkSpeed(this, 1.0F));
                return event.setAndContinue(RawAnimation.begin().thenLoop("walk"));
            }
            return PlayState.STOP;
        });
        AnimationController<CrabEntity> attack = new AnimationController<>(this, "attack", 5, event -> PlayState.CONTINUE).triggerableAnim("attack", RawAnimation.begin().thenPlay("attack"));
        controllers.add(main, attack);
    }

    @Override
    public AnimatableInstanceCache getAnimatableInstanceCache() {
        return cache;
    }

    @Nullable
    @Override
    public AgeableMob getBreedOffspring(ServerLevel serverLevel, AgeableMob ageableMob) {
        return ModEntityTypes.CRAB.get().create(serverLevel);
    }

    public static boolean checkCrabSpawnRules(EntityType<? extends Animal> entityType, LevelAccessor levelAccessor, MobSpawnType mobSpawnType, BlockPos blockPos, RandomSource randomSource) {
        return levelAccessor.getBlockState(blockPos.below()).is(ModTags.Blocks.CRABS_SPAWNABLE_ON) && isBrightEnoughToSpawn(levelAccessor, blockPos);
    }

    @Override
    public SpawnGroupData finalizeSpawn(ServerLevelAccessor serverLevelAccessor, DifficultyInstance difficultyInstance, MobSpawnType mobSpawnType, @Nullable SpawnGroupData spawnGroupData, @Nullable CompoundTag compoundTag) {
        Holder<Biome> holder = serverLevelAccessor.getBiome(this.blockPosition());
        if (holder.is(Biomes.MANGROVE_SWAMP)) {
            this.setVariant(CrabVariant.BLUE);
        }
        return super.finalizeSpawn(serverLevelAccessor, difficultyInstance, mobSpawnType, spawnGroupData, compoundTag);
    }

    @Override
    protected void defineSynchedData() {
        super.defineSynchedData();
        this.entityData.define(DATA_VARIANT, CrabVariant.RED.name);
    }

    public void addAdditionalSaveData(CompoundTag compoundTag) {
        super.addAdditionalSaveData(compoundTag);
        compoundTag.putString("variant", this.getVariant().getSerializedName());
    }

    public void readAdditionalSaveData(CompoundTag compoundTag) {
        super.readAdditionalSaveData(compoundTag);
        this.setVariant(CrabVariant.byName(compoundTag.getString("variant")));
    }

    @Override
    public void setVariant(CrabVariant crabVariant) {
        this.entityData.set(DATA_VARIANT, crabVariant.name);
    }

    @Override
    public CrabVariant getVariant() {
        return CrabVariant.byName(this.entityData.get(DATA_VARIANT));
    }

    public enum CrabVariant implements StringRepresentable {
        RED("red"),
        BLUE("blue");
        public static final StringRepresentable.EnumCodec<CrabVariant> CODEC = StringRepresentable.fromEnum(CrabVariant::values);
        final String name;

        CrabVariant(String name) {
            this.name = name;
        }

        @Override
        public String getSerializedName() {
            return this.name;
        }

        static CrabVariant byName(String string) {
            return CODEC.byName(string, RED);
        }
    }
}
