package club.someoneice.pc

import net.fabricmc.api.ModInitializer
import org.apache.logging.log4j.LogManager
import org.apache.logging.log4j.Logger

@Suppress("unused")
object PCMain: ModInitializer{
    const val MODID: String = "pineapple_coffee"
    const val VERSION: String = "0.0.1"
    val LOGGER: Logger = LogManager.getLogger(MODID)

    override fun onInitialize() {
    }
}

fun init() {
    
}