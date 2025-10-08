package com.spydnel.scourge.events;

import com.spydnel.scourge.Scourge;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.phys.Vec3;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.neoforge.event.entity.living.LivingEvent;
import net.neoforged.neoforge.event.entity.player.AttackEntityEvent;
import net.neoforged.neoforge.event.entity.player.PlayerEvent;

public class CombatEvents {
    @SubscribeEvent
    public static void OnAttackEntity(AttackEntityEvent event) {
        Scourge.LOGGER.debug("ajndlflksfl");
        Player player = event.getEntity();
        if (!player.onGround()) {
            player.setDeltaMovement(new Vec3(0, 0.7, 0));
        }
    }
}
