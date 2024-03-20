package club.someoneice.pc.api

import club.someoneice.pc.event.bus.Event

/**
 * If you wanna to making an SubEvent cancel, mark this annotation on your SubEvent class.
 *
 * It will unlock function [Event.setCancel]
 * @see Event
 */
@Target(AnnotationTarget.CLASS)
@Retention(AnnotationRetention.RUNTIME)
annotation class Cancelable
