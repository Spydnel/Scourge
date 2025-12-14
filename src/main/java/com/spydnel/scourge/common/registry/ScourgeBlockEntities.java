package com.spydnel.scourge.common.registry;

import com.spydnel.scourge.Scourge;
import com.spydnel.scourge.common.blockentities.GolemHeadBlockEntity;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

public class ScourgeBlockEntities
{
    public static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITY_TYPES = DeferredRegister.create(Registries.BLOCK_ENTITY_TYPE, Scourge.MODID);

    public static final Supplier<BlockEntityType<GolemHeadBlockEntity>> STONE_GOLEM_HEAD = BLOCK_ENTITY_TYPES.register(
            "my_block_entity",
            () -> BlockEntityType.Builder.of(
                            GolemHeadBlockEntity::new,
                            ScourgeBlocks.STONE_GOLEM_HEAD.get()
                    )
                    .build(null)
    );


}
