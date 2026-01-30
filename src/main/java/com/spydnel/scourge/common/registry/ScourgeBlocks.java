package com.spydnel.scourge.common.registry;

import com.spydnel.scourge.Scourge;
import com.spydnel.scourge.common.blocks.GolemHeadBlock;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.neoforged.neoforge.registries.DeferredBlock;
import net.neoforged.neoforge.registries.DeferredRegister;

public class ScourgeBlocks {
    public static final DeferredRegister.Blocks BLOCKS = DeferredRegister.createBlocks(Scourge.MODID);

    public static final DeferredBlock<GlazedTerracottaBlock> CHISELED_STONE = BLOCKS.register(
            "chiseled_stone",
            () -> new GlazedTerracottaBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.STONE_BRICKS)));

    public static final DeferredBlock<GlazedTerracottaBlock> MOSSY_CHISELED_STONE = BLOCKS.register(
            "mossy_chiseled_stone",
            () -> new GlazedTerracottaBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.STONE_BRICKS)));

    public static final DeferredBlock<RotatedPillarBlock> STONE_PILLAR = BLOCKS.register(
            "stone_pillar",
            () -> new RotatedPillarBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.STONE_BRICKS)));

    public static final DeferredBlock<CarpetBlock> FIELD_LICHEN = BLOCKS.register(
            "field_lichen",
            () -> new CarpetBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.MOSS_CARPET).noOcclusion()));

    public static final DeferredBlock<CarpetBlock> HILL_LICHEN = BLOCKS.register(
            "hill_lichen",
            () -> new CarpetBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.MOSS_CARPET).noOcclusion()));

    public static final DeferredBlock<CarpetBlock> CLIFF_LICHEN = BLOCKS.register(
            "cliff_lichen",
            () -> new CarpetBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.MOSS_CARPET).noOcclusion()));


    public static final DeferredBlock<GolemHeadBlock> STONE_GOLEM_HEAD = BLOCKS.register(
            "stone_golem_head",
            () -> new GolemHeadBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.STONE)));
}
