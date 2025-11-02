package com.spydnel.scourge;

import net.minecraft.world.item.BlockItem;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;

public class ScourgeItems {
    public static final DeferredRegister.Items ITEMS = DeferredRegister.createItems(Scourge.MODID);

    public static final DeferredItem<BlockItem> CHISELED_STONE = ITEMS.registerSimpleBlockItem("chiseled_stone", ScourgeBlocks.CHISELED_STONE);
    public static final DeferredItem<BlockItem> STONE_PILLAR = ITEMS.registerSimpleBlockItem("stone_pillar", ScourgeBlocks.STONE_PILLAR);
}
