package com.spydnel.scourge;

import net.minecraft.core.particles.ParticleType;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.core.registries.BuiltInRegistries;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public class ScourgeParticles {
    public static final DeferredRegister<ParticleType<?>> PARTICLE_TYPES =
            DeferredRegister.create(BuiltInRegistries.PARTICLE_TYPE, Scourge.MODID);

    public static final DeferredHolder<ParticleType<?>, SimpleParticleType> SWORD_BOUNCE = PARTICLE_TYPES.register(
            "sword_bounce",
            () -> new SimpleParticleType(false)
    );
}
