package com.spydnel.scourge.common.registry;

import com.spydnel.scourge.Scourge;
import com.spydnel.scourge.common.features.BoulderFeature;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.configurations.BlockStateConfiguration;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

public class ScourgeFeatures {
    public static final DeferredRegister<Feature<?>> FEATURES = DeferredRegister.create(BuiltInRegistries.FEATURE, Scourge.MODID);

    public static final Supplier<Feature<BlockStateConfiguration>> BOULDER = FEATURES.register(
            "boulder", () -> new BoulderFeature(BlockStateConfiguration.CODEC));


    //public static final ResourceKey<ConfiguredFeature<?, ?>>
}
