package club.someoneice.pc.example

import club.someoneice.pc.event.bus.Event
import com.mojang.authlib.minecraft.client.MinecraftClient
import net.minecraft.server.MinecraftServer

open class TickEvent private constructor() : Event() {
    class ClientTickEvent(val client: MinecraftClient): TickEvent()
    class ServerTickEvent(val server: MinecraftServer): TickEvent()
    // class PlayerTickEvent: TickEvent()
    // class WorldTickEvent: TickEvent()
}
