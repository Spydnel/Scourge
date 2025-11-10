package com.spydnel.scourge.client.events;

import com.spydnel.scourge.common.entities.StoneGolemRenderer;
import com.spydnel.scourge.common.registry.ScourgeEntities;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.EntityRenderersEvent;

@EventBusSubscriber
@OnlyIn(Dist.CLIENT)
public class ScourgeClientEvents {
    @SubscribeEvent
    public static void registerEntityRenderers(EntityRenderersEvent.RegisterRenderers event) {
        event.registerEntityRenderer(ScourgeEntities.STONE_GOLEM.get(), StoneGolemRenderer::new);
    }
}
