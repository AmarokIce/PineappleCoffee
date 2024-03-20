package club.someoneice.pc.event.bus

object PostMan {
    @JvmStatic
    fun <T: Event> post(event: T): T {
        return EventBus.runEventSort(event)
    }

    @JvmStatic
    fun <T: Event> postClientOnly(event: T): T {
        TODO("TODO")
    }

    @JvmStatic
    fun <T: Event> postServerOnly(event: T): T {
        TODO("TODO")
    }
}