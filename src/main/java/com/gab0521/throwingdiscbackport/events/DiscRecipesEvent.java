package com.gab0521.throwingdiscbackport.events;

import com.gab0521.throwingdiscbackport.config.CustomDiscAttributes;
import com.gab0521.throwingdiscbackport.recipes.DiscRecipes;
import com.gab0521.throwingdiscbackport.util.DiscDecoder;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import java.util.ArrayList;

@Mod.EventBusSubscriber
public class DiscRecipesEvent {

    @SubscribeEvent
    public static void registerRecipes(RegistryEvent.Register<IRecipe> event) {
        ArrayList<CustomDiscAttributes> decoded_discs_list = DiscDecoder.decode_discs();
        for (int i = 0; i < decoded_discs_list.size(); i++) {
            CustomDiscAttributes attributes = decoded_discs_list.get(i);
            String iteminput = attributes.getCraftingItem();
            String discoutput = "throwingdiscbackport:" + attributes.getMaterial() + "_throwing_disc";
            ResourceLocation registryName = new ResourceLocation(discoutput);
            Item input = Item.getByNameOrId(iteminput);
            Item output = Item.getByNameOrId(discoutput);
            if (input == null) {
                System.out.println("One or more discs didn't have a correct item registration (" + iteminput + "), try again!");
                continue;
            }
            ItemStack inputItemStack = new ItemStack(input);
            inputItemStack.setItemDamage(attributes.getMeta());
            ItemStack discItemStack = new ItemStack(output);
            event.getRegistry().register(new DiscRecipes(registryName, inputItemStack, discItemStack));
        }
    }
}
