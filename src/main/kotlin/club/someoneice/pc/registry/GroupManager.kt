package club.someoneice.pc.registry

import com.google.common.collect.Maps
import net.minecraft.core.Registry
import net.minecraft.core.registries.BuiltInRegistries
import net.minecraft.world.item.CreativeModeTab
import net.minecraft.world.item.ItemStack

@Suppress("unused")
class GroupManager(val modid: String): HashSet<GroupObject>() {
    fun registry(name: String, icon: ItemStack): GroupObject {
        val tab = GroupObject(name, icon)
        this.add(tab)
        return tab
    }

    fun registryAll(): HashMap<String, CreativeModeTab> {
        val list = Maps.newHashMap<String, CreativeModeTab>()
        this.forEach {
            val tab: CreativeModeTab = it.registryGroup()
            list[it.getName(modid).toString()] = tab

            Registry.register(BuiltInRegistries.CREATIVE_MODE_TAB, it.getName(modid).toString(), tab)
        }
        return list
    }
}