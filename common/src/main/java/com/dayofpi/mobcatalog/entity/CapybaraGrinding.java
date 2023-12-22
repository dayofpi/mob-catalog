package com.dayofpi.mobcatalog.entity;

import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.crafting.Ingredient;

public class CapybaraGrinding {
    public final Ingredient ingredient;
    public final Item result;
    public final int amount;

    private CapybaraGrinding(Ingredient ingredient, Item result, int amount) {
        this.ingredient = ingredient;
        this.result = result;
        this.amount = amount;
    }

    public CapybaraGrinding(Item ingredient, Item result, int amount) {
        this(Ingredient.of(ingredient), result, amount);
    }

    public CapybaraGrinding(TagKey<Item> ingredient, Item result, int amount) {
        this(Ingredient.of(ingredient), result, amount);
    }
}
