package com.spydnel.scourge.events;

import com.spydnel.scourge.Scourge;
import com.spydnel.scourge.ScourgeParticles;
import com.spydnel.scourge.ScourgeSounds;
import net.minecraft.client.Minecraft;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.phys.Vec3;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.neoforge.client.event.InputEvent;
import net.neoforged.neoforge.client.event.RenderHandEvent;
import net.neoforged.neoforge.event.entity.living.LivingEvent;
import net.neoforged.neoforge.event.entity.living.LivingKnockBackEvent;
import net.neoforged.neoforge.event.entity.player.AttackEntityEvent;
import net.neoforged.neoforge.event.entity.player.PlayerEvent;

public class CombatEvents {
    @SubscribeEvent
    public static void OnAttackEntity(AttackEntityEvent event) {
        Player player = event.getEntity();
        Vec3 pos = player.getEyePosition().add(player.getLookAngle().scale(1.6));
        if (!player.onGround() && player.isShiftKeyDown()) {
            player.setDeltaMovement(new Vec3(0, 0.65, 0));
            player.level().playSound(null, event.getTarget().blockPosition(), ScourgeSounds.SWORD_BOUNCE.value(), SoundSource.PLAYERS);
            player.level().addParticle(ScourgeParticles.SWORD_BOUNCE.get(),
                    pos.x,
                    pos.y + 1,
                    pos.z, 0, 0, 0);
        }
    }

    @SubscribeEvent
    public static void OnLivingKnockback(LivingKnockBackEvent event) {
        LivingEntity target = event.getEntity();
        LivingEntity attacker = target.getLastAttacker();
        if (!attacker.onGround()) {
            event.setStrength(0.1f);
        }
    }

    @SubscribeEvent
    public static void OnRenderHand(RenderHandEvent event) {

    }
}
