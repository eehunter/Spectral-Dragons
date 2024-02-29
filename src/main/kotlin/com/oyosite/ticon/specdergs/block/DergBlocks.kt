package com.oyosite.ticon.specdergs.block

import by.dragonsurvivalteam.dragonsurvival.common.blocks.TreasureBlock
import com.oyosite.ticon.specdergs.SpectralDragons
import com.oyosite.ticon.specdergs.util.autoRegistry
import net.minecraft.world.level.block.Block
import net.minecraft.world.level.block.SoundType
import net.minecraft.world.level.block.state.BlockBehaviour
import net.minecraft.world.level.material.MapColor
import net.minecraftforge.registries.DeferredRegister
import net.minecraftforge.registries.ForgeRegistries
import software.bernie.geckolib.core.`object`.Color

object DergBlocks {
    val BLOCKS: DeferredRegister<Block> = DeferredRegister.create(ForgeRegistries.BLOCKS, SpectralDragons.MODID)






    fun treasure(color: Number, mapColor: MapColor, sound: SoundType, additionalSettings: BlockBehaviour.Properties.()->Unit) =
        TreasureBlock(Color.ofRGB(((color.toInt() shr 16) and 0xFF), ((color.toInt() shr 8) and 0xFF), (color.toInt() and 0xFF)),
            BlockBehaviour.Properties.of().mapColor(mapColor).noOcclusion().sound(sound).strength(0.5f).apply(additionalSettings))

    val REGISTRY_OBJECTS = autoRegistry(BLOCKS)
}