package com.spydnel.scourge;

import net.minecraft.core.Holder;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.sounds.SoundEvent;
import net.neoforged.neoforge.registries.DeferredRegister;

public class ScourgeSounds {
    public static final DeferredRegister<SoundEvent> SOUND_EVENTS = DeferredRegister.create(BuiltInRegistries.SOUND_EVENT, Scourge.MODID);

    public static final Holder<SoundEvent> SWORD_BOUNCE = SOUND_EVENTS.register("item.sword.bounce", SoundEvent::createVariableRangeEvent);
}
