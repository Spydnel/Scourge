package com.spydnel.scourge.client.registry;

import com.spydnel.scourge.Scourge;
import com.spydnel.scourge.client.model.StoneGolemModel;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.EntityRenderersEvent;

@OnlyIn(Dist.CLIENT)
@EventBusSubscriber
public class ScourgeLayers {
    // Our ModelLayerLocation.
    public static final ModelLayerLocation STONE_GOLEM_LAYER = new ModelLayerLocation(
            // Should be the name of the entity this layer belongs to.
            // May be more generic if this layer can be used on multiple entities.
            ResourceLocation.fromNamespaceAndPath(Scourge.MODID, "stone_golem"),
            // The name of the layer itself. Should be main for the entity's base model,
            // and a more descriptive name (e.g. "wings") for more specific layers.
            "main"
    );

    @SubscribeEvent // on the mod event bus only on the physical client
    public static void registerLayerDefinitions(EntityRenderersEvent.RegisterLayerDefinitions event) {
        // Add our layer here.
        event.registerLayerDefinition(STONE_GOLEM_LAYER, StoneGolemModel::createBodyLayer);
    }
}
