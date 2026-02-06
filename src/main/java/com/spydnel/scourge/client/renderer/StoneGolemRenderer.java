package com.spydnel.scourge.client.renderer;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
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

        Iterator<BlockState> iterator = entity.getBlocks().iterator();
        int i = 0;
        poseStack.translate(-1.5, -1.5, -1.5);
        while(iterator.hasNext()) {
            BlockState blockState = (BlockState) iterator.next();
            int y = i / 9;
            int x = (i - y * 9) / 3;
            int z = (i - y * 9 - x * 3);
            poseStack.translate(x, y, z);
            if (!blockState.isEmpty()) {
                renderBlock(blockState, packedLight, poseStack, buffer);
            }
            poseStack.translate(-x, -y, -z);
            i++;
        }






        poseStack.popPose();

    }

    public void renderBlock(BlockState blockstate, int packedLight, PoseStack poseStack, MultiBufferSource buffer) {
        this.dispatcher.renderSingleBlock(blockstate, poseStack, buffer, packedLight, OverlayTexture.NO_OVERLAY);
    }

    @Override
    public @NotNull ResourceLocation getTextureLocation(StoneGolem stoneGolem) {
        return ResourceLocation.fromNamespaceAndPath(Scourge.MODID, "textures/entity/stone_golem.png");
    }
}
