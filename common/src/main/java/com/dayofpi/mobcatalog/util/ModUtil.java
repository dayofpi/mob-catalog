package com.dayofpi.mobcatalog.util;

import com.dayofpi.mobcatalog.entity.custom.PenguinEntity;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.vehicle.Boat;
import net.minecraft.world.phys.Vec3;

import java.util.List;

public class ModUtil {
    public static float getAnimationWalkSpeed(Entity entity, float minSpeed) {
        double movementSpeed = entity.getDeltaMovement().horizontalDistanceSqr() * 500;
        return (float) Mth.clamp(movementSpeed, minSpeed, minSpeed * 2);
    }

    public static void speedUpBoat(Boat boat) {
        if (boat.isVehicle()) {
            List<PenguinEntity> list = boat.level().getEntitiesOfClass(PenguinEntity.class, boat.getBoundingBox().inflate(15.0), Entity::isInWaterOrBubble);

            if (!list.isEmpty() && boat.getWaterLevelAbove() > 0.0F && boat.getPaddleState(0) && boat.getPaddleState(1)) {
                Vec3 alteredMovement = boat.getDeltaMovement().multiply(1.2, 0.0, 1.2);
                double maxSpeed = 0.7;
                boat.setDeltaMovement(Mth.clamp(alteredMovement.x, -maxSpeed, maxSpeed), alteredMovement.y, Mth.clamp(alteredMovement.z, -maxSpeed, maxSpeed));
            }
        }
        if (boat.getDeltaMovement().horizontalDistanceSqr() > 0.5) {
            for(int i = 0; i < 5; ++i) {
                boat.level().addParticle(ParticleTypes.SPLASH, boat.getRandomX(0.5), boat.getY() + 0.5, boat.getRandomZ(0.5), 0.0, 0.0, 0.0);
            }
        }
    }
}
