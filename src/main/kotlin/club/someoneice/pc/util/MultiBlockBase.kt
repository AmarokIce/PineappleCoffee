package club.someoneice.pc.util

import net.minecraft.block.Block
import net.minecraft.block.Blocks
import net.minecraft.entity.player.PlayerEntity
import net.minecraft.util.math.BlockPos
import net.minecraft.util.math.Direction
import net.minecraft.world.World

@Suppress("unused")
object MultiBlockBase {
    /**
     * Chunk the multi block is done. The map is a matrix and the left-top is the origin. The ox and oz is the distance from block to origin.
     *
     * The list in map smell like this: <p />
     *
     * | 0, 1, 2, 3, 4| <br />
     * | 5, 6, 7, 8, 9| <br />
     * |10,11,12,13,14| <br />
     * |15,16,17,18,19| <br />
     * |20,21,22,23,24| <br />
     *
     * And the key of the map is the Y floor. <br />
     **/
    fun checkMultiBlock(maps: Map<Int, List<Block?>>, player: PlayerEntity, world: World, basePos: BlockPos, ox: Int, oz: Int, face: Direction = player.movementDirection, maxY: Int = maps.size, shouldRemoveBlock: Boolean): Boolean {
        fun chunkBlockInMap(pos: BlockPos, y: Int, size: Int): Boolean {
            val block = maps[y]!![size]
            return if (block == Blocks.AIR || block == null) true
            else world.getBlockState(pos) == block
        }

        when (face) {
            Direction.EAST -> {
                val originPos = BlockPos(basePos.x + ox, basePos.y, basePos.z - oz)
                for (y in 0 until maxY) {
                    var i = 0
                    for (x in 0 downTo -4)
                        for (z in 0..4)
                            if (!chunkBlockInMap(BlockPos(originPos.x + x, originPos.y + y, originPos.z + z), y, i++))
                                return false
                }
            }

            Direction.SOUTH -> {
                val originPos = BlockPos(basePos.x + oz, basePos.y, basePos.z + ox)
                for (y in 0 until maxY) {
                    var i = 0
                    for (x in 0 downTo -4)
                        for (z in 0 downTo -4)
                            if (!chunkBlockInMap(BlockPos(originPos.x + x, originPos.y + y, originPos.z + z), y, i++))
                                return false
                }
            }

            Direction.WEST -> {
                val originPos = BlockPos(basePos.x - ox, basePos.y, basePos.z + oz)
                for (y in 0 until maxY) {
                    var i = 0
                    for (x in 0..4)
                        for (z in 0 downTo -4)
                            if (!chunkBlockInMap(BlockPos(originPos.x + x, originPos.y + y, originPos.z + z), y, i++))
                                return false
                }
            }

            Direction.NORTH -> {
                val originPos = BlockPos(basePos.x - oz, basePos.y, basePos.z - ox)
                for (y in 0 until maxY) {
                    var i = 0
                    for (x in 0..4)
                        for (z in 0..4)
                            if (!chunkBlockInMap(BlockPos(originPos.x + x, originPos.y + y, originPos.z + z), y, i++))
                                return false
                }
            }

            else -> return false
        }

        if (shouldRemoveBlock) {
            when (face) {
                Direction.EAST -> {
                    val originPos = BlockPos(basePos.x + ox, basePos.y, basePos.z - oz)
                    for (y in 0 until maxY) {
                        for (x in 0 downTo -4)
                            for (z in 0..4)
                                world.removeBlock(BlockPos(originPos.x + x, originPos.y + y, originPos.z + z), false)
                    }
                }

                Direction.SOUTH -> {
                    val originPos = BlockPos(basePos.x + oz, basePos.y, basePos.z + ox)
                    for (y in 0 until maxY) {
                        for (x in 0 downTo -4)
                            for (z in 0 downTo -4)
                                world.removeBlock(BlockPos(originPos.x + x, originPos.y + y, originPos.z + z), false)
                    }
                }

                Direction.WEST -> {
                    val originPos = BlockPos(basePos.x - ox, basePos.y, basePos.z + oz)
                    for (y in 0 until maxY) {
                        for (x in 0..4)
                            for (z in 0 downTo -4)
                                world.removeBlock(BlockPos(originPos.x + x, originPos.y + y, originPos.z + z), false)
                    }
                }

                Direction.NORTH -> {
                    val originPos = BlockPos(basePos.x - oz, basePos.y, basePos.z - ox)
                    for (y in 0 until maxY) {
                        for (x in 0..4)
                            for (z in 0..4)
                                world.removeBlock(BlockPos(originPos.x + x, originPos.y + y, originPos.z + z), false)
                    }
                }

                else -> return false
            }
        }
        return true
    }
}