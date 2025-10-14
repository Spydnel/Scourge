package com.spydnel.scourge.mixin;

import com.spydnel.scourge.Scourge;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.SwordItem;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.TieredItem;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;

@Mixin(SwordItem.class)
public abstract class SwordItemMixin extends TieredItem {
    public SwordItemMixin(Tier tier, Properties properties) {
        super(tier, properties);
    }
    
    @Inject(method = "use", at = @At("HEAD"))
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand usedHand) {
        Scourge.LOGGER.debug("YIPPEEE");
        return super.use(level, player, usedHand);
    };


}
