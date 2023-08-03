package club.someoneice.pc.registry

import net.minecraft.core.Registry
import net.minecraft.resources.ResourceKey
import net.minecraft.resources.ResourceLocation
import net.minecraft.tags.TagKey

/**
 * Get or create a tag from TagKey. Just instance the TagManager or use static.
 */
class TagManager<T>(val modid: String, val type: ResourceKey<Registry<T>>) {
    fun getTag(modid:String = this.modid, name: String): TagKey<T> = TagKey.create(type, ResourceLocation(modid, name))

    companion object {
        fun <T> getTag(type: ResourceKey<Registry<T>>, modid:String, name: String): TagKey<T> = TagKey.create(type, ResourceLocation(modid, name))
        fun <T> getTag(type: ResourceKey<Registry<T>>, name: ResourceLocation): TagKey<T>     = TagKey.create(type, name)
    }
}