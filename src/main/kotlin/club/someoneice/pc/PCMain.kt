package club.someoneice.pc

import net.fabricmc.api.ModInitializer
import org.apache.logging.log4j.LogManager
import org.apache.logging.log4j.Logger

@Suppress("unused")
object PCMain: ModInitializer{
    private const val MODID: String = "pineapple_coffee"
    val LOGGER: Logger = LogManager.getLogger(MODID)

    override fun onInitialize() {
    }
}

fun init() {
    
}