package club.someoneice.pc.config

import club.someoneice.pc.util.getOrSet
import club.someoneice.pc.util.setWithReturn
import com.google.common.collect.Maps
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.google.gson.reflect.TypeToken
import java.io.File
import java.io.FileInputStream
import java.io.FileOutputStream
import java.io.IOException

class ConfigBean(configName: String, canCommandSet: Boolean = false) {
    val configList: HashMap<String, Any> = Maps.newHashMap()
    private val json: Gson = GsonBuilder().setPrettyPrinting().disableHtmlEscaping().create()
    private val file = File("${System.getProperty("user.dir")}${File.separator}config${File.separator}${configName}.json")

    init {
        if (!file.isFile) {
            file.mkdirs()
            file.createNewFile()
        } else {
            val stringText = ByteArray(file.length().toInt())
            try {
                val inputStream = FileInputStream(file)
                inputStream.read(stringText)
                inputStream.close()
                this.configList.putAll(json.fromJson<Map<String, Any>>(String(stringText), object : TypeToken<Map<String?, Any?>?>() {}.type))
            } catch (e: IOException) {
                e.printStackTrace()
            }
        }
    }

    fun elementString   (name: String, value: String)   : String  = this.getFromConfig(name, value)
    fun elementInt      (name: String, value: Int)      : Int     = this.getFromConfig(name, value)
    fun elementBoolean  (name: String, value: Boolean)  : Boolean = this.getFromConfig(name, value)
    fun elementFloat  (name: String, value: Float)      : Float   = this.getFromConfig(name, value)
    fun elementDouble  (name: String, value: Double)    : Double  = this.getFromConfig(name, value)

    fun bing() {
        try {
            val outputStream = FileOutputStream(file)
            outputStream.write(this.json.toJson(this.configList).toByteArray())
            outputStream.close()
        } catch (e: IOException) {
            e.printStackTrace()
        }
    }

    private inline fun <reified T : Any> getFromConfig(name: String, value: T): T {
        val out = this.configList.getOrSet(name, value.toString())
        return if (out is T) out
        else this.configList.setWithReturn(name, value) as T
    }
}