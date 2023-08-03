package club.someoneice.pc.recipe

import net.minecraft.core.RegistryAccess
import net.minecraft.resources.ResourceLocation
import net.minecraft.world.inventory.CraftingContainer
import net.minecraft.world.item.ItemStack
import net.minecraft.world.item.crafting.CraftingBookCategory
import net.minecraft.world.item.crafting.RepairItemRecipe
import net.minecraft.world.level.Level

open class SimpleRecipe(name: ResourceLocation, book: CraftingBookCategory = CraftingBookCategory.MISC,
                   val match: (inv: CraftingContainer, world: Level) -> Boolean?,
                   val craft: (inv: CraftingContainer, assets: RegistryAccess) -> ItemStack?,
                   val size: (x: Int, y: Int) -> Boolean = { _, _ -> true},
    ): RepairItemRecipe(name, book) {
    internal constructor(name: ResourceLocation, book: CraftingBookCategory): this(name, book, { _, _ -> null }, { _, _ -> null })

    override fun canCraftInDimensions(width: Int, height: Int): Boolean {
        return this.size(width, height)
    }

    override fun matches(inv: CraftingContainer, level: Level): Boolean {
        return this.match(inv, level) ?: super.matches(inv, level)
    }

    override fun assemble(craftingContainer: CraftingContainer, registryAccess: RegistryAccess): ItemStack {
        return this.craft(craftingContainer, registryAccess) ?: super.assemble(craftingContainer, registryAccess)
    }
}