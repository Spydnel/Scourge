package com.spydnel.scourge.client.model;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Axis;
import com.spydnel.scourge.common.entities.StoneGolem;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.HierarchicalModel;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.animal.allay.Allay;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import org.joml.Quaternionf;

@OnlyIn(Dist.CLIENT)
public class StoneGolemModel<T extends LivingEntity> extends HierarchicalModel<T> {

    private final ModelPart root;
    private final ModelPart leftArm;
    private final ModelPart rightArm;
    private final ModelPart rightLeg;
    private final ModelPart leftLeg;
    private final ModelPart head;

    public StoneGolemModel(ModelPart root) {
        this.root = root.getChild("root");
        this.head = this.root.getChild("head");
        this.leftArm = this.head.getChild("leftArm");
        this.rightArm = this.head.getChild("rightArm");
        this.rightLeg = this.root.getChild("rightLeg");
        this.leftLeg = this.root.getChild("leftLeg");
    }

    public static LayerDefinition createBodyLayer() {
        MeshDefinition meshdefinition = new MeshDefinition();
        PartDefinition partdefinition = meshdefinition.getRoot();

        PartDefinition root = partdefinition.addOrReplaceChild("root", CubeListBuilder.create().texOffs(128, 96).addBox(-12.0F, -28.0F, -8.0F, 24.0F, 16.0F, 16.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 24.0F, 0.0F));

        PartDefinition head = root.addOrReplaceChild("head", CubeListBuilder.create().texOffs(0, 0).addBox(-24.0F, -24.0F, -24.0F, 48.0F, 48.0F, 48.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -52.0F, 0.0F));

        PartDefinition leftArm = head.addOrReplaceChild("leftArm", CubeListBuilder.create().texOffs(0, 96).addBox(-16.0F, -12.0F, -8.0F, 16.0F, 60.0F, 16.0F, new CubeDeformation(0.0F)), PartPose.offset(-24.0F, 4.0F, 0.0F));

        PartDefinition rightArm = head.addOrReplaceChild("rightArm", CubeListBuilder.create().texOffs(64, 96).addBox(0.0F, -12.0F, -8.0F, 16.0F, 60.0F, 16.0F, new CubeDeformation(0.0F)), PartPose.offset(24.0F, 4.0F, 0.0F));

        PartDefinition rightLeg = root.addOrReplaceChild("rightLeg", CubeListBuilder.create().texOffs(128, 128).addBox(-9.0F, -5.0F, -6.0F, 12.0F, 22.0F, 12.0F, new CubeDeformation(0.0F)), PartPose.offset(-8.0F, -17.0F, 0.0F));

        PartDefinition leftLeg = root.addOrReplaceChild("leftLeg", CubeListBuilder.create().texOffs(128, 162).addBox(-3.0F, -5.0F, -6.0F, 12.0F, 22.0F, 12.0F, new CubeDeformation(0.0F)), PartPose.offset(8.0F, -17.0F, 0.0F));

        return LayerDefinition.create(meshdefinition, 256, 256);
    }

    public void translateToHead(PoseStack poseStack) {
        poseStack.translate(head.x / -16f, head.y / -16f, head.z / 16f);
        poseStack.mulPose((new Quaternionf()).rotationZYX(head.zRot, -head.yRot, -head.xRot));
    }

//    @Override
//    public void renderToBuffer(PoseStack poseStack, VertexConsumer buffer, int packedLight, int packedOverlay, int color) {
//        this.root().render(poseStack, buffer, packedLight, packedOverlay, color);
//    }

    @Override
    public ModelPart root() {
        return this.root;
    }

    @Override
    public void setupAnim(T entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
        this.head.yRot = netHeadYaw * 0.017453292F;
        this.leftArm.xRot = netHeadYaw * -0.005f;
        this.rightArm.xRot = netHeadYaw * 0.005f;
    }
}
