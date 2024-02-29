package com.oyosite.ticon.specdergs.item

import com.oyosite.ticon.specdergs.SpectralDragons
import com.oyosite.ticon.specdergs.util.autoRegistry
import net.minecraft.world.item.Item
import net.minecraftforge.registries.DeferredRegister
import net.minecraftforge.registries.ForgeRegistries


object DergItems {
    val ITEMS: DeferredRegister<Item> = DeferredRegister.create(ForgeRegistries.ITEMS, SpectralDragons.MODID)







    val REGISTRY_OBJECTS = autoRegistry(ITEMS)// This is so cursed.

    init {
        //DergItems::class.declaredMemberProperties.mapNotNull{ (it.get(DergItems) as? Item)?.let{item -> it.name.lowercase() to item} }.map{ ITEMS.}

    }
}