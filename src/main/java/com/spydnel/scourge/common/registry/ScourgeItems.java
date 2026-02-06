package com.spydnel.scourge.common.registry;

import com.spydnel.scourge.Scourge;
import net.minecraft.world.item.BlockItem;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;

public class ScourgeItems {
    public static final DeferredRegister.Items ITEMS = DeferredRegister.createItems(Scourge.MODID);

    public static final DeferredItem<BlockItem> CHISELED_STONE = ITEMS.registerSimpleBlockItem("chiseled_stone", ScourgeBlocks.CHISELED_STONE);
    public static final DeferredItem<BlockItem> MOSSY_CHISELED_STONE = ITEMS.registerSimpleBlockItem("mossy_chiseled_stone", ScourgeBlocks.MOSSY_CHISELED_STONE);
    public static final DeferredItem<BlockItem> STONE_PILLAR = ITEMS.registerSimpleBlockItem("stone_pillar", ScourgeBlocks.STONE_PILLAR);
    public static final DeferredItem<BlockItem> MOSSY_STONE_PILLAR = ITEMS.registerSimpleBlockItem("mossy_stone_pillar", ScourgeBlocks.MOSSY_STONE_PILLAR);
    public static final DeferredItem<BlockItem> STONE_GOLEM_HEAD = ITEMS.registerSimpleBlockItem("stone_golem_head", ScourgeBlocks.STONE_GOLEM_HEAD);
    public static final DeferredItem<BlockItem> FIELD_LICHEN = ITEMS.registerSimpleBlockItem("field_lichen", ScourgeBlocks.FIELD_LICHEN);
    public static final DeferredItem<BlockItem> HILL_LICHEN = ITEMS.registerSimpleBlockItem("hill_lichen", ScourgeBlocks.HILL_LICHEN);
    public static final DeferredItem<BlockItem> CLIFF_LICHEN = ITEMS.registerSimpleBlockItem("cliff_lichen", ScourgeBlocks.CLIFF_LICHEN);
}
