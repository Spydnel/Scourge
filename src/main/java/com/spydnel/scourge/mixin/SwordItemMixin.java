package com.spydnel.scourge.mixin;

import com.spydnel.scourge.Scourge;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.core.component.DataComponents;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.SwordItem;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.TieredItem;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;

@Mixin(SwordItem.class)
public abstract class SwordItemMixin extends TieredItem {
    public SwordItemMixin(Tier tier, Properties properties) {
        super(tier, properties);
    }
    
    //@Inject(method = "use", at = @At("HEAD"))
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand usedHand) {
        AABB box = player.getBoundingBox().inflate(1, 0.1, 1).move(player.getLookAngle().scale(1.2));
        level.getEntitiesOfClass(LivingEntity.class, box).forEach(e -> {
            if (e != player) {
                e.hurt(player.damageSources().playerAttack(player), (float) player.getAttribute(Attributes.ATTACK_DAMAGE).getValue() * 1.5F);
                Scourge.LOGGER.debug(String.valueOf(player.getAttribute(Attributes.ATTACK_DAMAGE).getValue()));
            }
        });
        return super.use(level, player, usedHand);
    };


}
