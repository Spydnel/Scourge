package com.spydnel.scourge.common.registry;

import com.spydnel.scourge.Scourge;
import com.spydnel.scourge.common.entities.StoneGolem;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.EntityAttributeCreationEvent;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

@EventBusSubscriber
public class ScourgeEntities {
    public static final DeferredRegister<EntityType<?>> ENTITIES = DeferredRegister.create(Registries.ENTITY_TYPE, Scourge.MODID);

    public static final Supplier<EntityType<StoneGolem>> STONE_GOLEM = ENTITIES.register(
            "stone_golem",
            () -> EntityType.Builder.of(StoneGolem::new, MobCategory.MISC).sized(3, 3)
                    .build("stone_golem"));



    @SubscribeEvent // on the mod event bus
    public static void createDefaultAttributes(EntityAttributeCreationEvent event) {
        event.put(
                STONE_GOLEM.get(),
                StoneGolem.createAttributes().build()
        );
    }
}
