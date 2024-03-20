package club.someoneice.pc.event

import club.someoneice.pc.event.bus.Event

@Suppress("unused")
class PineappleConfigEvent<T>(private val fileName: String?, private val configKey: String?, private var value: T, private val packageName: String?): Event() {
    fun getFileName(): String? {
        return fileName
    }

    fun getConfigKey(): String? {
        return configKey
    }

    fun getPackageName(): String? {
        return packageName
    }

    fun getValue(): T {
        return value
    }

    fun setValue(value: T) {
        this.value = value
    }
}