package com.dayofpi.mobcatalog.entity;

import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.crafting.Ingredient;

public class CapybaraCutting {
    public final Ingredient ingredient;
    public final Item result;
    public final int amount;

    private CapybaraCutting(Ingredient ingredient, Item result, int amount) {
        this.ingredient = ingredient;
        this.result = result;
        this.amount = amount;
    }

    public CapybaraCutting(Item ingredient, Item result, int amount) {
        this(Ingredient.of(ingredient), result, amount);
    }

    public CapybaraCutting(TagKey<Item> ingredient, Item result, int amount) {
        this(Ingredient.of(ingredient), result, amount);
    }
}
