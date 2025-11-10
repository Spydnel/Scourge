package com.spydnel.scourge.common.entities;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.model.EntityModel;
import net.minecraft.world.entity.LivingEntity;

public class StoneGolemModel<T extends LivingEntity> extends EntityModel<StoneGolem> {
    @Override
    public void setupAnim(StoneGolem stoneGolem, float v, float v1, float v2, float v3, float v4) {
    }

    @Override
    public void renderToBuffer(PoseStack poseStack, VertexConsumer vertexConsumer, int i, int i1, int i2) {
    }
}
