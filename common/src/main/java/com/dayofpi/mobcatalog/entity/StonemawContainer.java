package com.dayofpi.mobcatalog.entity;

import com.dayofpi.mobcatalog.entity.custom.StonemawEntity;
import com.dayofpi.mobcatalog.sound.ModSoundEvents;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.player.Player;

public class StonemawContainer extends SimpleContainer {
    private final StonemawEntity stonemaw;

    public StonemawContainer(StonemawEntity stonemaw) {
        super(27);
        this.stonemaw = stonemaw;
    }

    @Override
    public void stopOpen(Player player) {
        super.stopOpen(player);
        this.stonemaw.setMouthOpen(false);
        this.stonemaw.triggerAnim("mouth2", "close");
        this.stonemaw.playSound(ModSoundEvents.STONEMAW_CLOSE.get(), 1.0F, this.stonemaw.getVoicePitch());
    }
}
