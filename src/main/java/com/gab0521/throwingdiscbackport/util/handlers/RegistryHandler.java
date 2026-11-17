package com.gab0521.throwingdiscbackport.util.handlers;


import com.gab0521.throwingdiscbackport.events.DiamondDiscDispenser;
import com.gab0521.throwingdiscbackport.events.GoldDiscDispenser;
import com.gab0521.throwingdiscbackport.events.IronDiscDispenser;
import com.gab0521.throwingdiscbackport.init.ModEntities;
import com.gab0521.throwingdiscbackport.recipes.SmeltingRecipes;
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

@Mod.EventBusSubscriber
public class RegistryHandler {

    public static void preInitRegistries() {
        ModEntities.registerEntities();
        RenderHandler.registerEntityRenders();
    }

    public static void initRegistries() {
        SmeltingRecipes.init();
        BlockDispenser.DISPENSE_BEHAVIOR_REGISTRY.putObject(ModItems.IRON_THROWING_DISC, new IronDiscDispenser() {
            @Override
            protected IProjectile getProjectileEntity(World worldIn, IPosition position, ItemStack stackIn) {
                return null;
            }
        });
        BlockDispenser.DISPENSE_BEHAVIOR_REGISTRY.putObject(ModItems.GOLD_THROWING_DISC, new GoldDiscDispenser() {
            @Override
            protected IProjectile getProjectileEntity(World worldIn, IPosition position, ItemStack stackIn) {
                return null;
            }
        });
        BlockDispenser.DISPENSE_BEHAVIOR_REGISTRY.putObject(ModItems.DIAMOND_THROWING_DISC, new DiamondDiscDispenser() {
            @Override
            protected IProjectile getProjectileEntity(World worldIn, IPosition position, ItemStack stackIn) {
                return null;
            }
        });
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
