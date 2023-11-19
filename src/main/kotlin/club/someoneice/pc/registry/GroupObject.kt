package club.someoneice.pc.registry

import com.google.common.collect.Lists
import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup
import net.minecraft.item.Item
import net.minecraft.item.ItemConvertible
import net.minecraft.item.ItemGroup
import net.minecraft.item.ItemStack
import net.minecraft.text.Text
import net.minecraft.util.Identifier

class GroupObject internal constructor(private val name: String, private val itemIcon: ItemStack) {
    private var itemList = Lists.newArrayList<Item>()
    fun getName(modid: String): Identifier = Identifier(modid, name)
    fun addItem(item: ItemConvertible) = this.itemList.add(item.asItem())
    fun replaceItemList(list: ArrayList<Item>) {
        this.itemList = list
    }

    internal fun registryGroup(): ItemGroup =
        FabricItemGroup.builder().name(Text.translatable(name)).icon{ itemIcon }.entries() {
                _, output -> this.itemList.forEach { output.addItem(it) }
        }.build()
}