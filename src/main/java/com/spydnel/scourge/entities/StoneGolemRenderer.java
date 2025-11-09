package com.spydnel.scourge.entities;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.block.BlockRenderDispatcher;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Position;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.item.FallingBlockEntity;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.neoforge.client.RenderTypeHelper;
import net.neoforged.neoforge.client.model.data.ModelData;

import java.util.Iterator;

public class StoneGolemRenderer extends EntityRenderer<StoneGolem> {
    private final BlockRenderDispatcher dispatcher;

    public StoneGolemRenderer(EntityRendererProvider.Context context) {
        super(context);
        this.dispatcher = context.getBlockRenderDispatcher();
    }

    public void render(StoneGolem entity, float entityYaw, float partialTicks, PoseStack poseStack, MultiBufferSource buffer, int packedLight) {
        BlockState blockstate = Blocks.DARK_OAK_SLAB.defaultBlockState();

        poseStack.pushPose();
        renderBlock(Blocks.DARK_OAK_PLANKS.defaultBlockState(), entity, poseStack, buffer);
        poseStack.translate(0, 1, 0);
        renderBlock(Blocks.WAXED_EXPOSED_CUT_COPPER_STAIRS.defaultBlockState(), entity, poseStack, buffer);
        poseStack.translate(0, 1, 0);
        renderBlock(Blocks.SPONGE.defaultBlockState(), entity, poseStack, buffer);

        poseStack.popPose();
        super.render(entity, entityYaw, partialTicks, poseStack, buffer, packedLight);
    }

    public void renderBlock(BlockState blockstate, StoneGolem entity, PoseStack poseStack, MultiBufferSource buffer) {
        if (blockstate.getRenderShape() == RenderShape.MODEL) {
            Level level = entity.level();
            if (blockstate != level.getBlockState(entity.blockPosition()) && blockstate.getRenderShape() != RenderShape.INVISIBLE) {
                BlockPos blockpos = BlockPos.containing(entity.getX(), entity.getBoundingBox().maxY, entity.getZ());
                poseStack.translate(-0.5, 0.0, -0.5);
                BakedModel model = this.dispatcher.getBlockModel(blockstate);
                Iterator var11 = model.getRenderTypes(blockstate, RandomSource.create(blockstate.getSeed(BlockPos.ZERO)), ModelData.EMPTY).iterator();
                while(var11.hasNext()) {
                    RenderType renderType = (RenderType)var11.next();
                    this.dispatcher.getModelRenderer().tesselateBlock(
                            level,
                            this.dispatcher.getBlockModel(blockstate),
                            blockstate,
                            blockpos,
                            poseStack,
                            buffer.getBuffer(RenderTypeHelper.getMovingBlockRenderType(renderType)),
                            false,
                            RandomSource.create(),
                            blockstate.getSeed(BlockPos.ZERO),
                            OverlayTexture.NO_OVERLAY, ModelData.EMPTY, renderType);
                }
                poseStack.translate(0.5, 0.0, 0.5);
            }
        }
    }

    @Override
    public ResourceLocation getTextureLocation(StoneGolem stoneGolem) {
        return null;
    }
}
