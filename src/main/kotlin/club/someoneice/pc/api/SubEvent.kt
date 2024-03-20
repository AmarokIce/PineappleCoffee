package club.someoneice.pc.api

import club.someoneice.pc.event.bus.EventBus

@Target(AnnotationTarget.FUNCTION)
@Retention(AnnotationRetention.RUNTIME)
annotation class SubEvent(val value: EventBus.EventPriority = EventBus.EventPriority.COMMON)
