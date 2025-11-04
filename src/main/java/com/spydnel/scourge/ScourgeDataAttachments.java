package com.spydnel.scourge;

import com.mojang.serialization.Codec;
import net.neoforged.neoforge.attachment.AttachmentType;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.NeoForgeRegistries;

import java.util.function.Supplier;

public class ScourgeDataAttachments {
    public static final DeferredRegister<AttachmentType<?>> ATTACHMENT_TYPES = DeferredRegister.create(NeoForgeRegistries.ATTACHMENT_TYPES, Scourge.MODID);

    public static final Supplier<AttachmentType<Integer>> JUMP_TICKS = ATTACHMENT_TYPES.register(
            "jump_ticks", () -> AttachmentType.builder(() -> 0).serialize(Codec.INT).build()
    );

    public static final Supplier<AttachmentType<Integer>> ATTACK_TICKS = ATTACHMENT_TYPES.register(
            "attack_ticks", () -> AttachmentType.builder(() -> 0).serialize(Codec.INT).build()
    );
}
