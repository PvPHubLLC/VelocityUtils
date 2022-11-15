package co.pvphub.velocity.reflect

import com.velocitypowered.proxy.protocol.MinecraftPacket
import com.velocitypowered.proxy.protocol.StateRegistry.*
import java.lang.reflect.Constructor
import java.lang.reflect.Field
import java.util.function.Supplier

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

private val stateRegistryAccessor = ClassAccessor("protocol.StateRegistry\$PacketRegistry")
fun <T : MinecraftPacket> PacketRegistry.safeRegister(clazz: Class<T>, supplier: Supplier<T>, vararg mappings: PacketMapping) {
    val method = stateRegistryAccessor.clazz().declaredMethods.first { it.name == "register" }
    method.isAccessible = true
    method.invoke(this, clazz, supplier, arrayOf(*mappings))
}