package co.pvphub.velocity.reflect

import java.lang.reflect.Constructor
import java.lang.reflect.Method

open class ClassAccessor(
    name: String
) {
    val clazz = name.velocityClass()!!
    lateinit var instance: Any

    fun createConstructor(vararg args: Any) {
        instance = clazz.getConstructor(*args.map { it::class.java }.toTypedArray()).newInstance(*args)
    }

    fun getConstructor(vararg types: Class<*>): Constructor<out Any> {
        return clazz.getConstructor(*types)
    }

    fun get() : Any {
        return instance
    }

    fun int(name: String, obj: Any? = null) : Int {
        return clazz.getDeclaredField(name).getInt(obj)
    }

    fun float(name: String, obj: Any? = null) : Float {
        return clazz.getDeclaredField(name).getFloat(obj)
    }

    fun double(name: String, obj: Any? = null) : Double {
        return clazz.getDeclaredField(name).getDouble(obj)
    }

    fun boolean(name: String, obj: Any? = null) : Boolean {
        return clazz.getDeclaredField(name).getBoolean(obj)
    }

    fun char(name: String, obj: Any? = null) : Char {
        return clazz.getDeclaredField(name).getChar(obj)
    }

    fun string(name: String, obj: Any? = null) : String {
        return clazz.getDeclaredField(name).get(obj) as String
    }

    fun get(name: String, obj: Any? = null) : Any {
        // need to set accessible and provide the class instance (static or not)
        return clazz.getDeclaredField(name).accessible(true).get(obj)
    }

    fun long(name: String, obj: Any? = null) : Long {
        return clazz.getDeclaredField(name).getLong(obj)
    }

    fun short(name: String, obj: Any? = null) : Short {
        return clazz.getDeclaredField(name).getShort(obj)
    }

    fun <T> staticMethod(name: String, vararg args: Any) : T {
        return getMethod(name, *args).invoke(null, *args) as T
    }

    fun staticMethodVoid(name: String, vararg args: Any) {
        getMethod(name, *args).invoke(null, *args)
    }

    fun <T> instanceMethod(name: String, obj: Any? = null, vararg args: Any) : T {
        return getMethod(name, *args).invoke(obj ?: clazz, *args) as T
    }

    fun instanceMethodVoid(name: String, obj: Any? = null, vararg args: Any) {
        getMethod(name, *args).invoke(obj ?: clazz, *args)
    }

    fun getMethod(name: String, vararg args: Any) : Method {
        return getMethodByClasses(name, *(args.map { it::class.java }.toTypedArray()))
    }

    fun getMethodByClasses(name: String, vararg args: Class<*>) : Method {
        return clazz.getDeclaredMethod(name, *args)
    }

    fun clazzOf(value: Any) : Class<*> {
        return value.javaClass
    }

    fun clazz() : Class<*> {
        return clazz
    }

}

fun access(name: String, cb: ClassAccessor.() -> Unit) {
    val accessor = ClassAccessor(name)
    cb(accessor)
}