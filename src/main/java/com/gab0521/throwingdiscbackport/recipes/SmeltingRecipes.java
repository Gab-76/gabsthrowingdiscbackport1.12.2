package com.gab0521.throwingdiscbackport.recipes;

import com.gab0521.throwingdiscbackport.init.ModItems;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class SmeltingRecipes {

    public static void init() {
        GameRegistry.addSmelting(ModItems.IRON_THROWING_DISC, new ItemStack(Items.IRON_NUGGET, 1), 0.1f);
        GameRegistry.addSmelting(ModItems.GOLD_THROWING_DISC, new ItemStack(Items.GOLD_NUGGET, 1), 0.1f);
    }

}
