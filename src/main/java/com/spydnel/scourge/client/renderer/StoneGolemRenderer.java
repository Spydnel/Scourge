package com.spydnel.scourge.client.renderer;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.datafixers.util.Pair;
import com.mojang.math.Axis;
import com.spydnel.scourge.Scourge;
import com.spydnel.scourge.client.model.StoneGolemModel;
import com.spydnel.scourge.client.registry.ScourgeLayers;
import com.spydnel.scourge.common.entities.StoneGolem;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.geom.EntityModelSet;
import net.minecraft.client.renderer.BlockEntityWithoutLevelRenderer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.block.BlockRenderDispatcher;
import net.minecraft.client.renderer.blockentity.BellRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderDispatcher;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.entity.BellBlockEntity;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import org.jetbrains.annotations.NotNull;

import java.util.Iterator;

@OnlyIn(Dist.CLIENT)
public class StoneGolemRenderer extends MobRenderer<StoneGolem, StoneGolemModel<StoneGolem>> {
    private final BlockRenderDispatcher blockRenderDispatcher;
    private final BlockEntityRenderDispatcher blockEntityRenderDispatcher;
    private final BellBlockEntity bellBlockEntity;

    public StoneGolemRenderer(EntityRendererProvider.Context context) {
        super(context, new StoneGolemModel<>(context.bakeLayer(ScourgeLayers.STONE_GOLEM_LAYER)), 1);
        this.blockRenderDispatcher = context.getBlockRenderDispatcher();
        this.bellBlockEntity = new BellBlockEntity(BlockPos.ZERO, Blocks.BELL.defaultBlockState());
        this.blockEntityRenderDispatcher = Minecraft.getInstance().getBlockEntityRenderDispatcher();


    }


    public void render(@NotNull StoneGolem entity, float entityYaw, float partialTicks, PoseStack poseStack, MultiBufferSource buffer, int packedLight) {
        //.yBodyRot = 0;
        //entity.yBodyRotO = 0;
        super.render(entity, entityYaw, partialTicks, poseStack, buffer, packedLight);
        poseStack.pushPose();




        //poseStack.mulPose(Axis.XP.rotationDegrees(-90.0F));
        //poseStack.mulPose(Axis.YP.rotationDegrees(180.0F));

        float f = Mth.rotLerp(partialTicks, entity.yBodyRotO, entity.yBodyRot);
        poseStack.mulPose(Axis.YP.rotationDegrees(180f - f));

        this.getModel().translateToHead(poseStack);

        //poseStack.mulPose(Axis.YP.rotationDegrees(180.0F - entity.yBodyRot));
        if (entity.getBlocks() != null) {
            //Scourge.LOGGER.debug(entity.getBlocks().toString());
            Iterator<Pair<BlockState, BlockEntity>> iterator = entity.getBlocks().iterator();
            int i = 0;

            poseStack.translate(-1.5, -1.5, -1.5);
            while(iterator.hasNext()) {
                poseStack.pushPose();
                Pair<BlockState, BlockEntity> pair = iterator.next();
                BlockState blockState = pair.getFirst();
                BlockEntity blockEntity = pair.getSecond();
                int y = i / 9;
                int x = (i - y * 9) / 3;
                int z = (i - y * 9 - x * 3);
                poseStack.translate(x, y, z);
                if (!blockState.isEmpty() && blockState.getRenderShape() != RenderShape.ENTITYBLOCK_ANIMATED) {
                    renderBlock(blockState, packedLight, poseStack, buffer);
                }
                if (blockEntity != null) {
                    renderBlockEntity(blockEntity, poseStack, buffer, packedLight);
                }
                poseStack.popPose();
                i++;
            }
        } else  {
            //Scourge.LOGGER.debug("null");
        }
        poseStack.popPose();

    }

    public void renderBlockEntity(BlockEntity blockEntity, PoseStack poseStack, MultiBufferSource buffer, int packedLight) {
       //this.blockEntityRenderDispatcher.renderItem(blockEntity, poseStack, buffer, packedLight, OverlayTexture.NO_OVERLAY);
        BlockEntityRenderer renderer = this.blockEntityRenderDispatcher.getRenderer(blockEntity);
        if (renderer != null) {
            renderer.render(blockEntity, 0, poseStack, buffer, packedLight, OverlayTexture.NO_OVERLAY);
        }
    }

    public void renderBlock(BlockState blockstate, int packedLight, PoseStack poseStack, MultiBufferSource buffer) {
        this.blockRenderDispatcher.renderSingleBlock(blockstate, poseStack, buffer, packedLight, OverlayTexture.NO_OVERLAY);
    }

    @Override
    public @NotNull ResourceLocation getTextureLocation(StoneGolem stoneGolem) {
        return ResourceLocation.fromNamespaceAndPath(Scourge.MODID, "textures/entity/stone_golem.png");
    }

//    public class GolemBlockEntityRenderer extends BlockEntityWithoutLevelRenderer {
//        public GolemBlockEntityRenderer(BlockEntityRenderDispatcher blockEntityRenderDispatcher, EntityModelSet entityModelSet) {
//            super(Minecraft.getInstance().getBlockEntityRenderDispatcher(), Minecraft.getInstance().getEntityModels());
//        }
//    }
}
