package com.dayofpi.mobcatalog.entity;

import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.ai.goal.TemptGoal;
import net.minecraft.world.item.crafting.Ingredient;

public class AlternateTemptGoal extends TemptGoal {
    private final boolean requirement;
    private final boolean required;

    public AlternateTemptGoal(PathfinderMob pathfinderMob, double speed, Ingredient ingredient, boolean requirement, boolean required, boolean canScare) {
        super(pathfinderMob, speed, ingredient, canScare);
        this.requirement = requirement;
        this.required = required;
    }

    @Override
    public boolean canUse() {
        if (this.required && !requirement) {
            return false;
        } else if (!this.required && requirement) {
            return false;
        }
        return super.canUse();
    }
}
