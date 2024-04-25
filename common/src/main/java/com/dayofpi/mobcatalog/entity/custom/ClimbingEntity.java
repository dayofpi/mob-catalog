package com.dayofpi.mobcatalog.entity.custom;

import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;

import java.util.stream.Stream;

public interface ClimbingEntity {
    boolean canClimbBlock(BlockState blockState);

    static boolean isClimbableBlockNearby(Entity entity) {
        if (entity instanceof ClimbingEntity climbingEntity) {
            final Level level = entity.level();

            Stream<BlockPos> posList = BlockPos.withinManhattanStream(entity.blockPosition(), 2, 1, 2);
            return posList.anyMatch(
                (blockPos) -> level.isLoaded(blockPos) && climbingEntity.canClimbBlock(level.getBlockState(blockPos))
            );
        }
        return false;
    }

    static boolean canClimb(Entity entity) {
        if (entity instanceof ClimbingEntity climbingEntity) {
            final Level level = entity.level();

            Stream<BlockPos> posList = BlockPos.withinManhattanStream(entity.blockPosition(), 1, 0, 1);
            return posList.anyMatch(
                (blockPos) -> blockPos.distToCenterSqr(entity.position()) < 1.3 && level.isLoaded(blockPos) && climbingEntity.canClimbBlock(level.getBlockState(blockPos))
            );
        }
        return false;
    }
}
