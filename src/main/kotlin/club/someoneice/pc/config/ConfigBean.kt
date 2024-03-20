package club.someoneice.pc.config

import club.someoneice.json.JSON
import club.someoneice.json.node.JsonNode
import club.someoneice.json.node.MapNode
import club.someoneice.json.processor.Json5Builder
import club.someoneice.pc.PCMain
import club.someoneice.pc.api.IPineappleConfig
import club.someoneice.pc.event.PineappleConfigEvent
import club.someoneice.pc.event.bus.PostMan
import com.google.common.collect.Maps
import java.io.File
import java.io.IOException

@Suppress("unused")
open class ConfigBean(private val configName: String) {
    private var mapBean: Json5Builder.ObjectBean = ConfigUtil.objectBean()
    private val nodeMapping: MutableMap<String, Json5Builder.ObjectBean> = Maps.newHashMap<String, Json5Builder.ObjectBean>()
    private val file: File = File(System.getProperty("user.dir") + "config", "$configName.json5")
    private val nodeBase: MapNode = try { ConfigUtil.readFromJson(file) } catch (e: IOException) { MapNode() }


    init {
        if (this is IPineappleConfig) ConfigUtil.configs[configName] = this
    }

    /**
     * 当重载时使用。<p />
     * Use it when we should overload.
     */
    fun readFileAndOverrideConfig() {
        val nodeBase1: MapNode = try { ConfigUtil.readFromJson(file) } catch (e: IOException) { MapNode() }
        nodeBase.obj.clear()
        nodeBase1.obj.forEach(nodeBase::put)
        mapBean = ConfigUtil.objectBean()
        nodeMapping.clear()
    }

    fun getString(key: String, defValue: String): String = getBean(key, defValue)
    fun getInteger(key: String, defValue: Int): Int = getBean(key, defValue)
    fun getBoolean(key: String, defValue: Boolean): Boolean = getBean(key, defValue)
    fun getFloat(key: String, defValue: Float): Float = getBean(key, defValue)
    fun getFloat(key: String, defValue: Double): Double = getBean(key, defValue)

    fun getString(key: String, defValue: String, pack: String): String = getBeanWithPackage(key, defValue, pack)
    fun getInteger(key: String, defValue: Int, pack: String): Int = getBeanWithPackage(key, defValue, pack)
    fun getBoolean(key: String, defValue: Boolean, pack: String): Boolean = getBeanWithPackage(key, defValue, pack)
    fun getFloat(key: String, defValue: Float, pack: String): Float = getBeanWithPackage(key, defValue, pack)
    fun getFloat(key: String, defValue: Double, pack: String): Double = getBeanWithPackage(key, defValue, pack)
    fun addNote(note: String?) {
        mapBean.addNote(note)
    }

    fun addEnter() {
        mapBean.enterLine()
    }

    fun addNote(note: String?, packName: String) {
        val bean: Json5Builder.ObjectBean = nodeMapping.getOrDefault(packName, ConfigUtil.objectBean())
        bean.addNote(note)
        nodeMapping[packName] = bean
    }

    fun addEnter(packName: String) {
        val bean: Json5Builder.ObjectBean = nodeMapping.getOrDefault(packName, ConfigUtil.objectBean())
        bean.enterLine()
        nodeMapping[packName] = bean
    }

    fun build() {
        val builder = Json5Builder()
        nodeMapping.forEach(mapBean::addBean)
        builder.put(mapBean)
        val str: String = builder.build()
        try { ConfigUtil.writeToJson(file, str) } catch (e: IOException) { PCMain.LOGGER.error(e) }
    }

    private fun <T> getBean(key: String, defValue: T): T {
        var value: T = if (nodeBase.has(key)) { nodeBase.get(key).obj as T } else defValue
        value = PostMan.post(PineappleConfigEvent<T>(configName, key, value, null)).getValue()
        mapBean.put(key, JsonNode<T>(value))
        return value
    }

    private fun <T> getBeanWithPackage(key: String, defValue: T, packName: String): T {
        val bean: Json5Builder.ObjectBean = nodeMapping.getOrDefault(packName, ConfigUtil.objectBean())
        var value: T = if (nodeBase.has(packName)) {
            val node: MapNode = JSON.json.tryPullObjectOrEmpty(nodeBase.get(packName))
            if (node.has(key)) { node.get(key).getObj() as T } else defValue
        } else defValue
        value = PostMan.post(PineappleConfigEvent<T>(configName, key, value, packName)).getValue()
        bean.put(key, JsonNode<T>(value))
        nodeMapping[packName] = bean
        return value
    }

    companion object {
        @JvmStatic val CONFIG_MAP: Map<String, IPineappleConfig> = Maps.newHashMap<String, IPineappleConfig>()
        @JvmStatic val JSON5_BEAN: JSON = JSON.json5
    }
}
