package com.spydnel.scourge.features;

import com.mojang.serialization.Codec;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;
import net.minecraft.world.level.levelgen.feature.configurations.BlockStateConfiguration;

public class BoulderFeature extends Feature<BlockStateConfiguration> {
    public BoulderFeature(Codec<BlockStateConfiguration> codec) {
        super(codec);
    }

    public boolean place(FeaturePlaceContext<BlockStateConfiguration> context) {
        BlockPos blockPos = context.origin();
        WorldGenLevel level = context.level();

        tryPlace(level, blockPos);
        tryPlace(level, blockPos.north());
        tryPlace(level, blockPos.west());
        tryPlace(level, blockPos.north().west());

        blockPos = blockPos.above();

        tryPlace(level, blockPos);
        tryPlace(level, blockPos.north());
        tryPlace(level, blockPos.west());
        tryPlace(level, blockPos.north().west());

        return false;
    }

    private static void tryPlace(WorldGenLevel level, BlockPos blockPos) {
        //if (level.isEmptyBlock(blockPos)) {}
        level.setBlock(blockPos, Blocks.RED_TERRACOTTA.defaultBlockState(), 0);
    }
}
