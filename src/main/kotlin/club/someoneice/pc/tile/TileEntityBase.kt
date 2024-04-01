package club.someoneice.pc.tile

import club.someoneice.pc.PCMain
import club.someoneice.pc.api.SaveToNbt
import net.minecraft.block.BlockState
import net.minecraft.block.entity.BlockEntity
import net.minecraft.block.entity.BlockEntityType
import net.minecraft.nbt.NbtCompound
import net.minecraft.util.math.BlockPos
import java.lang.reflect.Field

abstract class TileEntityBase(type: BlockEntityType<*>, pos: BlockPos, state: BlockState): BlockEntity(type, pos, state) {
    abstract fun writeToNbt(nbt: NbtCompound)
    abstract fun readFromNbt(nbt: NbtCompound)

    override fun readNbt(nbt: NbtCompound) {
        super.readNbt(nbt)
        for (field in this.javaClass.declaredFields) {
            try                  { getFromNBT(field!!, nbt) }
            catch (e: Exception) { PCMain.LOGGER.error(e)   }
        }
        this.readFromNbt(nbt)
    }

    override fun writeNbt(nbt: NbtCompound) {
        super.writeNbt(nbt)
        for (field in this.javaClass.declaredFields) {
            try                  { putToNBT(field, nbt)   }
            catch (e: Exception) { PCMain.LOGGER.error(e) }
        }
        this.writeToNbt(nbt)
    }

    @Throws(IllegalAccessException::class)
    private fun putToNBT(field: Field, nbt: NbtCompound) {
        field.isAccessible = true
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
    private fun getFromNBT(field: Field, nbt: NbtCompound) {
        field.isAccessible = true
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