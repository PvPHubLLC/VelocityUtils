package co.pvphub.velocity.reflect

import java.lang.reflect.Method

open class ClassAccessor(
    name: String
) {
    val clazz = name.velocityClass()!!
    lateinit var instance: Any

    fun createConstructor(vararg args: Any) {
        instance = clazz.getConstructor(*args.map { it::class.java }.toTypedArray()).newInstance(*args)
    }

    fun get() : Any {
        return instance
    }

    fun int(name: String, obj: Any? = null) : Int {
        return clazz.getDeclaredField(name).getInt(obj ?: clazz)
    }

    fun float(name: String, obj: Any? = null) : Float {
        return clazz.getDeclaredField(name).getFloat(obj ?: clazz)
    }

    fun double(name: String, obj: Any? = null) : Double {
        return clazz.getDeclaredField(name).getDouble(obj ?: clazz)
    }

    fun boolean(name: String, obj: Any? = null) : Boolean {
        return clazz.getDeclaredField(name).getBoolean(obj ?: clazz)
    }

    fun char(name: String, obj: Any? = null) : Char {
        return clazz.getDeclaredField(name).getChar(obj ?: clazz)
    }

    fun string(name: String, obj: Any? = null) : String {
        return clazz.getDeclaredField(name).get(obj ?: clazz) as String
    }

    fun get(name: String, obj: Any? = null) : Any {
        return clazz.getDeclaredField(name).get(obj ?: clazz)
    }

    fun long(name: String, obj: Any? = null) : Long {
        return clazz.getDeclaredField(name).getLong(obj ?: clazz)
    }

    fun short(name: String, obj: Any? = null) : Short {
        return clazz.getDeclaredField(name).getShort(obj ?: clazz)
    }

    fun <T> genericMethod(name: String, vararg args: Any) : T {
        return getMethod(name, *args).invoke(clazz, *args) as T
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