package com.oyosite.ticon.specdergs.block

import by.dragonsurvivalteam.dragonsurvival.common.blocks.TreasureBlock
import de.dafuqs.revelationary.api.revelations.RevelationAware
import net.minecraft.resources.ResourceLocation
import net.minecraft.util.Tuple
import net.minecraft.world.item.Item
import net.minecraft.world.level.block.state.BlockState
import software.bernie.geckolib.core.`object`.Color

class RevelationAwareTreasure(color: Color, settings: Properties) : TreasureBlock(color, settings), RevelationAware {
    override fun getCloakAdvancementIdentifier(): ResourceLocation {
        TODO("Not yet implemented")
    }

    override fun getBlockStateCloaks(): MutableMap<BlockState, BlockState> {
        TODO("Not yet implemented")
    }

    override fun getItemCloak(): Tuple<Item, Item>? {
        TODO("Not yet implemented")
    }
}