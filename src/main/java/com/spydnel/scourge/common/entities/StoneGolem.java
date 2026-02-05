package com.spydnel.scourge.common.entities;

import net.minecraft.core.BlockPos;
import net.minecraft.core.NonNullList;
import net.minecraft.core.registries.Registries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.NbtUtils;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.tags.ItemTags;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.*;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.ai.navigation.GroundPathNavigation;
import net.minecraft.world.entity.ai.navigation.PathNavigation;
import net.minecraft.world.entity.animal.IronGolem;
import net.minecraft.world.entity.npc.AbstractVillager;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.raid.Raider;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.LeavesBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.pathfinder.Node;
import net.minecraft.world.level.pathfinder.PathFinder;
import net.minecraft.world.level.pathfinder.WalkNodeEvaluator;
import net.minecraft.world.phys.AABB;
import net.neoforged.neoforge.event.EventHooks;

import java.util.Iterator;

public class StoneGolem extends PathfinderMob {
    private EatBlockGoal eatBlockGoal;
    private NonNullList<BlockState> blocks;

    public static final EntityDataAccessor<CompoundTag> BLOCKS = SynchedEntityData.defineId(StoneGolem.class, EntityDataSerializers.COMPOUND_TAG);

    public StoneGolem(EntityType<StoneGolem> entityType, Level level) {
        super(entityType, level);
    }

    @Override
    protected void defineSynchedData(SynchedEntityData.Builder builder) {
        super.defineSynchedData(builder);
        builder.define(BLOCKS, new CompoundTag());
    }

    public Iterable<BlockState> getBlocks() {
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
                .add(Attributes.ATTACK_DAMAGE, 1);
    }
}
