package com.spydnel.scourge.common.entities;

import com.mojang.datafixers.util.Pair;
import com.spydnel.scourge.Scourge;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderers;
import net.minecraft.core.*;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.NbtUtils;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.ItemTags;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.control.LookControl;
import net.minecraft.world.entity.ai.control.MoveControl;
import net.minecraft.world.entity.ai.goal.*;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.ai.navigation.GroundPathNavigation;
import net.minecraft.world.entity.ai.navigation.PathNavigation;
import net.minecraft.world.entity.animal.IronGolem;
import net.minecraft.world.entity.animal.camel.Camel;
import net.minecraft.world.entity.npc.AbstractVillager;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.raid.Raider;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.LeavesBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.pathfinder.Node;
import net.minecraft.world.level.pathfinder.PathFinder;
import net.minecraft.world.level.pathfinder.WalkNodeEvaluator;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.neoforged.neoforge.attachment.AttachmentType;
import net.neoforged.neoforge.event.EventHooks;

import java.util.Iterator;
import java.util.List;
import java.util.Optional;

public class StoneGolem extends PathfinderMob {
    private EatBlockGoal eatBlockGoal;
    private NonNullList<BlockState> blocks;

    public static final EntityDataAccessor<CompoundTag> BLOCKS = SynchedEntityData.defineId(StoneGolem.class, EntityDataSerializers.COMPOUND_TAG);
    //public static final EntityDataAccessor<CompoundTag> BLOCK_ENTITIES = SynchedEntityData.defineId(StoneGolem.class, EntityDataSerializers.COMPOUND_TAG);

    public StoneGolem(EntityType<StoneGolem> entityType, Level level) {
        super(entityType, level);
        this.moveControl = new StoneGolemMoveControl();

    }



    protected void playStepSound(BlockPos pos, BlockState block) {
        this.playSound(SoundEvents.DEEPSLATE_PLACE, 0.3F, 0.5F);
    }

    @Override
    public void checkDespawn() {}

    @Override
    protected void defineSynchedData(SynchedEntityData.Builder builder) {
        super.defineSynchedData(builder);
        builder.define(BLOCKS, new CompoundTag());
        //builder.define(BLOCK_ENTITIES, new CompoundTag());
    }

    public List<BlockState> getBlocks() {
        CompoundTag data = this.getEntityData().get(BLOCKS);
        NonNullList<BlockState> list = NonNullList.withSize(36, Blocks.AIR.defaultBlockState());

        if (data.contains("Blocks")) {

            ListTag listTag = data.getList("Blocks", 10);

            for(int i = 0; i < list.size(); i++) {
                CompoundTag compoundtag = listTag.getCompound(i);
                list.set(i, NbtUtils.readBlockState(this.level().holderLookup(Registries.BLOCK), compoundtag));
            }


            return list;
        }

        return NonNullList.withSize(36, Blocks.MAGENTA_CONCRETE.defaultBlockState());
    }

    public void setBlocks(NonNullList<BlockState> blocks) {
        ListTag listtag = new ListTag();
        Iterator iterator = blocks.iterator();

        while(iterator.hasNext()) {
            BlockState blockState = (BlockState) iterator.next();
            if (!blockState.isEmpty()) {
                listtag.add(NbtUtils.writeBlockState(blockState));
            } else {
                listtag.add(new CompoundTag());
            }
        }

        CompoundTag newData = new CompoundTag();
        newData.put("Blocks", listtag);
        this.getEntityData().set(BLOCKS, newData);
    }

//    public List<BlockEntity> getBlockEntities() {
//        List<BlockState> blockStates = this.getBlocks();
//
//        CompoundTag data = this.getEntityData().get(BLOCKS);
//        BlockEntity[] list = new BlockEntity[36];
//
//        if (data.contains("BlockEntities")) {
//
//            ListTag listTag = data.getList("BlockEntities", 10);
//
//            for(int i = 0; i < list.length; i++) {
//                BlockState b = blockStates.get(i);
//                if (b.hasBlockEntity()) {
//                    list[i] = ((EntityBlock)b.getBlock()).newBlockEntity(BlockPos.ZERO, b);
//                }
//            }
//
//
//            return List.of(list);
//        }
//        return List.of();
//    }
//
//    public void setBlockEntities(NonNullList<BlockEntity> blockEntities) {
//        ListTag listtag = new ListTag();
//        Iterator iterator = blockEntities.iterator();
//
//        while(iterator.hasNext()) {
//            BlockEntity blockEntity = (BlockEntity) iterator.next();
//            if (blockEntity != null) {
//                listtag.add(blockEntity.getPersistentData());
//            } else {
//                listtag.add(new CompoundTag());
//            }
//        }
//
//        CompoundTag newData = new CompoundTag();
//        newData.put("BlockEntities", listtag);
//        this.getEntityData().set(BLOCKS, newData);
//    }

//    private static CompoundTag writeBlockEntity(BlockEntity blockEntity) {
//        CompoundTag compoundtag = new CompoundTag();
////        compoundtag.putString("Name", BuiltInRegistries.BLOCK_ENTITY_TYPE.getKey(blockEntity.getType()).toString());
//        compoundtag.put("Data", blockEntity.getPersistentData());
//
//        return compoundtag;
//    }
//
//    private static BlockEntity readBlockEntity(CompoundTag tag) {
////        if (!tag.contains("Name")) {
////            return null;
////        }
////        ResourceLocation resourceLocation = ResourceLocation.parse(tag.getString("Name"));
////        Optional<? extends Holder<BlockEntityType<?>>> optional = blockEntityGetter.get(ResourceKey.create(Registries.BLOCK_ENTITY_TYPE, resourceLocation));
////        if (optional.isEmpty()) {
////            return null;
////        } else {
////            BlockEntityType<?> blockEntityType = optional.get().value();
////            CompoundTag compoundTag = tag.getCompound("Data");
////            return new Pair<BlockEntityType<?>, CompoundTag>(blockEntityType, compoundTag);
////        }
//
//    }



    public void addAdditionalSaveData (CompoundTag compound) {
        super.addAdditionalSaveData(compound);
        CompoundTag data = this.getEntityData().get(BLOCKS);
        if (data.contains("Blocks")) {
            compound.put("Blocks", data.getList("Blocks", 10));
        }
    }

    public void readAdditionalSaveData(CompoundTag compound) {
        if (compound.contains("Blocks", 9)) {
            ListTag listtag;
            listtag = compound.getList("Blocks", 10);

            CompoundTag newData = new CompoundTag();
            newData.put("Blocks", listtag);
            this.getEntityData().set(BLOCKS, newData);
        }
    }

    protected void registerGoals()
    {
        super.registerGoals();
        this.goalSelector.addGoal(0, new FloatGoal(this));
        this.goalSelector.addGoal(4, new MeleeAttackGoal(this, 1.0, true));
        this.goalSelector.addGoal(5, new WaterAvoidingRandomStrollGoal(this, 0.4));
        this.goalSelector.addGoal(6, new LookAtPlayerGoal(this, Player.class, 6.0F));
        //this.goalSelector.addGoal(10, new LookAtPlayerGoal(this, Mob.class, 8.0F));
        this.targetSelector.addGoal(3, new NearestAttackableTargetGoal<>(this, Player.class, true));
        this.targetSelector.addGoal(4, new NearestAttackableTargetGoal<>(this, IronGolem.class, true));
    }

    public int getHeadRotSpeed() {
        return 3;
    }

    public void aiStep() {
        super.aiStep();
        if (this.isAlive()) {

            if (this.horizontalCollision && EventHooks.canEntityGrief(this.level(), this)) {
                boolean flag = false;
                AABB aabb = this.getBoundingBox().inflate(0.2);
                Iterator var8 = BlockPos.betweenClosed(Mth.floor(aabb.minX), Mth.floor(aabb.minY), Mth.floor(aabb.minZ), Mth.floor(aabb.maxX), Mth.floor(aabb.maxY), Mth.floor(aabb.maxZ)).iterator();

                label62:
                while(true) {
                    BlockPos blockpos;
                    Block block;
                    do {
                        if (!var8.hasNext()) {
                            if (!flag && this.onGround()) {
                                this.jumpFromGround();
                            }
                            break label62;
                        }

                        blockpos = (BlockPos)var8.next();
                        BlockState blockstate = this.level().getBlockState(blockpos);
                        block = blockstate.getBlock();
                    } while(!(block instanceof LeavesBlock));

                    flag = this.level().destroyBlock(blockpos, true, this) || flag;
                }
            }
        }
    }

    class StoneGolemLookControl extends LookControl {
        StoneGolemLookControl() {
            super(StoneGolem.this);
        }

        public void tick() {
            super.tick();
        }
    }

    class StoneGolemMoveControl extends MoveControl {
        StoneGolemMoveControl() {
            super(StoneGolem.this);
        }

        public void tick() {
            if (this.operation == MoveControl.Operation.MOVE_TO) {
                this.operation = MoveControl.Operation.WAIT;
                double d0 = this.wantedX - this.mob.getX();
                double d1 = this.wantedZ - this.mob.getZ();
                double d2 = this.wantedY - this.mob.getY();
                double d3 = d0 * d0 + d2 * d2 + d1 * d1;
                if (d3 < 2.500000277905201E-7) {
                    this.mob.setZza(0.0F);
                    return;
                }

                float f9 = (float) (Mth.atan2(d1, d0) * 180.0 / 3.1415927410125732) - 90.0F;
                // alignment = (180 - Mth.degreesDifferenceAbs(this.mob.getYRot(), f9)) / 180;
                this.mob.setYRot(this.rotlerp(this.mob.getYRot(), f9, 5));
                this.mob.setSpeed((float) (this.speedModifier * this.mob.getAttributeValue(Attributes.MOVEMENT_SPEED)));
                BlockPos blockpos = this.mob.blockPosition();
                BlockState blockstate = this.mob.level().getBlockState(blockpos);
                VoxelShape voxelshape = blockstate.getCollisionShape(this.mob.level(), blockpos);
                if (d2 > (double) this.mob.maxUpStep() && d0 * d0 + d1 * d1 < (double) Math.max(1.0F, this.mob.getBbWidth()) || !voxelshape.isEmpty() && this.mob.getY() < voxelshape.max(Direction.Axis.Y) + (double) blockpos.getY() && !blockstate.is(BlockTags.DOORS) && !blockstate.is(BlockTags.FENCES)) {
                    this.mob.getJumpControl().jump();
                    this.operation = MoveControl.Operation.JUMPING;
                }
            } else {
                super.tick();
            }
        }
    }


//    public int getHeadRotSpeed() {
//        return 0;
//    }

//    public void turn(double yRot, double xRot) {
//        float f = (float)xRot * 0.0F;
//        float f1 = (float)yRot * 0.0F;
//        this.setXRot(this.getXRot() + f);
//        this.setYRot(this.getYRot() + f1);
//        this.setXRot(Mth.clamp(this.getXRot(), -90.0F, 90.0F));
//        this.xRotO += f;
//        this.yRotO += f1;
//        this.xRotO = Mth.clamp(this.xRotO, -90.0F, 90.0F);
//    }

    public static AttributeSupplier.Builder createAttributes() {
        return Mob.createMobAttributes()
                .add(Attributes.MAX_HEALTH, 8.0)
                .add(Attributes.MOVEMENT_SPEED, 0.2)
                .add(Attributes.ATTACK_DAMAGE, 1)
                .add(Attributes.STEP_HEIGHT, 1);
    }
}
