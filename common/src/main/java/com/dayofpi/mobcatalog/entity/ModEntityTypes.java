package com.dayofpi.mobcatalog.entity;

import com.dayofpi.mobcatalog.MobCatalog;
import com.dayofpi.mobcatalog.entity.custom.*;
import dev.architectury.registry.level.entity.EntityAttributeRegistry;
import dev.architectury.registry.registries.DeferredRegister;
import dev.architectury.registry.registries.RegistrySupplier;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;

public class ModEntityTypes {
    public static final DeferredRegister<EntityType<?>> ENTITY_TYPES = DeferredRegister.create(MobCatalog.MOD_ID, Registries.ENTITY_TYPE);

    public static final RegistrySupplier<EntityType<CapybaraEntity>> CAPYBARA = ENTITY_TYPES.register("capybara", () -> EntityType.Builder.of(CapybaraEntity::new, MobCategory.CREATURE).sized(0.9F, 0.95F).build("capybara"));
    public static final RegistrySupplier<EntityType<CrabEntity>> CRAB = ENTITY_TYPES.register("crab", () -> EntityType.Builder.of(CrabEntity::new, MobCategory.CREATURE).sized(0.8F, 0.55F).build("crab"));
    public static final RegistrySupplier<EntityType<PenguinEntity>> PENGUIN = ENTITY_TYPES.register("penguin", () -> EntityType.Builder.of(PenguinEntity::new, MobCategory.CREATURE).sized(0.55F, 1.2F).build("penguin"));
    public static final RegistrySupplier<EntityType<SlothEntity>> SLOTH = ENTITY_TYPES.register("sloth", () -> EntityType.Builder.of(SlothEntity::new, MobCategory.CREATURE).sized(1.2F, 0.55F).build("sloth"));
    public static final RegistrySupplier<EntityType<StonemawEntity>> STONEMAW = ENTITY_TYPES.register("stonemaw", () -> EntityType.Builder.of(StonemawEntity::new, MobCategory.MONSTER).sized(0.9F, 0.95F).build("stonemaw"));

    public static void registerAttributes() {
        EntityAttributeRegistry.register(CAPYBARA, CapybaraEntity::createAttributes);
        EntityAttributeRegistry.register(CRAB, CrabEntity::createAttributes);
        EntityAttributeRegistry.register(PENGUIN, PenguinEntity::createAttributes);
        EntityAttributeRegistry.register(SLOTH, SlothEntity::createAttributes);
        EntityAttributeRegistry.register(STONEMAW, StonemawEntity::createAttributes);
    }
}
