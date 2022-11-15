package co.pvphub.velocity.protocol

import co.pvphub.velocity.reflect.ClassAccessor
import com.velocitypowered.api.network.ProtocolVersion
import com.velocitypowered.proxy.protocol.StateRegistry.PacketMapping

fun mappings(id: Int, ver: ProtocolVersion, lastVer: ProtocolVersion? = null, encodeOnly: Boolean): PacketMapping {
    val mappingAccessor = ClassAccessor("protocol.StateRegistry\$PacketMapping")
    val constructor = mappingAccessor.clazz().getDeclaredConstructor(
        Int::class.java,
        ProtocolVersion::class.java,
        ProtocolVersion::class.java,
        Boolean::class.java
    )
    constructor.isAccessible = true
    return constructor.newInstance(id, ver, lastVer, encodeOnly) as PacketMapping
}