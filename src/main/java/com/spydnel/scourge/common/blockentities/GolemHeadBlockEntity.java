package com.spydnel.scourge.common.blockentities;

import com.spydnel.scourge.common.blocks.GolemHeadBlock;
import com.spydnel.scourge.common.entities.StoneGolem;
import com.spydnel.scourge.common.registry.ScourgeBlockEntities;
import com.spydnel.scourge.common.registry.ScourgeEntities;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.NonNullList;
import net.minecraft.core.Vec3i;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.DirectionProperty;

public class GolemHeadBlockEntity extends BlockEntity {
    public GolemHeadBlockEntity( BlockPos pos, BlockState blockState) {
        super(ScourgeBlockEntities.STONE_GOLEM_HEAD.get(), pos, blockState);
    }

    public static <T extends BlockEntity> void tick(Level level, BlockPos pos, BlockState state, T blockEntity) {
        if (level.hasNearbyAlivePlayer(pos.getX() + 0.5, pos.getY() + 0.5, pos.getZ() + 0.5, 2))
        {
            //level.setBlockAndUpdate(pos, Blocks.REDSTONE_BLOCK.defaultBlockState());

            NonNullList<BlockState> blocks = NonNullList.withSize(36, Blocks.AIR.defaultBlockState());


            Direction direction = state.getValue(GolemHeadBlock.FACING);

            Direction xDir = direction;
            Direction zDir = direction.getClockWise();
            Direction yDir = Direction.UP;

            BlockPos startPos = pos.relative(yDir, -1);
            startPos = startPos.relative(zDir, -1);

            BlockPos blockPos = startPos;
            int i = 0;
            for (int x = 0; x < 3; x++) {
                for (int z = 0; z < 3 ; z++) {
                    for (int y = 0; y < 4 ; y++) {
                        blockPos = startPos.relative(xDir, x);
                        blockPos = startPos.relative(zDir, z);
                        blockPos = startPos.relative(yDir, y);

                        blocks.set(i, level.getBlockState(blockPos));
                        level.destroyBlock(blockPos, false);

                        i++;
                    }
                }
            }

            StoneGolem stoneGolem = new StoneGolem(ScourgeEntities.STONE_GOLEM.get(), level);
            stoneGolem.setBlocks(blocks);
            stoneGolem.setPos(pos.getX(), pos.getY(), pos.getZ());

            level.addFreshEntity(stoneGolem);
        }
    }
}
