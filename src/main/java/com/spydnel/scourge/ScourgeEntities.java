package com.spydnel.scourge;

import com.spydnel.scourge.entities.StoneGolem;
import com.spydnel.scourge.entities.StoneGolemRenderer;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.EntityRenderersEvent;
import net.neoforged.neoforge.event.entity.EntityAttributeCreationEvent;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

@EventBusSubscriber
public class ScourgeEntities {
    public static final DeferredRegister<EntityType<?>> ENTITIES = DeferredRegister.create(Registries.ENTITY_TYPE, Scourge.MODID);

    public static final Supplier<EntityType<StoneGolem>> STONE_GOLEM = ENTITIES.register(
            "stone_golem",
            () -> EntityType.Builder.of(StoneGolem::new, MobCategory.MISC).sized(1, 1)
                    .build("stone_golem"));

    @SubscribeEvent
    //@OnlyIn(Dist.CLIENT)// on the mod event bus only on the physical client
    public static void registerEntityRenderers(EntityRenderersEvent.RegisterRenderers event) {
        event.registerEntityRenderer(STONE_GOLEM.get(), StoneGolemRenderer::new);
    }

    @SubscribeEvent // on the mod event bus
    public static void createDefaultAttributes(EntityAttributeCreationEvent event) {
        event.put(
                STONE_GOLEM.get(),
                StoneGolem.createAttributes().build()
        );
    }
}
