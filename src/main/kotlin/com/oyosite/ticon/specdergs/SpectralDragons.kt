package com.oyosite.ticon.specdergs

import com.oyosite.ticon.specdergs.SpectralDragons.MODID
import com.oyosite.ticon.specdergs.block.DergBlocks
import com.oyosite.ticon.specdergs.item.DergItems
import net.minecraft.client.Minecraft
import net.minecraftforge.fml.common.Mod
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent
import net.minecraftforge.fml.event.lifecycle.FMLDedicatedServerSetupEvent
import thedarkcolour.kotlinforforge.forge.MOD_BUS
import thedarkcolour.kotlinforforge.forge.runForDist
import org.apache.logging.log4j.Level
import org.apache.logging.log4j.LogManager
import org.apache.logging.log4j.Logger


@Mod(MODID)
object SpectralDragons {
    const val MODID: String = "specdergs"

    // the logger for our mod
    val LOGGER: Logger = LogManager.getLogger(MODID)

    init {
        LOGGER.log(Level.INFO, "Hello world!")

        MOD_BUS.addListener(::onCommonSetup)

        runForDist(
            clientTarget = {
                MOD_BUS.addListener(::onClientSetup)
            },
            serverTarget = {
                MOD_BUS.addListener(::onServerSetup)
            }
        )

    }

    private fun onCommonSetup(event: FMLCommonSetupEvent){
        DergItems.ITEMS.register(MOD_BUS)
        DergBlocks.BLOCKS.register(MOD_BUS)
    }

    private fun onClientSetup(event: FMLClientSetupEvent) {
        LOGGER.log(Level.INFO, "Initializing client...")
    }

    private fun onServerSetup(event: FMLDedicatedServerSetupEvent) {
        LOGGER.log(Level.INFO, "Server starting...")
    }
}