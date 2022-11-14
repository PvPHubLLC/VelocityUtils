package co.pvphub.velocity.protocol

import co.pvphub.velocity.protocol.packet.MinecraftPacket
import co.pvphub.velocity.reflect.ClassAccessor
import com.velocitypowered.api.network.ProtocolVersion
import java.lang.reflect.ParameterizedType
import java.util.function.Supplier

private val stateRegistryAccessor = ClassAccessor("protocol.StateRegistry")
// each enum has it's own clientbound

enum class PacketType {
    HANDSHAKE,
    STATUS,
    PLAY,
    LOGIN;
    private val enumAccessor = stateRegistryAccessor.clazz().enumConstants.first { it.toString() == name }

    private val clientbound = enumAccessor.javaClass.getField("clientbound").get(enumAccessor)
    private val serverbound = enumAccessor.javaClass.getField("serverbound").get(enumAccessor)

    fun <T : MinecraftPacket> registerClientbound(packet: T, vararg mappings: Any) {
        try {
            val method = clientbound.javaClass.declaredMethods.first { it.name == "register" }
            method.isAccessible = true
            val built = MinecraftPacket.cast(packet)

            method.invoke(
                clientbound,
                built::class.java,
                { MinecraftPacket.cast(packet) },
                mappings as Any
            )
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    fun registerServerbound(clazz: Class<*>, vararg mappings: Any) {
        serverbound.javaClass
            .getMethod("register", clazz::class.java, Supplier::class.java, mappings::class.java)
            .invoke(serverbound, clazz, clazz.getConstructor(), mappings)
    }
}

fun mappings(id: Int, ver: ProtocolVersion, lastVer: ProtocolVersion? = null, encodeOnly: Boolean): Any {
    val mappingAccessor = ClassAccessor("protocol.StateRegistry\$PacketMapping")
    val constructor = mappingAccessor.clazz().getDeclaredConstructor(
        Int::class.java,
        ProtocolVersion::class.java,
        ProtocolVersion::class.java,
        Boolean::class.java
    )
    constructor.isAccessible = true
    return constructor.newInstance(id, ver, lastVer, encodeOnly)
}