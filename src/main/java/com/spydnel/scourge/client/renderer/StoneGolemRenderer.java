package com.spydnel.scourge.client.renderer;

import com.mojang.blaze3d.vertex.PoseStack;
import com.spydnel.scourge.Scourge;
import com.spydnel.scourge.client.model.StoneGolemModel;
import com.spydnel.scourge.client.registry.ScourgeLayers;
import com.spydnel.scourge.common.entities.StoneGolem;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.block.BlockRenderDispatcher;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import net.neoforged.neoforge.client.RenderTypeHelper;
import net.neoforged.neoforge.client.model.data.ModelData;
import org.jetbrains.annotations.NotNull;

import java.util.Iterator;

@OnlyIn(Dist.CLIENT)
public class StoneGolemRenderer extends MobRenderer<StoneGolem, StoneGolemModel<StoneGolem>> {
    private final BlockRenderDispatcher dispatcher;

    public StoneGolemRenderer(EntityRendererProvider.Context context) {
        super(context, new StoneGolemModel<>(context.bakeLayer(ScourgeLayers.STONE_GOLEM_LAYER)), 1);
        this.dispatcher = context.getBlockRenderDispatcher();
    }


    public void render(@NotNull StoneGolem entity, float entityYaw, float partialTicks, PoseStack poseStack, MultiBufferSource buffer, int packedLight) {

        super.render(entity, entityYaw, partialTicks, poseStack, buffer, packedLight);
        poseStack.pushPose();
        this.setupRotations(entity, poseStack, 1, entityYaw, partialTicks, 1);

        Iterator<BlockState> iterator = entity.getBlocks().iterator();
        int i = 0;
        while(iterator.hasNext()) {
            BlockState blockState = (BlockState) iterator.next();
            int y = i / 9;
            int x = (i - y) / 3;
            int z = (i - y - x);
            if (!blockState.isEmpty()) {
                poseStack.translate(x - 1, y, z - 1);
                renderBlock(blockState, entity, poseStack, buffer);
            }
            poseStack.clear();
            i++;
        }






        poseStack.popPose();

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
    public @NotNull ResourceLocation getTextureLocation(StoneGolem stoneGolem) {
        return ResourceLocation.fromNamespaceAndPath(Scourge.MODID, "textures/entity/stone_golem.png");
    }
}
