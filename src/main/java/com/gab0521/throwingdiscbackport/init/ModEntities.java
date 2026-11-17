package com.gab0521.throwingdiscbackport.init;

import com.gab0521.throwingdiscbackport.ThrowingDiscBackport;
import com.gab0521.throwingdiscbackport.entities.throwing_disc.diamond.EntityDiamondThrowingDisc;
import com.gab0521.throwingdiscbackport.entities.throwing_disc.gold.EntityGoldThrowingDisc;
import com.gab0521.throwingdiscbackport.entities.throwing_disc.iron.EntityIronThrowingDisc;
import com.gab0521.throwingdiscbackport.util.Reference;
import net.minecraft.entity.Entity;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.registry.EntityRegistry;

public class ModEntities {

    public static void registerEntities() {
        registerNonMobEntity("iron_throwing_disc", EntityIronThrowingDisc.class, Reference.ENTITY_IRON_THROWING_DISC, 150);
        registerNonMobEntity("gold_throwing_disc", EntityGoldThrowingDisc.class, Reference.ENTITY_GOLD_THROWING_DISC, 150);
        registerNonMobEntity("diamond_throwing_disc", EntityDiamondThrowingDisc.class, Reference.ENTITY_DIAMOND_THROWING_DISC, 150);
    }

    private static void registerEntity(String name, Class<? extends Entity> entity, int id, int range, int color1, int color2) {

        EntityRegistry.registerModEntity(new ResourceLocation(Reference.MOD_ID + ":" + name), entity, name, id, ThrowingDiscBackport.instance, range, 1, true, color1, color2);

    }

    private static void registerNonMobEntity(String name, Class<? extends Entity> entity, int id, int range) {
        EntityRegistry.registerModEntity(new ResourceLocation(Reference.MOD_ID + ":" + name), entity, name, id, ThrowingDiscBackport.instance, range, 1, true);
    }

}
