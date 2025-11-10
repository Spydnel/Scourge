package com.spydnel.scourge.common.events;

import com.spydnel.scourge.Scourge;
import com.spydnel.scourge.common.registry.ScourgeParticles;
import com.spydnel.scourge.common.particle.SwordBounceProvider;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.RegisterParticleProvidersEvent;

@OnlyIn(Dist.CLIENT)
@EventBusSubscriber(modid = Scourge.MODID, value = Dist.CLIENT)
public class ScourgeEvents {
    @SubscribeEvent // on the mod event bus only on the physical client
    public static void registerParticleProviders(RegisterParticleProvidersEvent event) {
        event.registerSpriteSet(ScourgeParticles.SWORD_BOUNCE.get(), SwordBounceProvider::new);
    }
}
