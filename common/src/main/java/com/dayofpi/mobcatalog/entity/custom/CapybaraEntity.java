package com.dayofpi.mobcatalog.entity.custom;

import com.dayofpi.mobcatalog.entity.CapybaraCutting;
import com.dayofpi.mobcatalog.entity.ModEntityTypes;
import com.dayofpi.mobcatalog.sound.ModSoundEvents;
import com.dayofpi.mobcatalog.util.ModTags;
import com.dayofpi.mobcatalog.util.ModUtil;
import com.google.common.collect.ImmutableList;
import mod.azure.azurelib.animatable.GeoEntity;
import mod.azure.azurelib.core.animatable.instance.AnimatableInstanceCache;
import mod.azure.azurelib.core.animation.AnimatableManager;
import mod.azure.azurelib.core.animation.AnimationController;
import mod.azure.azurelib.core.animation.RawAnimation;
import mod.azure.azurelib.core.object.PlayState;
import mod.azure.azurelib.util.AzureLibUtil;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.AgeableMob;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.*;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.pathfinder.BlockPathTypes;
import org.jetbrains.annotations.Nullable;

public class CapybaraEntity extends Animal implements GeoEntity {
    private static final ImmutableList<CapybaraCutting> CUTTING_LIST = ImmutableList.of(
            new CapybaraCutting(Items.OAK_LOG, Items.STRIPPED_OAK_LOG, 1),
            new CapybaraCutting(Items.OAK_WOOD, Items.STRIPPED_OAK_WOOD, 1),
            new CapybaraCutting(Items.SPRUCE_LOG, Items.STRIPPED_SPRUCE_LOG, 1),
            new CapybaraCutting(Items.SPRUCE_WOOD, Items.STRIPPED_SPRUCE_WOOD, 1),
            new CapybaraCutting(Items.BIRCH_LOG, Items.STRIPPED_BIRCH_LOG, 1),
            new CapybaraCutting(Items.BIRCH_WOOD, Items.STRIPPED_BIRCH_WOOD, 1),
            new CapybaraCutting(Items.JUNGLE_LOG, Items.STRIPPED_JUNGLE_LOG, 1),
            new CapybaraCutting(Items.JUNGLE_WOOD, Items.STRIPPED_JUNGLE_WOOD, 1),
            new CapybaraCutting(Items.ACACIA_LOG, Items.STRIPPED_ACACIA_LOG, 1),
            new CapybaraCutting(Items.ACACIA_WOOD, Items.STRIPPED_ACACIA_WOOD, 1),
            new CapybaraCutting(Items.DARK_OAK_LOG, Items.STRIPPED_DARK_OAK_LOG, 1),
            new CapybaraCutting(Items.DARK_OAK_WOOD, Items.STRIPPED_DARK_OAK_WOOD, 1),
            new CapybaraCutting(Items.MANGROVE_LOG, Items.STRIPPED_MANGROVE_LOG, 1),
            new CapybaraCutting(Items.MANGROVE_WOOD, Items.STRIPPED_MANGROVE_WOOD, 1),
            new CapybaraCutting(Items.CHERRY_LOG, Items.STRIPPED_CHERRY_LOG, 1),
            new CapybaraCutting(Items.CHERRY_WOOD, Items.STRIPPED_CHERRY_WOOD, 1),
            new CapybaraCutting(Items.CRIMSON_STEM, Items.STRIPPED_CRIMSON_STEM, 1),
            new CapybaraCutting(Items.CRIMSON_HYPHAE, Items.STRIPPED_CRIMSON_HYPHAE, 1),
            new CapybaraCutting(Items.WARPED_STEM, Items.STRIPPED_WARPED_STEM, 1),
            new CapybaraCutting(Items.WARPED_HYPHAE, Items.STRIPPED_WARPED_HYPHAE, 1),
            new CapybaraCutting(Items.BAMBOO_BLOCK, Items.STRIPPED_BAMBOO_BLOCK, 1),
            new CapybaraCutting(Items.DRIED_KELP_BLOCK, Items.DRIED_KELP, 9),
            new CapybaraCutting(Items.HAY_BLOCK, Items.WHEAT, 9),
            new CapybaraCutting(Items.MELON, Items.MELON_SLICE, 9),
            new CapybaraCutting(Items.MELON_SLICE, Items.MELON_SEEDS, 1),
            new CapybaraCutting(Items.MANGROVE_ROOTS, Items.STICK, 4),
            new CapybaraCutting(ItemTags.PLANKS, Items.STICK, 2),
            new CapybaraCutting(Items.PUMPKIN, Items.PUMPKIN_SEEDS, 4),
            new CapybaraCutting(Items.SUGAR_CANE, Items.SUGAR, 1)
    );
    private final AnimatableInstanceCache cache = AzureLibUtil.createInstanceCache(this);

    public CapybaraEntity(EntityType<? extends Animal> entityType, Level level) {
        super(entityType, level);
        this.setPathfindingMalus(BlockPathTypes.WATER, 4.0F);
    }

    protected void registerGoals() {
        this.goalSelector.addGoal(0, new FloatGoal(this));
        this.goalSelector.addGoal(1, new PanicGoal(this, 1.6D));
        this.goalSelector.addGoal(2, new BreedGoal(this, 1.0D));
        this.goalSelector.addGoal(3, new TemptGoal(this, 1.2D, Ingredient.of(Items.MELON), false));
        this.goalSelector.addGoal(3, new TemptGoal(this, 1.2D, Ingredient.of(ModTags.Items.CAPYBARA_FOOD), false));
        this.goalSelector.addGoal(4, new FollowParentGoal(this, 1.1D));
        this.goalSelector.addGoal(5, new RandomStrollGoal(this, 1.0D));
        this.goalSelector.addGoal(6, new LookAtPlayerGoal(this, Player.class, 6.0F));
        this.goalSelector.addGoal(7, new RandomLookAroundGoal(this));
    }

    @Override
    protected float getWaterSlowDown() {
        return 0.98F;
    }

    public static AttributeSupplier.Builder createAttributes() {
        return Mob.createMobAttributes().add(Attributes.MAX_HEALTH, 10.0D).add(Attributes.MOVEMENT_SPEED, 0.25D);
    }

    @Override
    public boolean isFood(ItemStack pStack) {
        return pStack.is(ModTags.Items.CAPYBARA_FOOD);
    }

    @Nullable
    @Override
    protected SoundEvent getAmbientSound() {
        return ModSoundEvents.CAPYBARA_AMBIENT.get();
    }

    @Nullable
    @Override
    protected SoundEvent getHurtSound(DamageSource pDamageSource) {
        return ModSoundEvents.CAPYBARA_HURT.get();
    }

    @Nullable
    @Override
    protected SoundEvent getDeathSound() {
        return ModSoundEvents.CAPYBARA_DEATH.get();
    }

    @Override
    public SoundEvent getEatingSound(ItemStack pStack) {
        return ModSoundEvents.CAPYBARA_EAT.get();
    }

    @Override
    protected void usePlayerItem(Player pPlayer, InteractionHand pHand, ItemStack pStack) {
        super.usePlayerItem(pPlayer, pHand, pStack);
        this.playSound(this.getEatingSound(pStack), 1.0F, 1.0F + (this.random.nextFloat() - this.random.nextFloat()) * 0.2F);
    }

    @Override
    protected void playStepSound(BlockPos pPos, BlockState pBlock) {
        this.playSound(ModSoundEvents.CAPYBARA_STEP.get(), 0.15F, 1.0F);
    }

    @Override
    public InteractionResult mobInteract(Player pPlayer, InteractionHand pHand) {
        if (!this.level().isClientSide) {
            ItemStack itemStack = pPlayer.getItemInHand(pHand);
            boolean canEat = this.isFood(itemStack) && this.getAge() == 0 && this.canFallInLove();
            if (!canEat && !this.isBaby()) {
                for (CapybaraCutting capybaraCutting : CUTTING_LIST) {
                    if (capybaraCutting.ingredient.test(itemStack)) {
                        this.usePlayerItem(pPlayer, pHand, itemStack);
                        this.spawnAtLocation(new ItemStack(capybaraCutting.result, capybaraCutting.amount), 0.5F);
                        return InteractionResult.SUCCESS;
                    }
                }
            }
        }
        return super.mobInteract(pPlayer, pHand);
    }

    @Nullable
    @Override
    public AgeableMob getBreedOffspring(ServerLevel serverLevel, AgeableMob ageableMob) {
        return ModEntityTypes.CAPYBARA.get().create(serverLevel);
    }

    @Override
    public void registerControllers(AnimatableManager.ControllerRegistrar controllers) {
        controllers.add(new AnimationController<>(this, "main", 10, event -> {
            event.setControllerSpeed(ModUtil.getAnimationWalkSpeed(this, 1.0F));
            if (event.isMoving())
                return event.setAndContinue(RawAnimation.begin().thenLoop("walk"));
            return PlayState.STOP;
        }));
    }

    @Override
    public AnimatableInstanceCache getAnimatableInstanceCache() {
        return this.cache;
    }
}
