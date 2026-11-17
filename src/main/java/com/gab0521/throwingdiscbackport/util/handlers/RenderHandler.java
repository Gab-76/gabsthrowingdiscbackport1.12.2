package com.gab0521.throwingdiscbackport.util.handlers;

import com.gab0521.throwingdiscbackport.entities.throwing_disc.diamond.EntityDiamondThrowingDisc;
import com.gab0521.throwingdiscbackport.entities.throwing_disc.diamond.RenderDiamondThrowingDisc;
import com.gab0521.throwingdiscbackport.entities.throwing_disc.gold.EntityGoldThrowingDisc;
import com.gab0521.throwingdiscbackport.entities.throwing_disc.gold.RenderGoldThrowingDisc;
import com.gab0521.throwingdiscbackport.entities.throwing_disc.iron.EntityIronThrowingDisc;
import com.gab0521.throwingdiscbackport.entities.throwing_disc.iron.RenderIronThrowingDisc;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraftforge.fml.client.registry.IRenderFactory;
import net.minecraftforge.fml.client.registry.RenderingRegistry;

public class RenderHandler {

    public static void registerEntityRenders() {

        RenderingRegistry.registerEntityRenderingHandler(EntityIronThrowingDisc.class, new IRenderFactory<EntityIronThrowingDisc>() {
            @Override
            public Render<? super EntityIronThrowingDisc> createRenderFor(RenderManager manager) {
                return new RenderIronThrowingDisc<>(manager);
            }
        });
        RenderingRegistry.registerEntityRenderingHandler(EntityGoldThrowingDisc.class, new IRenderFactory<EntityGoldThrowingDisc>() {
            @Override
            public Render<? super EntityGoldThrowingDisc> createRenderFor(RenderManager manager) {
                return new RenderGoldThrowingDisc<>(manager);
            }
        });
        RenderingRegistry.registerEntityRenderingHandler(EntityDiamondThrowingDisc.class, new IRenderFactory<EntityDiamondThrowingDisc>() {
            @Override
            public Render<? super EntityDiamondThrowingDisc> createRenderFor(RenderManager manager) {
                return new RenderDiamondThrowingDisc<>(manager);
            }
        });

    }

}
