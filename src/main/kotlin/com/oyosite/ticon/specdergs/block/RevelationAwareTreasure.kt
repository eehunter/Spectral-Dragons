package com.oyosite.ticon.specdergs.block

import by.dragonsurvivalteam.dragonsurvival.common.blocks.TreasureBlock
import de.dafuqs.revelationary.api.revelations.RevelationAware
import net.minecraft.resources.ResourceLocation
import software.bernie.geckolib.core.`object`.Color

class RevelationAwareTreasure(color: Color, settings: Properties) : TreasureBlock(color, settings), RevelationAware {
    override fun getCloakAdvancementIdentifier(): ResourceLocation {
        TODO("Not yet implemented")
    }

    override fun getBlockStateCloaks(): MutableMap<net.minecraft.class_2680, net.minecraft.class_2680> {
        TODO("Not yet implemented")
    }

    override fun getItemCloak(): net.minecraft.class_3545? {
        TODO("Not yet implemented")
    }
}