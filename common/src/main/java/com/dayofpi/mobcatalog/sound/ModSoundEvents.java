package com.dayofpi.mobcatalog.sound;

import com.dayofpi.mobcatalog.MobCatalog;
import dev.architectury.registry.registries.DeferredRegister;
import dev.architectury.registry.registries.RegistrySupplier;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;

public class ModSoundEvents {
    public static final DeferredRegister<SoundEvent> SOUND_EVENTS = DeferredRegister.create(MobCatalog.MOD_ID, Registries.SOUND_EVENT);

    public static final RegistrySupplier<SoundEvent> CRAB_DEATH = registerSoundEvent("entity.crab.death");
    public static final RegistrySupplier<SoundEvent> CRAB_EAT = registerSoundEvent("entity.crab.eat");
    public static final RegistrySupplier<SoundEvent> CRAB_HURT = registerSoundEvent("entity.crab.hurt");
    public static final RegistrySupplier<SoundEvent> CRAB_STEP = registerSoundEvent("entity.crab.step");
    public static final RegistrySupplier<SoundEvent> PENGUIN_AMBIENT = registerSoundEvent("entity.penguin.ambient");
    public static final RegistrySupplier<SoundEvent> PENGUIN_DEATH = registerSoundEvent("entity.penguin.death");
    public static final RegistrySupplier<SoundEvent> PENGUIN_EAT = registerSoundEvent("entity.penguin.eat");
    public static final RegistrySupplier<SoundEvent> PENGUIN_HURT = registerSoundEvent("entity.penguin.hurt");
    public static final RegistrySupplier<SoundEvent> PENGUIN_STEP = registerSoundEvent("entity.penguin.step");

    private static RegistrySupplier<SoundEvent> registerSoundEvent(String name) {
        ResourceLocation id = new ResourceLocation(MobCatalog.MOD_ID, name);
        return SOUND_EVENTS.register(name, () -> SoundEvent.createVariableRangeEvent(id));
    }
}
