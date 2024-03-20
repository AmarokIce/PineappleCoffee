package club.someoneice.pc.tile

import club.someoneice.pc.PCMain
import club.someoneice.pc.api.SaveToNbt
import net.minecraft.core.BlockPos
import net.minecraft.nbt.CompoundTag
import net.minecraft.world.level.block.entity.BlockEntity
import net.minecraft.world.level.block.entity.BlockEntityType
import net.minecraft.world.level.block.state.BlockState
import java.lang.reflect.Field

abstract class TileEntityBase(type: BlockEntityType<*>, pos: BlockPos, state: BlockState): BlockEntity(type, pos, state) {
    abstract fun writeToNbt(nbt: CompoundTag)
    abstract fun readFromNbt(nbt: CompoundTag)

    override fun load(nbt: CompoundTag) {
        super.load(nbt)
        for (field in this.javaClass.getDeclaredFields()) {
            try                  { getFromNBT(field!!, nbt) }
            catch (e: Exception) { PCMain.LOGGER.error(e)   }
        }
        this.readFromNbt(nbt)
    }

    override fun saveAdditional(nbt: CompoundTag) {
        super.saveAdditional(nbt)
        for (field in this.javaClass.getDeclaredFields()) {
            try                  { putToNBT(field, nbt)   }
            catch (e: Exception) { PCMain.LOGGER.error(e) }
        }
        this.writeToNbt(nbt)
    }

    @Throws(IllegalAccessException::class)
    private fun putToNBT(field: Field, nbt: CompoundTag) {
        field.setAccessible(true)
        if (!field.isAnnotationPresent(SaveToNbt::class.java)) return
        val name: String = field.getAnnotation(SaveToNbt::class.java).value
        when (val obj = field[this]) {
            is String   -> nbt.putString(name, obj)
            is Int      -> nbt.putInt(name, obj)
            is Float    -> nbt.putFloat(name, obj)
            is Double   -> nbt.putDouble(name, obj)
            is Boolean  -> nbt.putBoolean(name, obj)
        }
    }

    @Throws(IllegalAccessException::class)
    private fun getFromNBT(field: Field, nbt: CompoundTag) {
        field.setAccessible(true)
        if (!field.isAnnotationPresent(SaveToNbt::class.java)) return
        val name: String = field.getAnnotation(SaveToNbt::class.java).value
        when (field[this]) {
            is String   -> field.set(this, nbt.getString(name))
            is Int      -> field.set(this, nbt.getInt(name))
            is Float    -> field.set(this, nbt.getFloat(name))
            is Double   -> field.set(this, nbt.getDouble(name))
            is Boolean  -> field.set(this, nbt.getBoolean(name))
        }
    }
}