package com.spydnel.scourge.features;

import com.mojang.serialization.Codec;
import com.spydnel.scourge.ScourgeBlocks;
import net.minecraft.core.BlockPos;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;
import net.minecraft.world.level.levelgen.feature.configurations.BlockStateConfiguration;

public class BoulderFeature extends Feature<BlockStateConfiguration> {

    Block STONE = Blocks.STONE;
    Block LICHEN = ScourgeBlocks.FIELD_LICHEN.get();
    public BoulderFeature(Codec<BlockStateConfiguration> codec) {
        super(codec);
    }

    public boolean place(FeaturePlaceContext<BlockStateConfiguration> context) {
        BlockPos blockPos = context.origin();
        WorldGenLevel level = context.level();
        RandomSource randomSource = context.random();

        place(level, blockPos, STONE);
        place(level, blockPos.north(), STONE);
        place(level, blockPos.west(), STONE);
        place(level, blockPos.north().west(), STONE);

        blockPos = blockPos.above();

        place(level, blockPos, STONE);
        place(level, blockPos.north(), STONE);
        place(level, blockPos.west(), STONE);
        place(level, blockPos.north().west(), STONE);

        blockPos = blockPos.above();

        placeWithChance(level, blockPos, LICHEN, 50, randomSource);
        placeWithChance(level, blockPos.north(), LICHEN, 50, randomSource);
        placeWithChance(level, blockPos.west(), LICHEN, 50, randomSource);
        placeWithChance(level, blockPos.north().west(), LICHEN, 50, randomSource);

        return false;
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
