package club.someoneice.pc.event.bus

import club.someoneice.pc.api.SubEvent
import com.google.common.collect.HashBasedTable
import com.google.common.collect.Lists
import com.google.common.collect.Table
import java.lang.reflect.Method
import kotlin.reflect.KCallable
import kotlin.reflect.KClass
import kotlin.reflect.KFunction
import kotlin.reflect.jvm.javaMethod

@Suppress("unused")
object EventBus {
    private val PACKAGE_EVENT = PackageEvent()

    fun registerEvent(anyObject: KClass<*>) {
        registerEvent(anyObject.java)
    }

    fun registerEvent(anyObject: Class<*>) {
        for (sub in anyObject.methods) {
            registerEventHandler(sub, anyObject)
        }
    }

    fun registerEvent(anyObject: Any) {
        val clazz = anyObject::class
        for (sub in clazz.members) {
            registerEventHandler(sub, clazz.java)
        }
    }

    /**
     * Use [PostMan.post] to post a event.
     * @see PostMan.post
     */
    internal fun <T: Event> runEventSort(event: T): T {
        val clazz = event::class.java
        PACKAGE_EVENT.mapHighest.row(clazz).forEach { (clz, methodList) ->
            methodList.forEach {
                it.isAccessible = true
                it.invoke(clz, event)
            }
        }
        PACKAGE_EVENT.mapCommon.row(clazz).forEach { (clz, methodList) ->
            methodList.forEach {
                it.isAccessible = true
                it.invoke(clz, event)
            }
        }
        PACKAGE_EVENT.mapLower.row(clazz).forEach { (clz, methodList) ->
            methodList.forEach {
                it.isAccessible = true
                it.invoke(clz, event)
            }
        }

        return event
    }

    private fun registerEventHandler(sub: KCallable<*>, clazz: Class<*>) {
        if (sub !is KFunction) return
        val method = sub.javaMethod
        method?.let {
            if (!it.isAnnotationPresent(SubEvent::class.java)) return
            else it.getAnnotation(SubEvent::class.java)
        }?.let {
            when (it.value) {
                EventPriority.HIGHEST -> addToPackage(PACKAGE_EVENT.mapHighest, method, clazz)
                EventPriority.COMMON -> addToPackage(PACKAGE_EVENT.mapCommon, method, clazz)
                EventPriority.LOWER -> addToPackage(PACKAGE_EVENT.mapLower, method, clazz)
            }
        }
    }

    private fun registerEventHandler(method: Method, clazz: Class<*>) {
        method.let {
            if (!it.isAnnotationPresent(SubEvent::class.java)) return
            else it.getAnnotation(SubEvent::class.java)
        }?.let {
            when (it.value) {
                EventPriority.HIGHEST -> addToPackage(PACKAGE_EVENT.mapHighest, method, clazz)
                EventPriority.COMMON -> addToPackage(PACKAGE_EVENT.mapCommon, method, clazz)
                EventPriority.LOWER -> addToPackage(PACKAGE_EVENT.mapLower, method, clazz)
            }
        }
    }

    private fun addToPackage(pack: Table<Class<out Event>, Class<*>, ArrayList<Method>>, method: Method, clz: Class<*>) {
        val clazz = method.parameterTypes[0]
        if (clazz.isInstance(Event::class.java)) {
            clazz as Class<out Event>
            if (pack.contains(clazz, clz)) pack.get(clazz, clz)!!.add(method)
            else pack.put(clazz, clz, Lists.newArrayList(method))
        }
    }

    enum class EventPriority {
        HIGHEST,
        COMMON,
        LOWER
    }

    data class PackageEvent(
        val mapHighest: Table<Class<out Event>, Class<*>, ArrayList<Method>> = HashBasedTable.create(),
        val mapCommon:  Table<Class<out Event>, Class<*>, ArrayList<Method>> = HashBasedTable.create(),
        val mapLower:   Table<Class<out Event>, Class<*>, ArrayList<Method>> = HashBasedTable.create()
    )
}