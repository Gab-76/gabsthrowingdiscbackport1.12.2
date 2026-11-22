package com.gab0521.throwingdiscbackport.util.handlers;

import com.gab0521.throwingdiscbackport.entities.throwing_disc.EntityThrowingDisc;
import com.gab0521.throwingdiscbackport.entities.throwing_disc.RenderThrowingDisc;
import com.gab0521.throwingdiscbackport.util.Reference;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.client.registry.IRenderFactory;
import net.minecraftforge.fml.client.registry.RenderingRegistry;

import javax.annotation.Nullable;

public class RenderHandler {

    public static void registerEntityRenders() {

        RenderingRegistry.registerEntityRenderingHandler(EntityThrowingDisc.class, new IRenderFactory<EntityThrowingDisc>() {
            @Override
            public Render<? super EntityThrowingDisc> createRenderFor(RenderManager manager) {
                return new RenderThrowingDisc<>(manager);
            }
        });
    }
}
