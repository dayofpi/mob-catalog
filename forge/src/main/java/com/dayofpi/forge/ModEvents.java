package com.dayofpi.forge;

import com.dayofpi.MobCatalog;
import com.dayofpi.entity.ModEntityTypes;
import com.dayofpi.entity.custom.CrabEntity;
import com.dayofpi.entity.custom.PenguinEntity;
import com.dayofpi.item.ModItems;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.SpawnPlacements;
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
                if (event.getSlot().equals(EquipmentSlot.MAINHAND) || event.getSlot().equals(EquipmentSlot.MAINHAND)) {
                    if (event.getEntity().getItemBySlot(EquipmentSlot.MAINHAND).is(ModItems.CRAB_CLAW.get()) || event.getEntity().getItemBySlot(EquipmentSlot.OFFHAND).is(ModItems.CRAB_CLAW.get())) {
                        ScaleTypes.BLOCK_REACH.getScaleData(event.getEntity()).setScale(2.0F);
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
            event.register(ModEntityTypes.CRAB.get(), SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, CrabEntity::checkCrabSpawnRules, SpawnPlacementRegisterEvent.Operation.OR);
            event.register(ModEntityTypes.PENGUIN.get(), SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, PenguinEntity::checkPenguinSpawnRules, SpawnPlacementRegisterEvent.Operation.OR);
        }
    }
}
