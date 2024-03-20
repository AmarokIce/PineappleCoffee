package club.someoneice.pc.util

import club.someoneice.pc.registry.GroupObject
import net.minecraft.network.chat.Component
import net.minecraft.world.effect.MobEffect
import net.minecraft.world.effect.MobEffectInstance
import net.minecraft.world.item.Item
import net.minecraft.world.item.ItemStack
import net.minecraft.world.level.ItemLike
import net.minecraft.world.level.block.Block
import java.util.*

fun random(): Random = Random()

/* Item Util */
fun ItemLike.asItemStack(size: Int = 1): ItemStack = ItemStack(this, size)
fun Item.setTab(tab: GroupObject): Item {
    tab.addItem(this)
    return this
}

/* MobEffect Util */
fun MobEffect.instance(time: Int, amplifier: Int = 0): MobEffectInstance = MobEffectInstance(this, time, amplifier)

/* String Chat Util */
fun String.translatable(): Component = Component.translatable(this)
fun String.literal(): Component = Component.literal(this)

/* Other Util */
fun getItemByBlock(block: Block): Item? = Item.BY_BLOCK[block]
fun Item.getBlockBySelf(): Block = Block.byItem(this)

fun <K, V> HashMap<K, V>.getKeyByValue(value: V): K? = this.keys.stream().filter { this[it] == value } .findFirst().get()
fun <K, V> HashMap<K, V>.getOrSet(key: K, value: V): V {
    return if (this.containsKey(key)) this[key]!!
    else this.setWithReturn(key, value)
}

fun <K, V> HashMap<K, V>.setWithReturn(key: K, value: V): V {
    this[key] = value
    return value
}