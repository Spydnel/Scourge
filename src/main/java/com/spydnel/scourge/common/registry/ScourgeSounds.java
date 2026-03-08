package com.spydnel.scourge.common.registry;

import com.spydnel.scourge.Scourge;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.sounds.SoundEvent;
import net.neoforged.neoforge.registries.DeferredRegister;

public class ScourgeSounds {
    public static final DeferredRegister<SoundEvent> SOUND_EVENTS = DeferredRegister.create(BuiltInRegistries.SOUND_EVENT, Scourge.MODID);

    public static final Holder<SoundEvent> SWORD_BOUNCE = SOUND_EVENTS.register("item.sword.bounce", SoundEvent::createVariableRangeEvent);
    public static final Holder<SoundEvent> STONE_GOLEM_STAND = SOUND_EVENTS.register("entity.stone_golem.stand", SoundEvent::createVariableRangeEvent);
}
