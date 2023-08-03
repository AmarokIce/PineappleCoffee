package club.someoneice.pc.registry

import com.google.common.collect.Maps
import net.minecraft.world.item.CreativeModeTab
import net.minecraft.world.item.ItemStack

class GroupManager(val modid: String): HashSet<GroupObject>() {
    fun registry(name: String, icon: ItemStack): GroupObject {
        val tab = GroupObject(name, icon)
        this.add(tab)
        return tab
    }

    fun registryAll(): HashMap<String, CreativeModeTab> {
        val list = Maps.newHashMap<String, CreativeModeTab>()
        this.forEach {
            list[it.getName(modid).toString()] = it.registryGroup()
        }
        return list;
    }
}