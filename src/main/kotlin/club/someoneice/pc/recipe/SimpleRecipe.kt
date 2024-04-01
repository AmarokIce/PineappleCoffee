package club.someoneice.pc.recipe

import net.minecraft.inventory.RecipeInputInventory
import net.minecraft.item.ItemStack
import net.minecraft.recipe.RepairItemRecipe
import net.minecraft.recipe.book.CraftingRecipeCategory
import net.minecraft.registry.DynamicRegistryManager
import net.minecraft.world.World

open class SimpleRecipe(book: CraftingRecipeCategory = CraftingRecipeCategory.MISC,
                   val match: (inv: RecipeInputInventory, world: World) -> Boolean?,
                   val craft: (inv: RecipeInputInventory, assets: DynamicRegistryManager) -> ItemStack?,
                   val size: (x: Int, y: Int) -> Boolean = { _, _ -> true},
    ): RepairItemRecipe(book) {
    internal constructor(book: CraftingRecipeCategory): this(book, { _, _ -> null }, { _, _ -> null })

    override fun fits(width: Int, height: Int): Boolean {
        return this.size(width, height)
    }

    override fun matches(inv: RecipeInputInventory, level: World): Boolean {
        return this.match(inv, level) ?: super.matches(inv, level)
    }

    override fun craft(recipe: RecipeInputInventory, registryAccess: DynamicRegistryManager): ItemStack {
        return this.craft(recipe, registryAccess) ?: super.craft(recipe, registryAccess)
    }
}