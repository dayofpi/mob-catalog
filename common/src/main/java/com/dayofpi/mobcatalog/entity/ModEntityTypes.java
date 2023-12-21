package com.dayofpi.mobcatalog.entity;

import com.dayofpi.mobcatalog.MobCatalog;
import com.dayofpi.mobcatalog.entity.custom.CrabEntity;
import com.dayofpi.mobcatalog.entity.custom.PenguinEntity;
import dev.architectury.registry.level.entity.EntityAttributeRegistry;
import dev.architectury.registry.registries.DeferredRegister;
import dev.architectury.registry.registries.RegistrySupplier;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;

public class ModEntityTypes {
    public static final DeferredRegister<EntityType<?>> ENTITY_TYPES = DeferredRegister.create(MobCatalog.MOD_ID, Registries.ENTITY_TYPE);

    public static final RegistrySupplier<EntityType<CrabEntity>> CRAB = ENTITY_TYPES.register("crab", () -> EntityType.Builder.of(CrabEntity::new, MobCategory.CREATURE).sized(0.8F, 0.55F).build("crab"));
    public static final RegistrySupplier<EntityType<PenguinEntity>> PENGUIN = ENTITY_TYPES.register("penguin", () -> EntityType.Builder.of(PenguinEntity::new, MobCategory.CREATURE).sized(0.55F, 1.2F).build("penguin"));

    public static void registerAttributes() {
        EntityAttributeRegistry.register(CRAB, CrabEntity::createAttributes);
        EntityAttributeRegistry.register(PENGUIN, PenguinEntity::createAttributes);
    }
}
