package club.someoneice.pc.registry

import com.google.common.collect.Maps
import net.minecraft.item.ItemGroup
import net.minecraft.item.ItemStack
import net.minecraft.registry.Registries
import net.minecraft.registry.Registry

@Suppress("unused")
class GroupManager(val modid: String): HashSet<GroupObject>() {
    fun registry(name: String, icon: ItemStack): GroupObject {
        val tab = GroupObject(name, icon)
        this.add(tab)
        return tab
    }

    fun registryAll(): HashMap<String, ItemGroup> {
        val list = Maps.newHashMap<String, ItemGroup>()
        this.forEach {
            val tab: ItemGroup = it.registryGroup()
            list[it.getName(modid).toString()] = tab

            Registry.register(Registries.ITEM_GROUP, it.getName(modid).toString(), tab)
        }
        return list
    }
}