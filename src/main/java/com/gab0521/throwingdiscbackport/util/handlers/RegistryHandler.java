package com.gab0521.throwingdiscbackport.util.handlers;


import com.gab0521.throwingdiscbackport.config.CustomDiscAttributes;
import com.gab0521.throwingdiscbackport.events.DiscDispenser;
import com.gab0521.throwingdiscbackport.events.DiscRecipesEvent;
import com.gab0521.throwingdiscbackport.init.ModEntities;
import com.gab0521.throwingdiscbackport.recipes.SmeltingRecipes;
import com.gab0521.throwingdiscbackport.util.DiscDecoder;
import com.gab0521.throwingdiscbackport.util.IHasModel;
import com.gab0521.throwingdiscbackport.init.ModItems;
import net.minecraft.block.Block;
import net.minecraft.block.BlockDispenser;
import net.minecraft.dispenser.IPosition;
import net.minecraft.entity.IProjectile;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLServerStartingEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import java.util.ArrayList;

@Mod.EventBusSubscriber
public class RegistryHandler {

    public static void preInitRegistries() {
        ModItems.registerItems();
        ModEntities.registerEntities();
        RenderHandler.registerEntityRenders();
    }

    public static void initRegistries() {
        SmeltingRecipes.init();
        ArrayList<CustomDiscAttributes> decoded_discs_list = DiscDecoder.decode_discs();
        for (int i = 0; i < decoded_discs_list.size(); i++) {
            CustomDiscAttributes attributes = decoded_discs_list.get(i);
            BlockDispenser.DISPENSE_BEHAVIOR_REGISTRY.putObject(ModItems.DISCS_MAP.get(attributes.getMaterial().toUpperCase()+"_THROWING_DISC"), new DiscDispenser() {
                @Override
                protected IProjectile getProjectileEntity(World worldIn, IPosition position, ItemStack stackIn) {
                    return null;
                }
            });
        }
    }

    public static void postInitRegistries() {

    }

    public static void serverRegistries(FMLServerStartingEvent event) {

    }

    @SubscribeEvent
    public static void onBlockRegister(RegistryEvent.Register<Block> event) {
    }

    @SubscribeEvent
    public static void onItemRegister(RegistryEvent.Register<Item> event) {
        event.getRegistry().registerAll(ModItems.ITEMS.toArray(new Item[0]));
    }

    @SubscribeEvent
    public static void onModelRegister(ModelRegistryEvent event) {

        for (Item item : ModItems.ITEMS){

            if (item instanceof IHasModel) {

                ((IHasModel)item).registerModels();

            }

        }
    }
}
