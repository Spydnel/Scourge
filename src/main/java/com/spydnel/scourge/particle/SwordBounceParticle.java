package com.spydnel.scourge.particle;

import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.ParticleRenderType;
import net.minecraft.client.particle.SpriteSet;
import net.minecraft.client.particle.TextureSheetParticle;

public class SwordBounceParticle extends TextureSheetParticle {
    private final SpriteSet spriteSet;
    protected SwordBounceParticle(ClientLevel level, double x, double y, double z, SpriteSet spriteSet) {
        super(level, x, y, z);
        this.spriteSet = spriteSet;
        this.gravity = 0;
        this.quadSize = 0.4F;
        this.setSpriteFromAge(spriteSet);
        this.lifetime = 5;
        this.xd = (Math.random() * 2.0 - 1.0) * 0.01;
        this.yd = (Math.random() * 2.0 - 1.0) * 0.01;
        this.zd = (Math.random() * 2.0 - 1.0) * 0.01;
    }

    @Override
    public void tick() {

        this.setSpriteFromAge(spriteSet);
        // Let super handle further movement. You may replace this with your own movement if needed.
        // You may also override move() if you only want to modify the built-in movement.
        this.oRoll = this.roll;
        this.roll += (20 - age * 4) * 0.05F;
        super.tick();
    }

    @Override
    public ParticleRenderType getRenderType() {
        return ParticleRenderType.PARTICLE_SHEET_LIT;
    }
}
