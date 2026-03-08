package com.spydnel.scourge.common.blockentities;

import com.mojang.datafixers.util.Pair;
import com.spydnel.scourge.common.blocks.GolemHeadBlock;
import com.spydnel.scourge.common.entities.StoneGolem;
import com.spydnel.scourge.common.registry.ScourgeBlockEntities;
import com.spydnel.scourge.common.registry.ScourgeEntities;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.NonNullList;
import net.minecraft.core.Vec3i;
import net.minecraft.gametest.framework.StructureUtils;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Pose;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.DirectionProperty;

import java.util.ArrayList;
import java.util.List;

public class GolemHeadBlockEntity extends BlockEntity {

    public boolean activated;
    public List<BlockPos> toRemove;

    public GolemHeadBlockEntity( BlockPos pos, BlockState blockState) {
        super(ScourgeBlockEntities.STONE_GOLEM_HEAD.get(), pos, blockState);
        this.activated = false;
        this.toRemove = new ArrayList<BlockPos>();
    }

    public static void tick(Level level, BlockPos pos, BlockState state, GolemHeadBlockEntity blockEntity) {

        if (blockEntity.activated) {
            blockEntity.toRemove.forEach((b) -> {
                level.setBlock(b, Blocks.AIR.defaultBlockState(), 2 | 16);
            });
        }

        if (level.hasNearbyAlivePlayer(pos.getX() + 0.5, pos.getY() + 0.5, pos.getZ() + 0.5, 8) && !blockEntity.activated)
        {
            //level.setBlockAndUpdate(pos, Blocks.REDSTONE_BLOCK.defaultBlockState());

            blockEntity.activated = true;

            NonNullList<Pair<BlockState, BlockEntity>> blocks = NonNullList.withSize(36, Pair.of(Blocks.AIR.defaultBlockState(), null));




            Direction direction = state.getValue(GolemHeadBlock.FACING);

            Direction xDir = direction.getOpposite();
            Direction zDir = direction.getClockWise();
            Direction yDir = Direction.UP;

            BlockPos startPos = pos.relative(yDir, -1);
            startPos = startPos.relative(zDir, -1);

            int rotation = 0;

            BlockPos blockPos = startPos;
            int i = 0;
            for (int y = 0; y < 4; y++) {
                for (int z = 0; z < 3 ; z++) {
                    for (int x = 0; x < 3 ; x++) {
                        blockPos = startPos.relative(xDir, x);
                        blockPos = blockPos.relative(zDir, z);
                        blockPos = blockPos.relative(yDir, y);

                        BlockState blockState = level.getBlockState(blockPos);
                        BlockEntity blockEntity1;

                        if (blockState.hasBlockEntity()) {
                            blockEntity1 = level.getBlockEntity(blockPos);
                        } else {
                            blockEntity1 = null;
                        }




                        switch (direction) {
                            case EAST -> rotation = 3;
                            case SOUTH -> rotation = 2;
                            case WEST -> rotation = 1;
                        }

                        blockState = blockState.rotate(level, blockPos, StructureUtils.getRotationForRotationSteps(rotation));
                        blocks.set(i, Pair.of(blockState, blockEntity1));

                        //level.setBlock(blockPos, Blocks.AIR.defaultBlockState(), 2 | 16);

                        ((GolemHeadBlockEntity) blockEntity).toRemove.add(blockPos);

                        i++;
                    }
                }
            }

            StoneGolem stoneGolem = new StoneGolem(ScourgeEntities.STONE_GOLEM.get(), level);
            stoneGolem.setBlocks(blocks);
            BlockPos entityPos = pos.relative(xDir, 1);
            stoneGolem.setPos(entityPos.getX() + 0.5, entityPos.getY() -1, entityPos.getZ() + 0.5);
            stoneGolem.setYRot(direction.toYRot());
            stoneGolem.yHeadRot = stoneGolem.getYRot();
            stoneGolem.setPose(Pose.SITTING);

            level.addFreshEntity(stoneGolem);
        }
    }
}
