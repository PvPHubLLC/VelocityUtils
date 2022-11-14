package co.pvphub.velocity.reflect

import java.lang.reflect.Constructor
import java.lang.reflect.Field

fun findClass(name: String) : Class<*>? {
    return try {
        Class.forName(name)
    } catch (e: ClassNotFoundException) {
        null
    }
}

fun findVelocityClass(name: String) : Class<*>? {
    return findClass("com.velocitypowered.proxy.${name}")
}

fun String.velocityClass() : Class<*>? {
    return findVelocityClass(this)
}

fun Field.accessible(value: Boolean) : Field {
    this.isAccessible = value
    return this
}