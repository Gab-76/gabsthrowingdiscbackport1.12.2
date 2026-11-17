package com.gab0521.throwingdiscbackport;

import com.gab0521.throwingdiscbackport.proxy.CommonProxy;
import com.gab0521.throwingdiscbackport.util.Reference;
import com.gab0521.throwingdiscbackport.util.handlers.RegistryHandler;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.event.FMLServerStartingEvent;

@Mod(modid = Reference.MOD_ID, name = Reference.NAME, version = Reference.VERSION)
public class ThrowingDiscBackport {



    @Mod.Instance
    public static ThrowingDiscBackport instance;

    @SidedProxy(clientSide = Reference.CLIENT_PROXY_CLASS, serverSide = Reference.COMMON_PROXY_CLASS)
    public static CommonProxy proxy;

    @Mod.EventHandler
    public static void PreInit(FMLPreInitializationEvent event) {
        RegistryHandler.preInitRegistries();
    }

    @Mod.EventHandler
    public static void init(FMLInitializationEvent event) {
        RegistryHandler.initRegistries();
    }

    @Mod.EventHandler
    public static void PostInit(FMLPostInitializationEvent event) {
        RegistryHandler.postInitRegistries();
    }

    @Mod.EventHandler
    public static void PostInit(FMLServerStartingEvent event) {
        RegistryHandler.serverRegistries(event);
    }

}
