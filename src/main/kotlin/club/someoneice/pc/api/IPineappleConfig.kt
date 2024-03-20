package club.someoneice.pc.api

import club.someoneice.pc.config.ConfigBean

@Suppress("unused")
interface IPineappleConfig {
    fun init()
    fun reload(): IPineappleConfig? {
        if (this is ConfigBean) this.readFileAndOverrideConfig()
        init()
        return this
    }
}
