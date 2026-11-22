package com.gab0521.throwingdiscbackport.config;

import com.gab0521.throwingdiscbackport.util.Reference;
import net.minecraftforge.common.config.Config;
import net.minecraftforge.common.config.ConfigManager;
import net.minecraftforge.fml.client.event.ConfigChangedEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Config(modid = Reference.MOD_ID, name = Reference.NAME)
public class ModConfig {

    @Config.RequiresMcRestart
    @Config.Comment("Custom Throwing discs, format: 'modid:material:meta:enchantability:damageinhand:damagethrown:attackspeed:durability:speed:maxbounces:floatingtime', put the textures and models in a resource pack with the path assets/throwingdiscbackport ")
    @Config.Name("Throwing Discs: ")
    public static String[] discs = new String[] {
        "minecraft:iron_ingot:0:14:1.5:4:-3.0:250:0.7:5:20",
        "minecraft:gold_ingot:0:22:1.5:3:-3:32:0.7:5:20",
        "minecraft:diamond:0:10:1.5:6:-3:1561:0.7:5:20",
    };

    @Mod.EventBusSubscriber(modid = Reference.MOD_ID)
    private static class EventHandler {
        @SubscribeEvent
        public static void onConfigChanged(final ConfigChangedEvent.OnConfigChangedEvent event) {
            if (event.getModID().equals(Reference.MOD_ID)) {
                ConfigManager.sync(Reference.MOD_ID, Config.Type.INSTANCE);
            }
        }
    }
}

