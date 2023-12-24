package com.dayofpi.mobcatalog.forge;

import com.dayofpi.mobcatalog.MobCatalog;
import com.dayofpi.mobcatalog.ModConfigs;
import com.dayofpi.mobcatalog.entity.ModEntityTypes;
import com.dayofpi.mobcatalog.entity.custom.CrabEntity;
import com.dayofpi.mobcatalog.entity.custom.PenguinEntity;
import com.dayofpi.mobcatalog.entity.custom.StonemawEntity;
import com.dayofpi.mobcatalog.item.ModItems;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.SpawnPlacements;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraftforge.event.entity.SpawnPlacementRegisterEvent;
import net.minecraftforge.event.entity.living.LivingEquipmentChangeEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import virtuoel.pehkui.api.ScaleTypes;

public class ModEvents {
    @Mod.EventBusSubscriber(modid = MobCatalog.MOD_ID, bus = Mod.EventBusSubscriber.Bus.FORGE)
    public static class ForgeEventBusEvents {
        @SubscribeEvent
        public static void onEquipmentChanged(LivingEquipmentChangeEvent event) {
            if (event.getTo().is(ModItems.CRAB_CLAW.get()) || event.getFrom().is(ModItems.CRAB_CLAW.get())) {
                boolean wasSlotUpdated = event.getSlot().equals(EquipmentSlot.MAINHAND);
                if (ModConfigs.CRAB_CLAW_WORKS_IN_OFFHAND.get()) {
                    wasSlotUpdated = event.getSlot().equals(EquipmentSlot.MAINHAND) || event.getSlot().equals(EquipmentSlot.OFFHAND);
                }
                if (wasSlotUpdated) {
                    boolean isCrabClawEquipped = event.getEntity().getItemBySlot(EquipmentSlot.MAINHAND).is(ModItems.CRAB_CLAW.get());
                    if (ModConfigs.CRAB_CLAW_WORKS_IN_OFFHAND.get()) {
                        isCrabClawEquipped = event.getEntity().getItemBySlot(EquipmentSlot.MAINHAND).is(ModItems.CRAB_CLAW.get()) || event.getEntity().getItemBySlot(EquipmentSlot.OFFHAND).is(ModItems.CRAB_CLAW.get());
                    }
                    if (isCrabClawEquipped) {
                        ScaleTypes.BLOCK_REACH.getScaleData(event.getEntity()).setScale(ModConfigs.CRAB_CLAW_REACH_MULTIPLIER.get());
                    } else {
                        ScaleTypes.BLOCK_REACH.getScaleData(event.getEntity()).setScale(1.0F);
                    }
                }
            }
        }
    }

    @Mod.EventBusSubscriber(modid = MobCatalog.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
    public static class ModEventBusEvents {
        @SubscribeEvent
        public static void registerSpawnPlacements(SpawnPlacementRegisterEvent event) {
            event.register(ModEntityTypes.CAPYBARA.get(), SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, Animal::checkAnimalSpawnRules, SpawnPlacementRegisterEvent.Operation.OR);
            event.register(ModEntityTypes.CRAB.get(), SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, CrabEntity::checkCrabSpawnRules, SpawnPlacementRegisterEvent.Operation.OR);
            event.register(ModEntityTypes.PENGUIN.get(), SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, PenguinEntity::checkPenguinSpawnRules, SpawnPlacementRegisterEvent.Operation.OR);
            event.register(ModEntityTypes.STONEMAW.get(), SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, StonemawEntity::checkStonemawSpawnRules, SpawnPlacementRegisterEvent.Operation.OR);
        }
    }
}
