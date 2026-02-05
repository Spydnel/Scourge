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
import net.minecraft.util.Mth;
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

        PartDefinition root = partdefinition.addOrReplaceChild("root", CubeListBuilder.create().texOffs(64, 0).addBox(-8.0F, -28.0F, -8.0F, 16.0F, 16.0F, 16.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 24.0F, 0.0F));

        PartDefinition head = root.addOrReplaceChild("head", CubeListBuilder.create(), PartPose.offset(0.0F, -52.0F, 0.0F));

        PartDefinition leftArm = head.addOrReplaceChild("leftArm", CubeListBuilder.create().texOffs(0, 0).mirror().addBox(0.0F, -12.0F, -8.0F, 16.0F, 60.0F, 16.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offset(24.0F, 4.0F, 0.0F));

        PartDefinition rightArm = head.addOrReplaceChild("rightArm", CubeListBuilder.create().texOffs(0, 0).addBox(-16.0F, -12.0F, -8.0F, 16.0F, 60.0F, 16.0F, new CubeDeformation(0.0F)), PartPose.offset(-24.0F, 4.0F, 0.0F));

        PartDefinition rightLeg = root.addOrReplaceChild("rightLeg", CubeListBuilder.create().texOffs(64, 32).addBox(-9.0F, -11.0F, -6.0F, 9.0F, 28.0F, 12.0F, new CubeDeformation(0.0F)), PartPose.offset(-8.0F, -17.0F, 0.0F));

        PartDefinition leftLeg = root.addOrReplaceChild("leftLeg", CubeListBuilder.create().texOffs(64, 32).mirror().addBox(0.0F, -11.0F, -6.0F, 9.0F, 28.0F, 12.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offset(8.0F, -17.0F, 0.0F));

        return LayerDefinition.create(meshdefinition, 128, 128);
    }

    public void translateToHead(PoseStack poseStack) {
        //poseStack.translate(root.x / -16f, root.y / -16f, root.z / 16f);
        poseStack.mulPose((new Quaternionf()).rotationZYX(root.zRot, -root.yRot, -root.xRot));

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

        float targetRot = entity.getYHeadRot() * 0.017453292f;
        float currentRot = Mth.rotLerp(0.007f, root.yRot, targetRot);
        float currentHeadRot = Mth.rotLerp(0.02f, head.yRot + root.yRot, targetRot);

        this.root.yRot = currentRot;
        this.head.yRot = currentHeadRot - root.yRot;

        this.leftArm.xRot = head.yRot * 0.2f;
        this.rightArm.xRot = head.yRot * -0.2f;

        this.leftArm.zRot = Mth.abs(head.yRot) * -0.2f;
        this.rightArm.zRot = Mth.abs(head.yRot) * 0.2f;
    }
}
