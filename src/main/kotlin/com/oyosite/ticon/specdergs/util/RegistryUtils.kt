package com.oyosite.ticon.specdergs.util

import com.oyosite.ticon.specdergs.item.DergItems
import net.minecraft.world.item.Item
import net.minecraftforge.registries.DeferredRegister
import kotlin.reflect.full.declaredMemberProperties


inline fun <reified R: Any, reified T> R.autoRegistry(registry: DeferredRegister<T>)=
    R::class.declaredMemberProperties.mapNotNull{ (it.get(this) as? T)?.let{obj -> it.name.lowercase() to obj } }.associate{ it.first to registry.register(it.first){it.second} }

