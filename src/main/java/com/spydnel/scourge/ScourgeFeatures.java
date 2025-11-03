package com.spydnel.scourge;

import com.spydnel.scourge.features.BoulderFeature;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.levelgen.feature.BlockBlobFeature;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.configurations.BlockStateConfiguration;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

public class ScourgeFeatures {
    public static final DeferredRegister<Feature<?>> FEATURES = DeferredRegister.create(BuiltInRegistries.FEATURE, Scourge.MODID);

    public static final Supplier<Feature<BlockStateConfiguration>> BOULDER = FEATURES.register(
            "boulder", () -> new BoulderFeature(BlockStateConfiguration.CODEC));


    //public static final ResourceKey<ConfiguredFeature<?, ?>>
}
