package com.gab0521.throwingdiscbackport.recipes;

import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraftforge.oredict.ShapedOreRecipe;

public class DiscRecipes extends ShapedOreRecipe {

    public DiscRecipes(ResourceLocation group, ItemStack input, ItemStack output) {
        super(group, output,
                " D ",
                "D D",
                " D ",
                'D',input);
        this.setRegistryName(group);
    }
}
