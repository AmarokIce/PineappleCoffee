package club.someoneice.pc.registry

import net.minecraft.core.Registry
import net.minecraft.resources.ResourceLocation
import java.util.function.Supplier
import kotlin.jvm.optionals.getOrNull

/**
 * A list save all the registry obj and registry in once.
 */
class RegistryManager<T>(val modid: String, private val registry: Registry<T>): HashSet<DataObject<T>>() {
    fun registry(name: String, value: Supplier<T>): RegistryObject<T> {
        val data = DataObject(ResourceLocation(modid, name), value)
        this.add(data)
        return RegistryObject(data)
    }

    fun registry(name: String, value: T): RegistryObject<T> {
        val data = DataObject(ResourceLocation(modid, name)) { value }
        this.add(data)
        return RegistryObject(data)
    }

    fun getObjectByName(name: String): T? {
        val id = ResourceLocation(this.modid, name)
        return this.stream().filter { it.id.toString() == id.toString() }.findFirst().getOrNull()?.value?.get()
    }

    fun registryAll() {
        this.forEach() { Registry.register(this.registry, it.id, it.value.get()) }
    }
}