package club.someoneice.pc.example

import club.someoneice.pc.PCMain
import club.someoneice.pc.api.SubEvent

object ExampleEvent {
    private var isRunning = false

    @SubEvent
    fun clientTickEvent(event: TickEvent.ClientTickEvent) {
        if (!isRunning) {
            PCMain.LOGGER.info("Client Tick Event is Running!")
            isRunning = true
        }
    }
}