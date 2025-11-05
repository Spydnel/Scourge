package com.spydnel.scourge.features;

import com.mojang.serialization.Codec;
import com.spydnel.scourge.ScourgeBlocks;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Position;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;
import net.minecraft.world.level.levelgen.feature.configurations.BlockStateConfiguration;

import java.util.Arrays;

public class BoulderFeature extends Feature<BlockStateConfiguration> {

    private static final Block STONE = Blocks.STONE;
    public BoulderFeature(Codec<BlockStateConfiguration> codec) {
        super(codec);
    }

    public boolean place(FeaturePlaceContext<BlockStateConfiguration> context) {
        BlockPos blockPos = context.origin().below(2).south(2).east(2);
        WorldGenLevel level = context.level();
        RandomSource randomSource = context.random();

        BlockPos[] positions = new BlockPos[25];

        for (int i = 0; i < positions.length; i++) {
            int row = Mth.floor( i / 5f);
            positions[i] = blockPos.north(row).west(i - row * 5);
        }

        boolean[] isGround = new boolean[25];

        for (int i = 0; i < isGround.length; i++) {
            isGround[i] = isDirt(level.getBlockState(positions[i]));
        }

        if (isGround[12]) {

            for (int i = 0; i < positions.length; i++) {

                BlockPos centerPos = positions[12];
                int distance = (int) positions[i].distToCenterSqr(centerPos.getX(), centerPos.getY(), centerPos.getZ());
                if (4 - distance > randomSource.nextInt(1, 4)) {
                    pillar(level, positions[i], 4 - distance / 2, randomSource, context);
                }
            }

            return true;
        }
        return false;
    }

    private static void pillar(WorldGenLevel level, BlockPos blockPos, int height, RandomSource randomSource, FeaturePlaceContext<BlockStateConfiguration> context) {
        for (int i = 0; i < height; i++) {
            place(level, blockPos.above(i), STONE);
        }
        placeWithChance(level, blockPos.above(height), context.config().state.getBlock(), 80, randomSource);
    }

    private static void place(WorldGenLevel level, BlockPos blockPos, Block block) {
        //if (level.isEmptyBlock(blockPos)) {}
        level.setBlock(blockPos, block.defaultBlockState(), 0);
    }

    private static void placeWithChance(WorldGenLevel level, BlockPos blockPos, Block block, float chance, RandomSource randomSource) {
        //if (level.isEmptyBlock(blockPos)) {}
        if (randomSource.nextInt(100) < chance) {
            level.setBlock(blockPos, block.defaultBlockState(), 0);
        }
    }
}
