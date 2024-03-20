package club.someoneice.pc.api


@Target(AnnotationTarget.FIELD)
@Retention(AnnotationRetention.RUNTIME)
annotation class SaveToNbt(val value: String)