package club.someoneice.pc.registry

import net.minecraft.resources.ResourceLocation
import java.util.function.Supplier
import kotlin.properties.ReadOnlyProperty
import kotlin.reflect.KProperty

class DataObject<T>(val id: ResourceLocation, val value: Supplier<T>)
class RegistryObject<T>(private val registryObject: DataObject<T>) : ReadOnlyProperty<Any, T>, Supplier<T>, () -> T {
    override fun getValue(thisRef: Any, property: KProperty<*>): T = registryObject.value.get()
    override fun invoke(): T = registryObject.value.get()
    override fun get(): T = registryObject.value.get()
    fun getObject(): DataObject<T> = registryObject
}