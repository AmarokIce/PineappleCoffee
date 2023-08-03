package club.someoneice.pc.registry

import com.google.common.collect.Lists
import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup
import net.minecraft.resources.ResourceLocation
import net.minecraft.world.item.CreativeModeTab
import net.minecraft.world.item.Item
import net.minecraft.world.item.ItemStack
import net.minecraft.world.level.ItemLike

class GroupObject internal constructor(private val name: String, private val itemIcon: ItemStack) {
    private var itemList = Lists.newArrayList<Item>()
    fun getName(modid: String): ResourceLocation = ResourceLocation(modid, name)
    fun addItem(item: ItemLike) = this.itemList.add(item.asItem())
    fun replaceItemList(list: ArrayList<Item>) {
        this.itemList = list
    }

    internal fun registryGroup(): CreativeModeTab =
        FabricItemGroup.builder().icon{ itemIcon }.displayItems {
                _, output -> this.itemList.forEach { output.accept(it) }
        }.build()
}