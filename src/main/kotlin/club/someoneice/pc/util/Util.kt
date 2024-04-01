package club.someoneice.pc.util

import club.someoneice.pc.registry.GroupObject
import net.minecraft.block.Block
import net.minecraft.entity.effect.StatusEffect
import net.minecraft.entity.effect.StatusEffectInstance
import net.minecraft.item.Item
import net.minecraft.item.ItemConvertible
import net.minecraft.item.ItemStack
import net.minecraft.text.Text
import java.util.*

fun random(): Random = Random()

/* Item Util */
fun ItemConvertible.asItemStack(size: Int = 1): ItemStack = ItemStack(this, size)
fun Item.setTab(tab: GroupObject): Item {
    tab.addItem(this)
    return this
}

/* MobEffect Util */
fun StatusEffect.instance(time: Int, amplifier: Int = 0) = StatusEffectInstance(this, time, amplifier)

/* String Chat Util */
fun String.translatable(): Text = Text.translatable(this)
fun String.literal(): Text = Text.literal(this)

/* Other Util */
fun Item.getBlockBySelf(): Block = Block.getBlockFromItem(this)

fun <K, V> HashMap<K, V>.getKeyByValue(value: V): K? = this.keys.stream().filter { this[it] == value } .findFirst().get()
fun <K, V> HashMap<K, V>.getOrSet(key: K, value: V): V {
    return if (this.containsKey(key)) this[key]!!
    else this.setWithReturn(key, value)
}

fun <K, V> HashMap<K, V>.setWithReturn(key: K, value: V): V {
    this[key] = value
    return value
}