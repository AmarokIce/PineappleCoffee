package club.someoneice.pc.registry

import net.minecraft.registry.Registry
import net.minecraft.registry.RegistryKey
import net.minecraft.registry.tag.TagKey
import net.minecraft.util.Identifier

/**
 * Get or create a tag from TagKey. Just instance the TagManager or use static.
 */
class TagManager<T>(val modid: String, val type: RegistryKey<Registry<T>>) {
    fun getTag(modid:String = this.modid, name: String): TagKey<T> = TagKey.of(type, Identifier(modid, name))

    companion object {
        fun <T> getTag(type: RegistryKey<Registry<T>>, modid:String, name: String): TagKey<T> = TagKey.of(type, Identifier(modid, name))
        fun <T> getTag(type: RegistryKey<Registry<T>>, name: Identifier): TagKey<T> = TagKey.of(type, name)
    }
}