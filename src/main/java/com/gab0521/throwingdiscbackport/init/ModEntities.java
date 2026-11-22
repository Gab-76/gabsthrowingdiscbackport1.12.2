package com.gab0521.throwingdiscbackport.init;

import com.gab0521.throwingdiscbackport.ThrowingDiscBackport;
import com.gab0521.throwingdiscbackport.entities.throwing_disc.EntityThrowingDisc;
import com.gab0521.throwingdiscbackport.util.Reference;
import net.minecraft.entity.Entity;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.registry.EntityRegistry;

public class ModEntities {

    public static void registerEntities() {
        registerNonMobEntity("throwing_disc", EntityThrowingDisc.class, Reference.ENTITY_THROWING_DISC, 150);
    }

    private static void registerEntity(String name, Class<? extends Entity> entity, int id, int range, int color1, int color2) {

        EntityRegistry.registerModEntity(new ResourceLocation(Reference.MOD_ID + ":" + name), entity, name, id, ThrowingDiscBackport.instance, range, 1, true, color1, color2);

    }

    private static void registerNonMobEntity(String name, Class<? extends Entity> entity, int id, int range) {
        EntityRegistry.registerModEntity(new ResourceLocation(Reference.MOD_ID + ":" + name), entity, name, id, ThrowingDiscBackport.instance, range, 1, true);
    }

}
