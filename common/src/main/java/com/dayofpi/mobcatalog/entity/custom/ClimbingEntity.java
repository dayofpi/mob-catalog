package com.dayofpi.mobcatalog.entity.custom;

import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.block.state.BlockState;

import java.util.stream.Stream;

public interface ClimbingEntity {
    boolean canClimbBlock(BlockState blockState);

    static boolean isClimbableBlockNearby(Entity entity) {
        if (entity instanceof ClimbingEntity) {
            Stream<BlockPos> posList = BlockPos.withinManhattanStream(entity.blockPosition(), 2, 1, 2);
            return posList.anyMatch(blockPos -> ((ClimbingEntity) entity).canClimbBlock(entity.level().getBlockState(blockPos)));
        }
        return false;
    }

    static boolean canClimb(Entity entity) {
        if (entity instanceof ClimbingEntity) {
            Stream<BlockPos> posList = BlockPos.withinManhattanStream(entity.blockPosition(), 1, 0, 1);
            return posList.anyMatch(blockPos -> blockPos.distToCenterSqr(entity.position()) < 1.3 && ((ClimbingEntity) entity).canClimbBlock(entity.level().getBlockState(blockPos)));
        }
        return false;
    }
}
