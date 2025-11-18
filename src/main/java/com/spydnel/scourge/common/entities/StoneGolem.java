package com.spydnel.scourge.common.entities;

import com.spydnel.scourge.common.registry.ScourgeBlocks;
import net.minecraft.core.NonNullList;
import net.minecraft.core.registries.Registries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.NbtUtils;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.*;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.Nullable;

import java.util.Iterator;

public class StoneGolem extends Animal {
    private EatBlockGoal eatBlockGoal;
    private final NonNullList<BlockState> blocks;

    public StoneGolem(EntityType<? extends Animal> entityType, Level level) {
        super(entityType, level);
        this.blocks = NonNullList.withSize(36, Blocks.STONE.defaultBlockState());
        this.blocks.set(26, ScourgeBlocks.FIELD_LICHEN.get().defaultBlockState());
        this.blocks.set(27, ScourgeBlocks.FIELD_LICHEN.get().defaultBlockState());
        this.blocks.set(28, ScourgeBlocks.FIELD_LICHEN.get().defaultBlockState());
        this.blocks.set(29, ScourgeBlocks.FIELD_LICHEN.get().defaultBlockState());
        this.blocks.set(30, ScourgeBlocks.FIELD_LICHEN.get().defaultBlockState());
        this.blocks.set(31, ScourgeBlocks.FIELD_LICHEN.get().defaultBlockState());
        this.blocks.set(32, ScourgeBlocks.FIELD_LICHEN.get().defaultBlockState());
        this.blocks.set(33, ScourgeBlocks.FIELD_LICHEN.get().defaultBlockState());
        this.blocks.set(34, ScourgeBlocks.FIELD_LICHEN.get().defaultBlockState());
        this.blocks.set(35, ScourgeBlocks.FIELD_LICHEN.get().defaultBlockState());
    }

    public Iterable<BlockState> getBlocks() {
        return this.blocks;
    }

    public void addAdditionalSaveData (CompoundTag compound) {
        super.addAdditionalSaveData(compound);
        ListTag listtag = new ListTag();
        Iterator iterator = this.blocks.iterator();

        while(iterator.hasNext()) {
            BlockState blockState = (BlockState) iterator.next();
            if (!blockState.isEmpty()) {
                listtag.add(NbtUtils.writeBlockState(blockState));
            } else {
                listtag.add(new CompoundTag());
            }
        }

        compound.put("Blocks", listtag);
    }

    public void readAdditionalSaveData(CompoundTag compound) {
        if (compound.contains("Blocks", 9)) {
            ListTag listtag;
            listtag = compound.getList("Blocks", 10);

            for(int i = 0; i < this.blocks.size(); ++i) {
                CompoundTag compoundtag = listtag.getCompound(i);
                this.blocks.set(i, NbtUtils.readBlockState(this.level().holderLookup(Registries.BLOCK), compoundtag));
            }
        }
    }

    @Override
    public boolean isFood(ItemStack itemStack) {
        return false;
    }

    protected void registerGoals()
    {
        super.registerGoals();
        this.eatBlockGoal = new EatBlockGoal(this);
        this.goalSelector.addGoal(0, new FloatGoal(this));
        this.goalSelector.addGoal(1, new PanicGoal(this, 1.25));
        this.goalSelector.addGoal(2, new BreedGoal(this, 1.0));
        this.goalSelector.addGoal(3, new TemptGoal(this, 1.1, (p_335259_) -> {
            return p_335259_.is(ItemTags.SHEEP_FOOD);
        }, false));
        this.goalSelector.addGoal(4, new FollowParentGoal(this, 1.1));
        this.goalSelector.addGoal(5, this.eatBlockGoal);
        this.goalSelector.addGoal(6, new WaterAvoidingRandomStrollGoal(this, 1.0));
        this.goalSelector.addGoal(7, new LookAtPlayerGoal(this, Player.class, 6.0F));
        this.goalSelector.addGoal(8, new RandomLookAroundGoal(this));
    }

    public static AttributeSupplier.Builder createAttributes() {
        return Mob.createMobAttributes().add(Attributes.MAX_HEALTH, 8.0).add(Attributes.MOVEMENT_SPEED, 0.23000000417232513);
    }

    @Nullable
    @Override
    public AgeableMob getBreedOffspring(ServerLevel serverLevel, AgeableMob ageableMob) {
        return null;
    }
}
