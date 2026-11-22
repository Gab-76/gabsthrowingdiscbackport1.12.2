package com.gab0521.throwingdiscbackport.recipes;

import com.gab0521.throwingdiscbackport.config.CustomDiscAttributes;
import com.gab0521.throwingdiscbackport.events.DiscDispenser;
import com.gab0521.throwingdiscbackport.init.ModItems;
import com.gab0521.throwingdiscbackport.util.DiscDecoder;
import net.minecraft.block.BlockDispenser;
import net.minecraft.dispenser.IPosition;
import net.minecraft.entity.IProjectile;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.registry.GameRegistry;

import java.util.ArrayList;

public class SmeltingRecipes {

    public static void init() {
        ArrayList<CustomDiscAttributes> decoded_discs_list = DiscDecoder.decode_discs();
        for (int i = 0; i < decoded_discs_list.size(); i++) {
            CustomDiscAttributes attributes = decoded_discs_list.get(i);
            String mapkey = attributes.getMaterial().toUpperCase() + "_THROWING_DISC";
            if (mapkey.equals("IRON_THROWING_DISC")){
                GameRegistry.addSmelting(ModItems.DISCS_MAP.get(mapkey), new ItemStack(Items.IRON_NUGGET, 1), 0.1f);
            }
            if (mapkey.equals("GOLD_THROWING_DISC")){
                GameRegistry.addSmelting(ModItems.DISCS_MAP.get(mapkey), new ItemStack(Items.GOLD_NUGGET, 1), 0.1f);
            }
        }
    }

}
