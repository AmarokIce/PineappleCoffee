package club.someoneice.pc.config

import club.someoneice.json.JSON
import club.someoneice.json.node.JsonNode
import club.someoneice.json.node.MapNode
import club.someoneice.json.processor.Json5Builder.ArrayBean
import club.someoneice.json.processor.Json5Builder.ObjectBean
import club.someoneice.pc.api.IPineappleConfig
import com.google.common.collect.Maps
import com.google.common.io.Files
import java.io.File
import java.io.IOException


@Suppress("unused")
object ConfigUtil {
    @JvmField
    var configs = Maps.newHashMap<String, IPineappleConfig>()

    @JvmStatic
    @Throws(IOException::class)
    fun readFromJson(file: File): MapNode {
        if (!file.exists() || !file.isFile) file.createNewFile() else if (file.canRead()) {
            val node = JSON.json5.parse(file)
            if (node.type == JsonNode.NodeType.Null) return MapNode()
            if (node.type == JsonNode.NodeType.Map) return node as MapNode
        }
        return MapNode()
    }

    @JvmStatic
    @Throws(IOException::class)
    fun writeToJson(file: File, str: String) {
        if (!file.exists() || !file.isFile) file.createNewFile()
        Files.write(str.toByteArray(), file)
    }

    fun objectBean() = ObjectBean()
    fun arrayBean() = ArrayBean()
}
