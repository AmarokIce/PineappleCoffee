package club.someoneice.pc.event.bus

import club.someoneice.pc.PCMain
import club.someoneice.pc.api.Cancelable

abstract class Event {
    var shouldCancel: Boolean = false

    fun setCancel(setCancel: Boolean) {
        if (this::class.java.isAnnotationPresent(Cancelable::class.java)) {
            this.shouldCancel = setCancel
        } else PCMain.LOGGER.warn("Cannot set cancel on event: ${this.javaClass.simpleName} because it's not a Cancelable SubEvent.")
    }
}